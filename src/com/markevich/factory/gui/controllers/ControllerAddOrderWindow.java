package com.markevich.factory.gui.controllers;

import biznesObgectFactory.Client;
import biznesObgectFactory.Order;
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
    private DatePicker dateDatePicker;
    @FXML
    private Label dateLabel;
    @FXML
    private Label clientNameLabel;
    @FXML
    private TextField sizeOrderTextField;
    @FXML
    private Label sizeOrderLabel;
    @FXML
    private TextField orderTermTextField;
    @FXML
    private Label orderTermLabel;
    @FXML
    private SplitMenuButton statusSplitMenuButton;
    @FXML
    private MenuItem waitMenuItem;
    @FXML
    private MenuItem startMenuItem;
    @FXML
    private MenuItem stopMenuItem;
    @FXML
    private MenuItem endMenuItem;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField stageTextField;
    @FXML
    private Label stageLabel;
    @FXML
    private TableView<Order> tableAllOrder;
    private ObservableList<Order> observableList;
    private String data;

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

    public void checkStatusWait() {
        statusSplitMenuButton.setText(waitMenuItem.getText());
    }

    public void checkStatusStart() {
        statusSplitMenuButton.setText(startMenuItem.getText());
    }

    public void checkStatusStop() {
        statusSplitMenuButton.setText(stopMenuItem.getText());
    }

    public void checkStatusEnd() {
        statusSplitMenuButton.setText(endMenuItem.getText());
    }

    public void clearSelectOrder() {
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
        Boolean bool = true;

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

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public void reloadWindow() {
        if (checkConnect()) {
            Client client = ServiceFactory.ClientServices().loadById(data);
            clientNameLabel.setText(client.getCompanyName());
            List<Order> listOrder = ServiceFactory.OrderServices().loadAll();
            observableList = tableAllOrder.getItems();
            observableList.clear();
            for (Order order : listOrder) {
                if (order.getClientId().equals(data)) {
                    observableList.add(order);
                }
            }
            clearSelectOrder();
        } else {
            showCheckConnectWindow();
        }
    }
}