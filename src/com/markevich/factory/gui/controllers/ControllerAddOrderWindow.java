package com.markevich.factory.gui.controllers;


import businessObjectFactoryBox.Client;
import businessObjectFactoryBox.Order;
import com.markevich.factory.gui.common.AppWindows;
import com.markevich.factory.gui.common.CheckConnect;
import com.markevich.factory.gui.common.DBWindow;
import com.markevich.factory.gui.common.WindowConfigs;
import com.markevich.factory.service.ServiceFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigInteger;
import java.util.List;

public class ControllerAddOrderWindow implements DBWindow {

    @FXML
    private void showMainWindow() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.showWindow(WindowConfigs.StartWindow);
        appWindows.reloadWindow(WindowConfigs.StartWindow);
    }

    @FXML
    private void showOrdersClientWindow() {
        if (checkConnect()) {
            AppWindows appWindows = AppWindows.getInstance();
            appWindows.showWindow(WindowConfigs.OrdersClientWindow);
            appWindows.getWindowByConfig(WindowConfigs.OrdersClientWindow).getController().setData(data);
            appWindows.reloadWindow(WindowConfigs.OrdersClientWindow);
        } else {
            showCheckConnectWindow();
        }
    }

    private void showCheckConnectWindow() {
        CheckConnect checkConnect = new CheckConnect();
        checkConnect.createWindow();
    }

    @FXML
    private DatePicker dateDatePicker;
    @FXML
    private Label clientNameLabel;
    @FXML
    private TextField sizeOrderTextField;
    @FXML
    private TextField orderTermTextField;
    @FXML
    private SplitMenuButton statusSplitMenuButton;
    @FXML
    private TextField stageTextField;

    public void save() {
        if (checkConnect()) {
            Order order = new Order();
            if (checkValueText()) {
                order.setOrderName(clientNameLabel.getText());
                order.setStage(stageTextField.getText());
                order.setStatus(statusSplitMenuButton.getText());
                order.setSizeOrder(sizeOrderTextField.getText());
                order.setClientId(data);
                order.setStartDate(dateDatePicker.getValue().toString());
                order.setOrderTerm(orderTermTextField.getText());
                order.setId("0");
                ServiceFactory.OrderServices().save(order);
                reloadWindow();
            }
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private MenuItem waitMenuItem;

    @FXML
    private void checkStatusWait() {
        statusSplitMenuButton.setText(waitMenuItem.getText());
    }

    @FXML
    private MenuItem startMenuItem;

    @FXML
    private void checkStatusStart() {
        statusSplitMenuButton.setText(startMenuItem.getText());
    }

    @FXML
    private MenuItem stopMenuItem;

    @FXML
    private void checkStatusStop() {
        statusSplitMenuButton.setText(stopMenuItem.getText());
    }

    @FXML
    private MenuItem endMenuItem;

    @FXML
    private void checkStatusEnd() {
        statusSplitMenuButton.setText(endMenuItem.getText());
    }

    private Boolean isNumber(String strNumber) {
        try {
            new BigInteger(strNumber);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    private Boolean checkConnect() {
        return ServiceFactory.ConnectService().connect().equals("OK");
    }

    @FXML
    private Label dateLabel;
    @FXML
    private Label sizeOrderLabel;
    @FXML
    private Label orderTermLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label stageLabel;

    private void clearSelectOrder() {
        statusSplitMenuButton.setText("");
        sizeOrderTextField.setText("");
        stageTextField.setText("");
        orderTermTextField.setText("");
        dateDatePicker.setValue(null);
        sizeOrderLabel.setText("");
        statusLabel.setText("");
        stageLabel.setText("");
        orderTermLabel.setText("");
        dateLabel.setText("");
    }

    private Boolean checkValueText() {
        boolean bool = true;

        if (clientNameLabel.getText().isEmpty()) {
            bool = false;
        }
        if (!isNumber(sizeOrderTextField.getText())) {
            sizeOrderLabel.setText("Please enter number");
            bool = false;
        } else {
            sizeOrderLabel.setText("");
        }
        if (statusSplitMenuButton.getText().isEmpty()) {
            statusLabel.setText("Please select term");
            bool = false;
        } else {
            statusLabel.setText("");
        }
        if (!isNumber(stageTextField.getText())) {
            stageLabel.setText("Please enter number");
            bool = false;
        } else {
            stageLabel.setText("");
        }
        if (!isNumber(orderTermTextField.getText())) {
            orderTermLabel.setText("Please enter number");
            bool = false;
        } else {
            orderTermLabel.setText("");
        }
        if (dateDatePicker.getValue() == null) {
            dateLabel.setText("Please select date");
            bool = false;
        } else {
            dateLabel.setText("");
        }
        return bool;
    }

    private String data;

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @FXML
    TableView<Order> tableAllOrder;

    @Override
    public void reloadWindow() {
        if (checkConnect()) {
            Client client = ServiceFactory.ClientServices().loadById(data);
            if (!(client == null)) {
                clientNameLabel.setText(client.getCompanyName());
            }
            List<Order> listOrder = ServiceFactory.OrderServices().loadAll();
            if (!(listOrder == null)) {
                ObservableList<Order> observableList;
                observableList = tableAllOrder.getItems();
                observableList.clear();
                for (Order order : listOrder) {
                    if (order.getClientId().equals(data)) {
                        observableList.add(order);
                    }
                }
                clearSelectOrder();
            }
        } else {
            showCheckConnectWindow();
        }
    }
}