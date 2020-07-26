package com.markevich.factory.gui.controllers;

import businessObjectFactoryBox.Client;
import businessObjectFactoryBox.Order;
import businessObjectFactoryBox.Product;
import com.markevich.factory.gui.common.AppWindows;
import com.markevich.factory.gui.common.CheckConnect;
import com.markevich.factory.gui.common.DBWindow;
import com.markevich.factory.gui.common.WindowConfigs;
import com.markevich.factory.service.ServiceFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

public class ControllerListClientWindow implements DBWindow {

    @FXML
    private void showMainWindow() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.showWindow(WindowConfigs.StartWindow);
        appWindows.reloadWindow(WindowConfigs.StartWindow);
    }

    @FXML
    private void showAddClientWindow() {
        if (checkConnect()) {
            AppWindows appWindows = AppWindows.getInstance();
            appWindows.showWindow(WindowConfigs.ClientAddWindow);
            appWindows.reloadWindow(WindowConfigs.ClientAddWindow);
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void showOrdersClientWindow() {
        if (checkConnect()) {
            Client client = tableAllClient.getSelectionModel().getSelectedItem();
            if (!(client == null)) {
                AppWindows appWindows = AppWindows.getInstance();
                appWindows.showWindow(WindowConfigs.OrdersClientWindow);
                appWindows.getWindowByConfig(WindowConfigs.OrdersClientWindow).getController().setData(client.getId());
                appWindows.reloadWindow(WindowConfigs.OrdersClientWindow);
            }
        } else {
            showCheckConnectWindow();
        }
    }

    private void showCheckConnectWindow() {
        CheckConnect checkConnect = new CheckConnect();
        checkConnect.createWindow();
    }

    @FXML
    private Button updateButton;
    @FXML
    private Button ordersClientButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField companyNameTextField;
    @FXML
    private TextArea legalDataTextField;
    @FXML
    private TextArea addressTextField;
    @FXML
    private TextField managerTextField;

    @FXML
    private void selectClient() {
        Client client = tableAllClient.getSelectionModel().getSelectedItem();
        if (!(client == null)) {
            updateButton.setDisable(false);
            deleteButton.setDisable(false);
            ordersClientButton.setDisable(false);
            companyNameTextField.setDisable(false);
            legalDataTextField.setDisable(false);
            addressTextField.setDisable(false);
            managerTextField.setDisable(false);
            companyNameTextField.setText(client.getCompanyName());
            legalDataTextField.setText(client.getLegalData());
            addressTextField.setText(client.getAddress());
            managerTextField.setText(client.getManager());
            tableAllClient.refresh();
        }
    }

    @FXML
    private void update() {
        if (checkConnect()) {
            Client client = tableAllClient.getSelectionModel().getSelectedItem();
            if (!(client == null)) {
                if (!(companyNameTextField.getText().isEmpty())) {
                    client.setCompanyName(companyNameTextField.getText());
                }
                if (!(addressTextField.getText().isEmpty())) {
                    client.setAddress(addressTextField.getText());

                }
                if (!(managerTextField.getText().isEmpty())) {
                    client.setManager(managerTextField.getText());

                }
                if (!(legalDataTextField.getText().isEmpty())) {
                    client.setLegalData(legalDataTextField.getText());
                }
                ServiceFactory.ClientServices().update(client);
                reloadWindow();
            }
        } else {
            showCheckConnectWindow();
        }
    }

    private void clearSelectClient() {
        companyNameTextField.setText("");
        legalDataTextField.setText("");
        addressTextField.setText("");
        managerTextField.setText("");
        tableAllClient.refresh();
    }

    @FXML
    private void delete() {
        if (checkConnect()) {
            Client client = tableAllClient.getSelectionModel().getSelectedItem();
            if (!(client == null)) {
                List<Order> orderList = ServiceFactory.OrderServices().loadAll();
                for (Order order : orderList) {
                    if (client.getId().equals(order.getClientId())) {
                        List<Product> productList = ServiceFactory.ProductServices().loadAll();
                        for (Product product : productList) {
                            if (order.getId().equals(product.getOrderId())) {
                                ServiceFactory.ProductServices().delete(product.getId());
                            }
                        }
                        ServiceFactory.OrderServices().delete(order.getId());
                    }
                }
                ServiceFactory.ClientServices().delete(client.getId());
                reloadWindow();
            }
        } else {
            showCheckConnectWindow();
        }
    }

    private Boolean checkConnect() {
        return ServiceFactory.ConnectService().connect().equals("OK");
    }

    private String data;

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @FXML
    private TableView<Client> tableAllClient;

    @Override
    public void reloadWindow() {
        if (checkConnect()) {
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
            ordersClientButton.setDisable(true);
            companyNameTextField.setDisable(true);
            legalDataTextField.setDisable(true);
            addressTextField.setDisable(true);
            managerTextField.setDisable(true);
            ObservableList<Client> observableList;
            List<Client> listClient = ServiceFactory.ClientServices().loadAll();
            if(!(listClient == null)) {
                observableList = tableAllClient.getItems();
                observableList.clear();
                clearSelectClient();
                observableList.addAll(listClient);
            }
        } else {
            showCheckConnectWindow();
        }
    }
}