package com.lozumi.namsgui;
import com.lozumi.namsgui.model.*;

/**
 * HTML团队格式化类
 *
 * <p>该类实现了 {@link TeamFormatter} 接口，用于生成包含团队信息的HTML字符串。
 *
 * <p>采用单例模式确保只有一个实例。
 *
 * @author Lozumi
 * @version 1.0
 */
public class HTMLTeamFormatter implements TeamFormatter {
    private static HTMLTeamFormatter singletonInstance;

    private HTMLTeamFormatter() {}

    /**
     * 获取类的单例实例
     *
     * @return HTMLTeamFormatter 的单例实例
     */
    public static synchronized HTMLTeamFormatter getSingletonInstance() {
        if (singletonInstance == null) {
            singletonInstance = new HTMLTeamFormatter();
        }
        return singletonInstance;
    }

    /**
     * 格式化团队信息为HTML字符串
     *
     * @param team 要格式化的团队对象
     * @return 包含团队信息的HTML字符串
     * 使用了 String.format 来使字符串拼接更简洁
     */
    @Override
    public String formatTeam(Team team) {
        Student creator = team.getCreator();
        StringBuilder sb = new StringBuilder();

        // HTML 头部
        sb.append("<html>\n\t<body>\n\t\t<center><h2>团队信息</h2><center>\n\t\t");

        // 团队信息
        sb.append(String.format("%s %s %s<br>\n\t\tcreator[%s %s %s %s %s %s %s %s]<br>\n\t\t",
                team.getTeamId(), team.getTeamName(), team.getDepartment(),
                creator.getId(), creator.getName(), creator.getPhoneNo(),
                creator.getEmail(), creator.getStudentNo(),
                creator.getGender() ? "女" : "男", creator.getGrade(), creator.getDepartment()));

        // 学生列表
        sb.append("\t\t<h3>学生列表</h3>\n\t\t<blockquote>\n");
        for (Student s : team.getStudentList()) {
            sb.append(String.format("\t\t\t%s %s %s %s %s %s %s %s<br>\n",
                    s.getId(), s.getName(), s.getPhoneNo(),
                    s.getEmail(), s.getStudentNo(),
                    s.getGender() ? "女" : "男", s.getGrade(), s.getDepartment()));
        }
        sb.append("\t\t</blockquote>\n\t\t<h3>教师列表</h3>\n\t\t<blockquote>\n");

        // 教师列表
        for (Teacher t : team.getTeacherList()) {
            sb.append(String.format("\t\t\t%s %s %s %s %s %s<br>\n",
                    t.getId(), t.getName(), t.getPhoneNo(),
                    t.getEmail(), t.getTeacherNo(), t.getDepartment()));
        }

        // HTML 尾部
        sb.append("\t\t</blockquote>\n\t</body>\n</html>\n");

        return sb.toString();
    }
}
