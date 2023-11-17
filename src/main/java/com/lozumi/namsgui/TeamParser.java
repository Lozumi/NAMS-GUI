package com.lozumi.namsgui;
import com.lozumi.namsgui.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TeamParser {
    private final String filePath;

    public TeamParser(String filePath) {
        this.filePath = filePath;
    }

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

    private Team parseTeam(String line) {
        String[] parts = line.split("_");

        String teamId = parts[1];
        String teamName = parts[2];
        String department = parts[3];

        // 创建者
        String creatorId = parts[5];
        String creatorName = parts[6];
        String creatorPhoneNo = parts[7];
        String creatorEmail = parts[8];
        Student creator = new Student(creatorId, creatorName, creatorPhoneNo, creatorEmail,
                "", false, "", ""); // 你可能需要调整构造函数

        Team team = new Team(teamId, teamName, department, creator);

        // 学生
        int studentStartIndex = 10;
        while (parts[studentStartIndex].equals("Student")) {
            String studentId = parts[studentStartIndex + 1];
            String studentName = parts[studentStartIndex + 2];
            String studentPhoneNo = parts[studentStartIndex + 3];
            String studentEmail = parts[studentStartIndex + 4];
            String studentNo = parts[studentStartIndex + 5];
            boolean studentGender = Boolean.parseBoolean(parts[studentStartIndex + 6]);
            String studentGrade = parts[studentStartIndex + 7];
            String studentDepartment = parts[studentStartIndex + 8];

            Student student = new Student(studentId, studentName, studentPhoneNo, studentEmail,
                    studentNo, studentGender, studentGrade, studentDepartment);

            team.addStudent(student);
            studentStartIndex += 10; // 移动到下一个学生或教师
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

    public static void main(String[] args) {
        TeamParser teamFileReader = new TeamParser("team.dat");
        ArrayList<Team> teams = teamFileReader.readTeamsFromFile();

        // 打印团队信息
        for (Team team : teams) {
            System.out.println(team);
        }
    }
}
