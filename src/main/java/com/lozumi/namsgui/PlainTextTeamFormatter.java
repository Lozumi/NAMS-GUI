package com.lozumi.namsgui;
import com.lozumi.namsgui.model.*;

/**
 * 纯文本团队格式化类
 *
 * <p>该类实现了 {@link TeamFormatter} 接口，用于生成包含团队信息的纯文本字符串。
 *
 * <p>采用单例模式确保只有一个实例。
 *
 * @author Lozumi
 * @version 1.0
 */
public class PlainTextTeamFormatter implements TeamFormatter {
	private static PlainTextTeamFormatter singletonInstance;

	private PlainTextTeamFormatter() {}

	/**
	 * 获取类的单例实例
	 *
	 * @return PlainTextTeamFormatter 的单例实例
	 */
	public static synchronized PlainTextTeamFormatter getSingletonInstance() {
		if (singletonInstance == null) {
			singletonInstance = new PlainTextTeamFormatter();
		}
		return singletonInstance;
	}

	/**
	 * 格式化团队信息为纯文本字符串
	 *
	 * @param team 要格式化的团队对象
	 * @return 包含团队信息的纯文本字符串
	 * 相比原定义，使用了 String.format 来使字符串拼接更简洁
	 */
	@Override
	public String formatTeam(Team team) {
		StringBuilder sb = new StringBuilder();
		sb.append("Team {\n")
				.append(String.format("\tteamId : %s\n", team.getTeamId()))
				.append(String.format("\tteamName : %s\n", team.getTeamName()))
				.append(String.format("\tdepartment : %s\n", team.getDepartment()))
				.append("\tcreator {\n")
				.append(String.format("\t\tid : %s\n", team.getCreator().getId()))
				.append(String.format("\t\tgender : %s\n", team.getCreator().getGender() ? "女" : "男"))
				.append("\t}\n\tstudentList {\n");

		for (Student s : team.getStudentList()) {
			sb.append("\t\tstudent { \n")
					.append(String.format("\t\t\tid : %s\n", s.getId()))
					.append(String.format("\t\t\tname : %s\n", s.getName()))
					.append("\t\t}\n");
		}
		sb.append("\t}\n\tteacherList {\n");

		for (Teacher t : team.getTeacherList()) {
			sb.append("\t\tteacher { \n")
					.append(String.format("\t\t\tid : %s\n", t.getId()))
					.append(String.format("\t\t\tname : %s\n", t.getName()))
					.append("\t\t}\n");
		}
		sb.append("\t}\n}\n");

		return sb.toString();
	}
}
