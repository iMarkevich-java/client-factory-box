package com.markevich.factory.gui.controllers;

import com.markevich.factory.gui.common.DBWindow;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ControllerCheckConnectWindow implements DBWindow {

    @FXML
    private javafx.scene.control.Button closeButton;

    @FXML
    private void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void setData(String data) {

    }

    @Override
    public void reloadWindow() {

    }
}
