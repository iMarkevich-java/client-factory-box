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

import java.util.List;

public class ControllerAllDBWindow implements DBWindow {

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

    private List<Client> clientsList = null;
    @FXML
    private TextArea clientTextArea;
    @FXML
    private TableView<Client> clientTableView;

    @FXML
    private void clientChange() {
        if (checkConnect()) {
            clientTextArea.setText("");
            ObservableList<Client> observableList;
            clientsList = ServiceFactory.ClientServices().loadAll();
            if (!(clientsList == null)) {
                observableList = clientTableView.getItems();
                observableList.clear();
                observableList.addAll(clientsList);
            }
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void setClient() {
        Client client = clientTableView.getSelectionModel().getSelectedItem();
        if (!(client == null)) {
            clientTextArea.setText(client.toString());
        }
    }

    @FXML
    private TextArea staffTextArea;
    @FXML
    private ImageView staffPhotoImageView;
    @FXML
    private TableView<Staff> staffTableView;

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
    private TableView<Day> staffDayTable;

    @FXML
    private void setStaff() {
        Staff staff = staffTableView.getSelectionModel().getSelectedItem();
        if (!(staff == null)) {
            staffTextArea.setText(staff.toString());
            staffPhotoImageView.setImage(new Image(staff.getPathPhoto()));
            StaffDays staffDays = ServiceFactory.StaffDayServices().loadById(staff.getId());
            if (!(staffDays == null)) {
                ObservableList<Day> observableListDay = staffDayTable.getItems();
                observableListDay.clear();
                observableListDay.addAll(staffDays.getListDay());
            }
        }

    }

    private List<Order> ordersList = null;
    @FXML
    private TextArea orderTextArea;
    @FXML
    private TableView<Order> orderTableView;

    @FXML
    private void ordersChange() {
        if (checkConnect()) {
            orderTextArea.setText("");
            ObservableList<Order> observableList;
            ordersList = ServiceFactory.OrderServices().loadAll();
            if (!(ordersList == null)) {
                observableList = orderTableView.getItems();
                observableList.clear();
                observableList.addAll(ordersList);
            }
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void setOrder() {
        Order order = orderTableView.getSelectionModel().getSelectedItem();
        if (!(order == null)) {
            orderTextArea.setText(order.toString());
        }
    }

    private List<Material> materialsList = null;
    @FXML
    private TextArea materialTextArea;
    @FXML
    private ImageView materialImageView;
    @FXML
    private TableView<Material> materialsTableView;

    @FXML
    private void materialsChange() {
        if (checkConnect()) {
            materialTextArea.setText("");
            materialImageView.setImage(null);
            ObservableList<Material> observableList;
            materialsList = ServiceFactory.MaterialServices().loadAll();
            if (!(materialsList == null)) {
                observableList = materialsTableView.getItems();
                observableList.clear();
                observableList.addAll(materialsList);
            }
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void setMaterials() {
        Material material = materialsTableView.getSelectionModel().getSelectedItem();
        if (!(material == null)) {
            materialTextArea.setText(material.toString());
            materialImageView.setImage(new Image(material.getPathImage()));
        }
    }

    private List<Product> productsList = null;
    @FXML
    private ImageView imageProductImageView;
    @FXML
    private TextArea productTextArea;
    @FXML
    private ImageView textureProductImageView;
    @FXML
    private TableView<Product> productTableView;

    @FXML
    private void productChange() {
        if (checkConnect()) {
            imageProductImageView.setImage(null);
            textureProductImageView.setImage(null);
            productTextArea.setText("");
            ObservableList<Product> observableList;
            productsList = ServiceFactory.ProductServices().loadAll();
            if (!(productsList == null)) {
                observableList = productTableView.getItems();
                observableList.clear();
                observableList.addAll(productsList);
            }
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void setProduct() {
        Product product = productTableView.getSelectionModel().getSelectedItem();
        if (!(product == null)) {
            productTextArea.setText(product.toString());
            imageProductImageView.setImage(new Image(product.getImage()));
            textureProductImageView.setImage(new Image(product.getTexture()));
        }
    }

    private List<Supplier> supplierList = null;
    @FXML
    private TextArea supplierTextArea;
    @FXML
    private TableView<Supplier> supplierTableView;

    @FXML
    private void supplierChange() {
        if (checkConnect()) {
            supplierTextArea.setText("");
            ObservableList<Supplier> observableList;
            supplierList = ServiceFactory.SupplierServices().loadAll();
            if (!(supplierList == null)) {
                observableList = supplierTableView.getItems();
                observableList.clear();
                observableList.addAll(supplierList);
            }
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void setSupplier() {
        Supplier supplier = supplierTableView.getSelectionModel().getSelectedItem();
        if (!(supplier == null)) {
            supplierTextArea.setText(supplier.toString());
        }
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
