package com.markevich.factory.gui.controllers;

import businessObjectFactoryBox.User;
import com.markevich.factory.ConnectDataUser;
import com.markevich.factory.gui.common.AppWindows;
import com.markevich.factory.gui.common.CheckConnect;
import com.markevich.factory.gui.common.DBWindow;
import com.markevich.factory.gui.common.WindowConfigs;
import com.markevich.factory.service.ServiceFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ControllerMainWindow implements DBWindow {
    @FXML
    private CheckBox localhostCheckBox;
    @FXML
    private Button allDataButton;
    @FXML
    private Button staffButton;
    @FXML
    private Button supplierButton;
    @FXML
    private Button clientButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button staffDayButton;
    @FXML
    private Button connectButton;
    @FXML
    private Button enterUserButton;
    @FXML
    private TextField ipTextField;
    @FXML
    private TextField portTextField;
    @FXML
    private TextField nameUserTextField;
    @FXML
    private PasswordField passwordField;

    public void showStaffDayWindow() {
        if (checkConnect()) {
            AppWindows appWindows = AppWindows.getInstance();
            appWindows.showWindow(WindowConfigs.StaffDayWindow);
            appWindows.reloadWindow(WindowConfigs.StaffDayWindow);
        } else {
            showCheckConnectWindow();
        }
    }

    public void showAllDBWindow() {
        if (checkConnect()) {
            AppWindows appWindows = AppWindows.getInstance();
            appWindows.showWindow(WindowConfigs.AllDBWindow);
            appWindows.reloadWindow(WindowConfigs.AllDBWindow);
        } else {
            showCheckConnectWindow();
        }
    }

    public void showClientListWindow() {
        if (checkConnect()) {
            AppWindows appWindows = AppWindows.getInstance();
            appWindows.showWindow(WindowConfigs.ClientListWindow);
            appWindows.reloadWindow(WindowConfigs.ClientListWindow);
        } else {
            showCheckConnectWindow();
        }
    }

    public void showStaffListWindow() {
        if (checkConnect()) {
            AppWindows appWindows = AppWindows.getInstance();
            appWindows.showWindow(WindowConfigs.StaffListWindow);
            appWindows.reloadWindow(WindowConfigs.StaffListWindow);
        } else {
            showCheckConnectWindow();
        }
    }

    public void showSupplierListWindow() {
        if (checkConnect()) {
            AppWindows appWindows = AppWindows.getInstance();
            appWindows.showWindow(WindowConfigs.SupplierListWindow);
            appWindows.reloadWindow(WindowConfigs.SupplierListWindow);
        } else {
            showCheckConnectWindow();
        }
    }

    private void showCheckConnectWindow() {
        CheckConnect checkConnect = new CheckConnect();
        checkConnect.createWindow();
    }

    @FXML
    private void enter() {
        if (enterUserButton.getText().equals("Enter")) {
            if (!(nameUserTextField.getText().isEmpty() || passwordField.getText().isEmpty())) {
                User user = new User();
                user.setName(nameUserTextField.getText());
                user.setPassword(passwordField.getText());
                String message = ServiceFactory.UserServices().verification(user);
                if (message.equals("Authorized")) {
                    enterUserButton.setText("Exit");
                    nameUserTextField.setDisable(true);
                    passwordField.setDisable(true);
                    allDataButton.setDisable(false);
                    staffButton.setDisable(false);
                    clientButton.setDisable(false);
                    supplierButton.setDisable(false);
                    staffDayButton.setDisable(false);
                    reloadWindow();
                }
            }
        } else {
            nameUserTextField.setText("");
            passwordField.setText("");
            enterUserButton.setText("Enter");
            nameUserTextField.setDisable(false);
            passwordField.setDisable(false);
            allDataButton.setDisable(true);
            staffButton.setDisable(true);
            clientButton.setDisable(true);
            supplierButton.setDisable(true);
            staffDayButton.setDisable(true);
            reloadWindow();
        }
    }

    @FXML
    private void connect() {
        if (connectButton.getText().equals("CONNECT")) {
            if (localhostCheckBox.isSelected()) {
                ConnectDataUser.setIp("localhost");
            } else {
                if (!(ipTextField.getText().isEmpty())) {
                    ConnectDataUser.setIp(ipTextField.getText());
                } else {
                    return;
                }
            }
            if (!portTextField.getText().isEmpty()) {
                ConnectDataUser.setPort(Integer.parseInt(portTextField.getText()));
            } else {
                return;
            }
            if (ServiceFactory.ConnectService().connect().equals("OK")) {
                connectButton.setText("DISCONNECT");
                localhostCheckBox.setDisable(true);
                ipTextField.setDisable(true);
                portTextField.setDisable(true);
                enterUserButton.setDisable(false);
                nameUserTextField.setDisable(false);
                passwordField.setDisable(false);
                reloadWindow();
            }
        } else {
            ConnectDataUser.setIp(null);
            ConnectDataUser.setPort(null);
            ipTextField.setText("");
            portTextField.setText("");
            localhostCheckBox.setDisable(false);
            ipTextField.setDisable(false);
            portTextField.setDisable(false);
            connectButton.setText("CONNECT");
            nameUserTextField.setText("");
            passwordField.setText("");

            enterUserButton.setText("Enter");
            enterUserButton.setDisable(true);
            nameUserTextField.setDisable(true);
            passwordField.setDisable(true);

            allDataButton.setDisable(true);
            staffButton.setDisable(true);
            clientButton.setDisable(true);
            supplierButton.setDisable(true);
            staffDayButton.setDisable(true);
            reloadWindow();
        }

    }

    private Boolean checkConnect() {
        return ServiceFactory.ConnectService().connect().equals("OK");
    }

    @FXML
    private void exit() {
        Platform.exit();
    }

    @Override
    public void setData(String data) {
    }

    @Override
    public void reloadWindow() {
    }
}
