package com.markevich.factory.gui.controllers;

import biznesObgectFactory.Client;
import biznesObgectFactory.Order;
import biznesObgectFactory.Product;
import com.markevich.factory.gui.common.AppWindows;
import com.markevich.factory.gui.common.CheckConnect;
import com.markevich.factory.gui.common.DBWindow;
import com.markevich.factory.gui.common.WindowConfigs;
import com.markevich.factory.service.ServiceFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class ControllerOrdersClientWindow implements DBWindow {
    @FXML
    private Button deleteButton;
    @FXML
    private Button orderProductButton;
    @FXML
    private Button updateButton;
    @FXML
    private Label startDateLabel;
    @FXML
    private TextField orderTermTextField;
    @FXML
    private Label endDateLabel;
    @FXML
    private Label orderNameLabel;
    @FXML
    private TextField sizeOrderTextField;
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
    private TextField stageTextField;
    @FXML
    private TableView<Order> tableOrdersClient;
    private ObservableList<Order> observableList;
    private String data;
    private LocalDate startDate;

    @FXML
    private void showMainWindow() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.showWindow(WindowConfigs.StartWindow);
        appWindows.reloadWindow(WindowConfigs.StartWindow);
    }

    @FXML
    private void showAddOrderWindow() {
        if (checkConnect()) {
            AppWindows appWindows = AppWindows.getInstance();
            appWindows.showWindow(WindowConfigs.OrderAddWindow);
            appWindows.getWindowByConfig(WindowConfigs.OrderAddWindow).getController().setData(data);
            appWindows.reloadWindow(WindowConfigs.OrderAddWindow);
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void showAddProductWindow() {
        if (checkConnect()) {
            Order order = tableOrdersClient.getSelectionModel().getSelectedItem();
            if (!(order == null)) {
                AppWindows appWindows = AppWindows.getInstance();
                appWindows.showWindow(WindowConfigs.ProductAddWindow);
                appWindows.getWindowByConfig(WindowConfigs.ProductAddWindow).getController().setData(order.getId());
                appWindows.reloadWindow(WindowConfigs.ProductAddWindow);
            }
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void showClientListWindow() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.showWindow(WindowConfigs.ClientListWindow);
        appWindows.reloadWindow(WindowConfigs.ClientListWindow);
    }

    private void showCheckConnectWindow() {
        CheckConnect checkConnect = new CheckConnect();
        checkConnect.createWindow();
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
            orderTermTextField.setDisable(true);
            sizeOrderTextField.setDisable(true);
            statusSplitMenuButton.setDisable(true);
            stageTextField.setDisable(true);
            orderProductButton.setDisable(true);
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
            Client client = ServiceFactory.ClientServices().loadById(data);
            orderNameLabel.setText(client.getCompanyName());
            List<Order> listOrder = ServiceFactory.OrderServices().loadAll();
            observableList = tableOrdersClient.getItems();
            observableList.clear();
            for (Order order : listOrder) {
                if (order.getClientId().equals(data)) {
                    observableList.add(order);
                }
            }
            clearSelectOrder();
            tableOrdersClient.refresh();
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void checkStatusWait() {
        statusSplitMenuButton.setText(waitMenuItem.getText());
    }

    @FXML
    private void checkStatusStart() {
        statusSplitMenuButton.setText(startMenuItem.getText());
    }

    @FXML
    private void checkStatusStop() {
        statusSplitMenuButton.setText(stopMenuItem.getText());
    }

    @FXML
    private void checkStatusEnd() {
        statusSplitMenuButton.setText(endMenuItem.getText());
    }

    @FXML
    private void selectOrder() {
        Order order = tableOrdersClient.getSelectionModel().getSelectedItem();
        if (order != null) {
            orderTermTextField.setDisable(false);
            sizeOrderTextField.setDisable(false);
            statusSplitMenuButton.setDisable(false);
            stageTextField.setDisable(false);
            orderProductButton.setDisable(false);
            updateButton.setDisable(false);
            deleteButton.setDisable(false);
            sizeOrderTextField.setText(String.valueOf(order.getSizeOrder()));
            statusSplitMenuButton.setText(order.getStatus());
            stageTextField.setText(order.getStage());
            startDateLabel.setText(order.getStartDate());
            orderTermTextField.setText(order.getOrderTerm());
            startDate = order.getStartDateLocalDate();
            endDateLabel.setText(startDate.plusDays(Long.parseLong(order.getOrderTerm())).toString());
        }
    }

    @FXML
    private void update() {
        if (checkConnect()) {
            Order order = tableOrdersClient.getSelectionModel().getSelectedItem();
            if (!(order == null)) {
                order.setStatus(statusSplitMenuButton.getText());
                order.setStage(stageTextField.getText());
                order.setSizeOrder(sizeOrderTextField.getText());
                order.setOrderTerm(orderTermTextField.getText());
                order.setStartDate(startDateLabel.getText());
                order.setOrderName(orderNameLabel.getText());
                ServiceFactory.OrderServices().update(order);
                reloadWindow();
            }
        } else {
            showCheckConnectWindow();
        }
    }

    private void clearSelectOrder() {
        sizeOrderTextField.setText("");
        statusSplitMenuButton.setText("");
        stageTextField.setText("");
        startDateLabel.setText("");
        orderTermTextField.setText("");
        endDateLabel.setText("");
    }

    @FXML
    private void delete() {
        if (checkConnect()) {
            Order order = tableOrdersClient.getSelectionModel().getSelectedItem();
            if (!(order == null)) {
                List<Product> productList = ServiceFactory.ProductServices().loadAll();
                for (Product product : productList) {
                    if (order.getId().equals(product.getOrderId())) {
                        ServiceFactory.ProductServices().delete(product.getId());
                    }
                }
                ServiceFactory.OrderServices().delete(order.getId());
                reloadWindow();
            }
        } else {
            showCheckConnectWindow();
        }
    }
}