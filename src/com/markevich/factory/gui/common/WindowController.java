package com.markevich.factory.gui.common;

import javafx.scene.Scene;

public class WindowController {

    private final Scene scene;

    private final DBWindow controller;

    public WindowController(Scene scene, DBWindow controller) {
        this.scene = scene;
        this.controller = controller;
    }

    public Scene getScene() {
        return scene;
    }

    public DBWindow getController() {
        return controller;
    }

    public void reloadWindow() {
        controller.reloadWindow();
    }
}
