package com.lozumi.namsgui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * 主应用程序类，负责启动 JavaFX 应用程序和管理主界面。
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private TeamOverviewController teamOverviewController;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("团队管理系统");
        initRootLayout();
        showTeamOverview();
    }

    /**
     * 初始化主界面的根布局。
     */
    public void initRootLayout() {
        try {
            // 从FXML文件加载根布局。
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // 显示包含根布局的场景。
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在根布局内显示团队概览。
     */
    public void showTeamOverview() {
        try {
            // 加载团队概览。
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TeamOverview.fxml"));
            AnchorPane teamOverview = (AnchorPane) loader.load();

            // 设置团队概览为根布局的中心部分。
            rootLayout.setCenter(teamOverview);

            // 将控制器访问主应用程序。
            TeamOverviewController teamOverviewController = loader.getController();
            teamOverviewController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回主舞台。
     *
     * @return 主舞台
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * 应用程序的入口点。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        launch(args);
    }
}
