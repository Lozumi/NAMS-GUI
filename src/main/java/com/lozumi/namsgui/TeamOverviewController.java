package com.lozumi.namsgui;

import com.lozumi.namsgui.model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.ScrollPane;

import java.io.File;

/**
 * 团队概览控制器类，处理与团队概览界面交互的事件和逻辑。
 */
public class TeamOverviewController {

    @FXML
    private TextArea resultTextArea;
    @FXML
    private TextArea chosenTextArea;
    @FXML
    private RadioButton plainTextRadioButton;
    @FXML
    private RadioButton xmlRadioButton;
    @FXML
    private RadioButton htmlRadioButton;
    @FXML
    private Button chooseButton;
    @FXML
    private Button parseButton;
    @FXML
    private Button resetButton;
    @FXML
    private ComboBox<Team> teamComboBox;
    @FXML
    private TextFlow guideTextFlow;
    @FXML
    private ScrollPane scrollPane;

    // 对主应用程序的引用
    private MainApp mainApp;

    // 用户选择的文件
    private File chosenFile;

    private TeamParser teamParser;

    // 团队列表
    private ObservableList<Team> teamList = FXCollections.observableArrayList();
    // 用户选择的团队
    private Team chosenTeam;

    @FXML
    public void initialize() {
        // 设置欢迎信息
        guideTextFlow.getChildren().addAll(createColoredText("欢迎使用团队管理系统！本系统由Lozumi制作，当前版本1.0。\n请根据左侧边栏文字提示进行操作。", Color.GREEN));
        scrollPane.setVvalue(scrollPane.getVmax());
    }

    /**
     * 处理选择按钮点击事件。
     */
    @FXML
    private void ChooseButtonClicked() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择数据文件");
            chosenFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
            if (chosenFile != null) {
                chosenTextArea.setText("文件路径：" + chosenFile.getAbsolutePath());
                appendSuccessText("选择成功！");
            }
        } catch (Exception e) {
            appendErrorText("选择按钮点击时出现异常：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 处理读取按钮点击事件。
     */
    @FXML
    private void ReadButtonClicked() {
        try {
            if (chosenFile != null && chosenTeam == null) {
                TeamParser teamParser = new TeamParser(chosenFile.getAbsolutePath());
                teamList.addAll(teamParser.readTeamsFromFile());

                // 将观察列表数据添加到下拉框中
                teamComboBox.setItems(teamList);

                // 设置下拉框中每个选项的显示内容，只显示团队名
                teamComboBox.setConverter(new StringConverter<Team>() {
                    @Override
                    public String toString(Team team) {
                        return team == null ? null : team.getTeamName();
                    }

                    @Override
                    public Team fromString(String string) {
                        // 如果需要支持从字符串转为 Team 对象，可以在这里实现
                        return null;
                    }
                });

                // 默认展示第一个队伍
                teamComboBox.setValue(teamList.get(0));
                chosenTeam = teamList.get(0);
                appendSuccessText("读取成功！");
            } else {
                appendErrorText("请勿重复读取！");
            }
        } catch (Exception e) {
            appendErrorText("读取按钮点击时出现异常：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 处理团队选择下拉框选项改变事件。
     */
    @FXML
    private void TeamComboBoxChosen() {
        try {
            // 选择监听器，可以在选择不同团队时进行相应操作
            teamComboBox.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        showTeamDetails(newValue);
                        chosenTeam = newValue;
                        //appendSuccessText("选择了"+newValue+"~");
                    });
        } catch (Exception e) {
            appendErrorText("团队选择下拉框选项改变事件处理时出现异常：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 处理解析按钮点击事件。
     */
    @FXML
    private void ParseButtonClicked() {
        try {
            if (chosenFile != null) {
                // 根据选定的格式（纯文本、XML、HTML）执行解析
                if (plainTextRadioButton.isSelected()) {
                    resultTextArea.setText(PlainTextTeamFormatter.getSingletonInstance().formatTeam(chosenTeam));
                    appendSuccessText("成功解析为纯文字格式~");
                } else if (xmlRadioButton.isSelected()) {
                    resultTextArea.setText(XMLTeamFormatter.getSingletonInstance().formatTeam(chosenTeam));
                    appendSuccessText("成功解析为XML格式~");
                } else if (htmlRadioButton.isSelected()) {
                    resultTextArea.setText(HTMLTeamFormatter.getSingletonInstance().formatTeam(chosenTeam));
                    appendSuccessText("成功解析为HTML格式~");
                } else {
                    appendErrorText("请选择解析格式。");
                }
            } else {
                appendErrorText("请首先读取团队。");
            }
        } catch (Exception e) {
            appendErrorText("解析按钮点击时出现异常：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 重置按钮点击事件处理方法。
     */
    @FXML
    private void resetButtonClicked() {
        try {
            // 重置 UI 元素和相关对象
            resultTextArea.clear();
            plainTextRadioButton.setSelected(false);
            xmlRadioButton.setSelected(false);
            htmlRadioButton.setSelected(false);

            // 清空已选择的文件
            chosenFile = null;
            chosenTextArea.clear();

            // 清空已选择的团队和解析器对象
            chosenTeam = null;
            teamParser = null;
            teamComboBox.getItems().clear();
            appendSuccessText("重置完成！");
        } catch (Exception e) {
            appendErrorText("重置按钮点击时出现异常：" + e.getMessage());
            e.printStackTrace();
        }
    }

    // 弹出警告对话框的辅助方法
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("警告");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // 辅助方法，将异常信息添加到 guideTextFlow 中，并设置为红色
    private void appendErrorText(String error) {
        Text errorText = new Text("\n" + error);
        errorText.setFill(Color.RED);
        guideTextFlow.getChildren().addAll(errorText);
        scrollPane.setVvalue(scrollPane.getVmax());
    }

    // 辅助方法，将成功信息添加到 guideTextFlow 中，并设置为绿色
    private void appendSuccessText(String success) {
        Text successText = new Text("\n" + success);
        successText.setFill(Color.GREEN);
        guideTextFlow.getChildren().addAll(successText);
        scrollPane.setVvalue(scrollPane.getVmax());
    }

    //创建颜色文字
    private Text createColoredText(String text, Color color) {
        Text coloredText = new Text(text);
        coloredText.setFill(color);
        return coloredText;
    }

    /**
     * 显示团队详情的辅助方法。
     *
     * @param team 要显示的团队
     */
    private void showTeamDetails(Team team) {
        // 在此处添加显示团队详情的逻辑，可以更新其他界面元素
    }

    /**
     * 设置对主应用程序的引用。
     *
     * @param mainApp 主应用程序
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
