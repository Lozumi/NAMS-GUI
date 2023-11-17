package com.lozumi.namsgui;

import com.lozumi.namsgui.model.Student;
import com.lozumi.namsgui.model.Team;
import com.lozumi.namsgui.model.Teacher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 团队解析器类，负责从文件中读取团队信息并解析为团队对象。
 */
public class TeamParser {
    private final String filePath;

    /**
     * 构造函数，初始化团队解析器，指定数据文件路径。
     *
     * @param filePath 数据文件路径
     */
    public TeamParser(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 从数据文件中读取团队信息并解析为团队对象列表。
     *
     * @return 团队对象列表
     */
    public ArrayList<Team> readTeamsFromFile() {
        ArrayList<Team> teams = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Team team = parseTeam(line);
                if (team != null) {
                    teams.add(team);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return teams;
    }

    /**
     * 解析一行数据为团队对象。
     *
     * @param line 数据文件中的一行数据
     * @return 解析后的团队对象，如果解析失败返回 null
     */
    private Team parseTeam(String line) {
        if (line.trim().isEmpty()) {
            return null;  // 如果是空行，则返回 null
        }
        String[] parts = line.split("_");

        String teamId = parts[1];
        String teamName = parts[2];
        String department = parts[3];

        // 创建者
        String creatorId = parts[5];
        String creatorName = parts[6];
        String creatorPhoneNo = parts[7];
        String creatorEmail = parts[8];
        String creatorStudentNo = parts[9];
        boolean creatorStudentGender = Objects.equals(parts[10], "男");
        String creatorGrade = parts[11];
        String creatorDepartment = parts[12];
        Student creator = new Student(creatorId, creatorName, creatorPhoneNo, creatorEmail,
                creatorStudentNo, creatorStudentGender, creatorGrade, creatorDepartment);

        Team team = new Team(teamId, teamName, department, creator);

        // 学生
        int studentStartIndex = 13;
        while (studentStartIndex < parts.length && parts[studentStartIndex].equals("Student")) {
            String studentId = parts[studentStartIndex + 1];
            String studentName = parts[studentStartIndex + 2];
            String studentPhoneNo = parts[studentStartIndex + 3];
            String studentEmail = parts[studentStartIndex + 4];
            String studentNo = parts[studentStartIndex + 5];
            boolean studentGender = Objects.equals(parts[studentStartIndex + 6], "男");
            String studentGrade = parts[studentStartIndex + 7];
            String studentDepartment = parts[studentStartIndex + 8];

            Student student = new Student(studentId, studentName, studentPhoneNo, studentEmail,
                    studentNo, studentGender, studentGrade, studentDepartment);

            team.addStudent(student);
            studentStartIndex += 9; // 移动到下一个学生或教师
        }

        // 教师
        int teacherStartIndex = studentStartIndex;
        while (teacherStartIndex < parts.length && parts[teacherStartIndex].equals("Teacher")) {
            String teacherId = parts[teacherStartIndex + 1];
            String teacherName = parts[teacherStartIndex + 2];
            String teacherPhoneNo = parts[teacherStartIndex + 3];
            String teacherEmail = parts[teacherStartIndex + 4];
            String teacherNo = parts[teacherStartIndex + 5];
            String teacherDepartment = parts[teacherStartIndex + 6];

            Teacher teacher = new Teacher(teacherId, teacherName, teacherPhoneNo, teacherEmail,
                    teacherNo, teacherDepartment);

            team.addTeacher(teacher);
            teacherStartIndex += 7; // 移动到下一个教师
        }

        return team;
    }

    /**
     * 主方法，用于测试团队解析器。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        TeamParser teamFileReader = new TeamParser("team.dat");
        ArrayList<Team> teams = teamFileReader.readTeamsFromFile();

        // 打印团队信息
        for (Team team : teams) {
            System.out.println(team);
        }
    }
}
