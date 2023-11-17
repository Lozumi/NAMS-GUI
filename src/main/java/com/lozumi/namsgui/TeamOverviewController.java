package com.lozumi.namsgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.lozumi.namsgui.MainApp;
import com.lozumi.namsgui.model.*;

public class TeamOverviewController {
    @FXML
    private TextArea resultTextArea;
    @FXML
    private TextArea chosenTextArea;
    private TextArea chosenTeamTextArea;

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

    // Reference to the main application.
    private MainApp mainApp;

    // File chosen by the user
    private File chosenFile;

    private TeamParser teamParser;

    private ComboBox<Team> teamComboBox;

    private ObservableList<Team> teamList = FXCollections.observableArrayList();


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
        teamComboBox.setItems(teamList);
        // 自定义下拉框中每个选项的显示，只显示团队名
        teamComboBox.setCellFactory(new Callback<ListView<Team>, ListCell<Team>>() {
            @Override
            public ListCell<Team> call(ListView<Team> param) {
                return new ListCell<Team>() {
                    @Override
                    protected void updateItem(Team item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getTeamName());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        // 选择监听器，可以在选择不同团队时进行相应操作
        teamComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    showTeamDetails(newValue);
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
            // Implement plain text parsing logic
            // Example: mainApp.parsePlainText(chosenFile);
        } else if (xmlRadioButton.isSelected()) {
            // Implement XML parsing logic
            // Example: mainApp.parseXML(chosenFile);
        } else if (htmlRadioButton.isSelected()) {
            // Implement HTML parsing logic
            // Example: mainApp.parseHTML(chosenFile);
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
