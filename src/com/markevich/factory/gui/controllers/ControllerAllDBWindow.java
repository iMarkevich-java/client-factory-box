package com.markevich.factory.gui.controllers;

import businessObjectFactoryBox.*;
import com.markevich.factory.gui.common.AppWindows;
import com.markevich.factory.gui.common.CheckConnect;
import com.markevich.factory.gui.common.DBWindow;
import com.markevich.factory.gui.common.WindowConfigs;
import com.markevich.factory.service.ServiceFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ControllerAllDBWindow implements DBWindow {
    @FXML
    private TextArea clientTextArea;
    @FXML
    private TextArea staffTextArea;
    @FXML
    private TextArea orderTextArea;
    @FXML
    private TextArea productTextArea;
    @FXML
    private TextArea materialTextArea;
    @FXML
    private TextArea supplierTextArea;
    @FXML
    private ImageView imageProductImageView;
    @FXML
    private ImageView textureProductImageView;
    @FXML
    private ImageView staffPhotoImageView;
    @FXML
    private ImageView materialImageView;
    @FXML
    private TableView<Client> clientTableView;
    @FXML
    private TableView<Staff> staffTableView;
    @FXML
    private TableView<Day> staffDayTable;
    @FXML
    private TableView<Order> orderTableView;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableView<Material> materialsTableView;
    @FXML
    private TableView<Supplier> supplierTableView;
    private List<Client> clientsList = new ArrayList<>();
    private List<Order> ordersList = new ArrayList<>();
    private List<Product> productsList = new ArrayList<>();
    private List<Material> materialsList = new ArrayList<>();
    private List<Supplier> supplierList = new ArrayList<>();


    @FXML
    private void showMainWindow() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.showWindow(WindowConfigs.StartWindow);
        appWindows.reloadWindow(WindowConfigs.StartWindow);
    }

    private void showCheckConnectWindow() {
        CheckConnect checkConnect = new CheckConnect();
        checkConnect.createWindow();
    }

    @FXML
    private void clientChange() {
        if (checkConnect()) {
            clientTextArea.setText("");
            ObservableList<Client> observableList;
            clientsList = ServiceFactory.ClientServices().loadAll();
            observableList = clientTableView.getItems();
            observableList.clear();
            observableList.addAll(clientsList);
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void setClient() {
        Client client = clientTableView.getSelectionModel().getSelectedItem();
        clientTextArea.setText(client.toString());
    }

    @FXML
    private void staffChange() {
        if (checkConnect()) {
            staffTextArea.setText("");
            staffPhotoImageView.setImage(null);
            ObservableList<Staff> observableList;
            List<Staff> staffList = ServiceFactory.StaffServices().loadAll();
            observableList = staffTableView.getItems();
            observableList.clear();
            observableList.addAll(staffList);
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void setStaff() {
        Staff staff = staffTableView.getSelectionModel().getSelectedItem();
        staffTextArea.setText(staff.toString());
        staffPhotoImageView.setImage(new Image(staff.getPathPhoto()));
        StaffDays staffDays = ServiceFactory.StaffDayServices().loadById(staff.getId());
        ObservableList<Day> observableListDay = staffDayTable.getItems();
        observableListDay.clear();
        observableListDay.addAll(staffDays.getListDay());
    }

    @FXML
    private void ordersChange() {
        if (checkConnect()) {
            ObservableList<Order> observableList;
            ordersList = ServiceFactory.OrderServices().loadAll();
            observableList = orderTableView.getItems();
            observableList.clear();
            observableList.addAll(ordersList);
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void setOrder() {
        Order order = orderTableView.getSelectionModel().getSelectedItem();
        orderTextArea.setText(order.toString());
    }

    @FXML
    private void materialsChange() {
        if (checkConnect()) {
            ObservableList<Material> observableList;
            materialsList = ServiceFactory.MaterialServices().loadAll();
            observableList = materialsTableView.getItems();
            observableList.clear();
            observableList.addAll(materialsList);
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void setMaterials() {
        Material material = materialsTableView.getSelectionModel().getSelectedItem();
        materialTextArea.setText(material.toString());
        materialImageView.setImage(new Image(material.getPathImage()));
    }

    @FXML
    private void productChange() {
        if (checkConnect()) {
            ObservableList<Product> observableList;
            productsList = ServiceFactory.ProductServices().loadAll();
            observableList = productTableView.getItems();
            observableList.clear();
            observableList.addAll(productsList);
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void setProduct() {
        Product product = productTableView.getSelectionModel().getSelectedItem();
        productTextArea.setText(product.toString());
        imageProductImageView.setImage(new Image(product.getImage()));
        textureProductImageView.setImage(new Image(product.getTexture()));
    }

    @FXML
    private void supplierChange() {
        if (checkConnect()) {
            ObservableList<Supplier> observableList;
            supplierList = ServiceFactory.SupplierServices().loadAll();
            observableList = supplierTableView.getItems();
            observableList.clear();
            observableList.addAll(supplierList);
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void setSupplier() {
        Supplier supplier = supplierTableView.getSelectionModel().getSelectedItem();
        supplierTextArea.setText(supplier.toString());
    }

    private Boolean checkConnect() {
        return ServiceFactory.ConnectService().connect().equals("OK");
    }

    @Override
    public void setData(String data) {

    }

    @Override
    public void reloadWindow() {
    }
}
