package com.markevich.factory.gui.common;

import com.markevich.factory.gui.exceptions.CanNotCreateWindowException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CheckConnect {

    public void createWindow() {
        Stage stage = new Stage();
        WindowConfigs windowConfig = WindowConfigs.CheckConnectWindow;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(windowConfig.getXmlUi()));
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new CanNotCreateWindowException(e, windowConfig);
        }
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }
}
