package com.markevich.factory;


import com.markevich.factory.gui.common.AppWindows;
import com.markevich.factory.gui.common.WindowConfigs;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.addMainStage(primaryStage);
        appWindows.showWindow(WindowConfigs.StartWindow);
    }
}
