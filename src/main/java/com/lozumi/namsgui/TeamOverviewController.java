package com.lozumi.namsgui;

import com.lozumi.namsgui.model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

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

    // 对主应用程序的引用
    private MainApp mainApp;

    // 用户选择的文件
    private File chosenFile;

    private TeamParser teamParser;

    // 团队列表
    private ObservableList<Team> teamList = FXCollections.observableArrayList();
    // 用户选择的团队
    private Team chosenTeam;

    /**
     * 处理选择按钮点击事件。
     */
    @FXML
    private void ChooseButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择数据文件");
        chosenFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if (chosenFile != null) {
            chosenTextArea.setText("文件路径：" + chosenFile.getAbsolutePath());
            System.out.println("文件路径：" + chosenFile.getAbsolutePath());
        }
    }

    /**
     * 处理读取按钮点击事件。
     */
    @FXML
    private void ReadButtonClicked() {
        if (chosenFile != null) {
            TeamParser teamParser = new TeamParser(chosenFile.getAbsolutePath());
            teamList.addAll(teamParser.readTeamsFromFile());
        }

        // 将观察列表数据添加到下拉框中
        teamComboBox.setItems(teamList);

        // 设置下拉框中每个选项的显示内容，只显示团队名
//        teamComboBox.setCellFactory(new Callback<ListView<Team>, ListCell<Team>>() {
//            @Override
//            public ListCell<Team> call(ListView<Team> param) {
//                return new ListCell<Team>() {
//                    @Override
//                    protected void updateItem(Team item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (item != null) {
//                            setText(item.getTeamName());
//                        } else {
//                            setText(null);
//                        }
//                    }
//                };
//            }
//        });
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
    }

    /**
     * 处理团队选择下拉框选项改变事件。
     */
    @FXML
    private void TeamComboBoxChosen() {
        // 选择监听器，可以在选择不同团队时进行相应操作
        teamComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    showTeamDetails(newValue);
                    chosenTeam = newValue;
                });
    }

    /**
     * 处理解析按钮点击事件。
     */
    @FXML
    private void ParseButtonClicked() {
        if (chosenFile == null) {
            showAlert("请选择一个数据文件。");
            return;
        }

        // 根据选定的格式（纯文本、XML、HTML）执行解析
        if (plainTextRadioButton.isSelected()) {
            resultTextArea.setText(PlainTextTeamFormatter.getSingletonInstance().formatTeam(chosenTeam));
        } else if (xmlRadioButton.isSelected()) {
            resultTextArea.setText(XMLTeamFormatter.getSingletonInstance().formatTeam(chosenTeam));
        } else if (htmlRadioButton.isSelected()) {
            resultTextArea.setText(HTMLTeamFormatter.getSingletonInstance().formatTeam(chosenTeam));
        } else {
            showAlert("请选择解析格式。");
        }
    }

    /**
     * 处理重置按钮点击事件。
     */
    @FXML
    private void resetButtonClicked() {
        // 重置 UI 元素
        resultTextArea.clear();
        plainTextRadioButton.setSelected(false);
        xmlRadioButton.setSelected(false);
        htmlRadioButton.setSelected(false);
    }

    // 弹出警告对话框的辅助方法
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("警告");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
