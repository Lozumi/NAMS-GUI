package com.lozumi.namsgui;

import com.lozumi.namsgui.model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.File;

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


    // Reference to the main application.
    private MainApp mainApp;

    // File chosen by the user
    private File chosenFile;

    private TeamParser teamParser;

    private ObservableList<Team> teamList = FXCollections.observableArrayList();
    private Team chosenTeam;


    // Event handler for the Choose button
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

    // Event handler for the Read button
    @FXML
    private void ReadButtonClicked() {
        if (chosenFile != null) {
            TeamParser teamParser = new TeamParser(chosenFile.getAbsolutePath());
            teamList.addAll(teamParser.readTeamsFromFile());
        }
        // 提供一个下拉栏选择团队列表里的一个团队

        // 将观察列表数据添加到下拉框中
        // 使用 StringConverter 只是负责将对象在界面上显示为字符串和从字符串转换为对象，对于 ObservableList 的操作并没有影响。 StringConverter 主要是用于处理对象在 UI 上的显示和输入。
        teamComboBox.setItems(teamList);
        // 自定义下拉框中每个选项的显示，只显示团队名
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

        //默认展示第一个队伍
        teamComboBox.setValue(teamList.get(0));
        chosenTeam=teamList.get(0);
    }


    @FXML
    private void TeamComboBoxChosen(){
        // 选择监听器，可以在选择不同团队时进行相应操作
        teamComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    showTeamDetails(newValue);
                    chosenTeam = newValue;
                    //updateChosenTeamTextArea(newValue);
                });
    }

    // Event handler for the Parse button
    @FXML
    private void ParseButtonClicked() {
        if (chosenFile == null) {
            showAlert("请选择一个数据文件。");
            return;
        }

        // Perform parsing based on selected format (plain text, XML, HTML)
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
     * Called when the user clicks the "重置" button.
     */
    @FXML
    private void resetButtonClicked() {
        // Reset the UI elements as needed
        resultTextArea.clear();
        plainTextRadioButton.setSelected(false);
        xmlRadioButton.setSelected(false);
        htmlRadioButton.setSelected(false);
    }

    // Utility method to show an alert
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("警告");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showTeamDetails(Team team) {
        // 在此处添加显示团队详情的逻辑，可以更新其他界面元素
    }

    //    private void updateChosenTeamTextArea(Team team) {
//        if (team != null) {
//            chosenTeamTextArea.setText("已选择的团队名：" + team.getTeamName());
//        } else {
//            chosenTeamTextArea.clear();
//        }
//    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
