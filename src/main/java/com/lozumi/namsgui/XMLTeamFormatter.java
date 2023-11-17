package com.lozumi.namsgui;
import com.lozumi.namsgui.model.*;

/**
 * XML团队格式化类
 *
 * <p>该类实现了 {@link TeamFormatter} 接口，用于生成包含团队信息的XML字符串。
 *
 * <p>采用单例模式确保只有一个实例。
 *
 * @author Lozumi
 * @version 1.0
 */
public class XMLTeamFormatter implements TeamFormatter {
    private static XMLTeamFormatter singletonInstance;

    private XMLTeamFormatter() {}

    /**
     * 获取类的单例实例
     *
     * @return XMLTeamFormatter 的单例实例
     */
    public static synchronized XMLTeamFormatter getSingletonInstance() {
        if (singletonInstance == null) {
            singletonInstance = new XMLTeamFormatter();
        }
        return singletonInstance;
    }

    /**
     * 格式化团队信息为XML字符串
     *
     * @param team 要格式化的团队对象
     * @return 包含团队信息的XML字符串
     * 使用了 String.format 来使字符串拼接更简洁
     */
    @Override
    public String formatTeam(Team team) {
        Student creator = team.getCreator();
        StringBuilder sb = new StringBuilder();

        // 团队信息
        sb.append(String.format("<Team teamId=\"%s\" teamName=\"%s\" department=\"%s\">\n",
                team.getTeamId(), team.getTeamName(), team.getDepartment()));

        // 创建者信息
        sb.append(String.format("\t<Creator id=\"%s\" name=\"%s\" phoneNo=\"%s\" email=\"%s\" studentNo=\"%s\" gender=\"%s\" grade=\"%s\" department=\"%s\"/>\n",
                creator.getId(), creator.getName(), creator.getPhoneNo(), creator.getEmail(),
                creator.getStudentNo(), creator.getGender() ? "女" : "男", creator.getGrade(), creator.getDepartment()));

        // 学生列表
        sb.append("\t<StudentList>\n");
        for (Student student : team.getStudentList()) {
            sb.append(String.format("\t\t<Student id=\"%s\" name=\"%s\" phoneNo=\"%s\" email=\"%s\" studentNo=\"%s\" gender=\"%s\" grade=\"%s\" department=\"%s\"/>\n",
                    student.getId(), student.getName(), student.getPhoneNo(), student.getEmail(),
                    student.getStudentNo(), student.getGender() ? "女" : "男", student.getGrade(), student.getDepartment()));
        }
        sb.append("\t</StudentList>\n");

        // 教师列表
        sb.append("\t<TeacherList>\n");
        for (Teacher teacher : team.getTeacherList()) {
            sb.append(String.format("\t\t<Teacher id=\"%s\" name=\"%s\" phoneNo=\"%s\" email=\"%s\" teacherNo=\"%s\" department=\"%s\"/>\n",
                    teacher.getId(), teacher.getName(), teacher.getPhoneNo(), teacher.getEmail(),
                    teacher.getTeacherNo(), teacher.getDepartment()));
        }
        sb.append("\t</TeacherList>\n");

        // 结束 Team 标签
        sb.append("</Team>\n");

        return sb.toString();
    }
}
