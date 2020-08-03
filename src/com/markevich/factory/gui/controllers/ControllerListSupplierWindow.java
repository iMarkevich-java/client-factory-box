package com.markevich.factory.gui.controllers;

import businessObjectFactoryBox.Material;
import businessObjectFactoryBox.Supplier;
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

public class ControllerListSupplierWindow implements DBWindow {

    @FXML
    private Button updateSupplierButton;
    @FXML
    private Button deleteSupplierButton;
    @FXML
    private Button supplierMaterialsButton;
    @FXML
    private TextField companyNameTextField;
    @FXML
    private TextArea legalDataTextField;
    @FXML
    private TextArea addressTextField;
    @FXML
    private TextField managerTextField;
    @FXML
    private TableView<Supplier> tableAllSupplier;

    @FXML
    private void showMainWindow() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.showWindow(WindowConfigs.StartWindow);
        appWindows.reloadWindow(WindowConfigs.StartWindow);
    }

    @FXML
    private void showAddSupplierWindow() {
        if (checkConnect()) {
            AppWindows appWindows = AppWindows.getInstance();
            appWindows.showWindow(WindowConfigs.SupplierAddWindow);
            appWindows.reloadWindow(WindowConfigs.SupplierAddWindow);
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void showMaterialsSupplierWindow() {
        if (checkConnect()) {
            Supplier supplier = tableAllSupplier.getSelectionModel().getSelectedItem();
            if (!(supplier == null)) {
                AppWindows appWindows = AppWindows.getInstance();
                appWindows.showWindow(WindowConfigs.SupplierMaterialsWindow);
                appWindows.getWindowByConfig(WindowConfigs.SupplierMaterialsWindow).getController().setData(supplier.getId());
                appWindows.reloadWindow(WindowConfigs.SupplierMaterialsWindow);
            }
        } else {
            showCheckConnectWindow();
        }
    }

    private void showCheckConnectWindow() {
        CheckConnect checkConnect = new CheckConnect();
        checkConnect.createWindow();
    }

    public void selectClient() {
        Supplier supplier = tableAllSupplier.getSelectionModel().getSelectedItem();
        if (!(supplier == null)) {
            updateSupplierButton.setDisable(false);
            deleteSupplierButton.setDisable(false);
            supplierMaterialsButton.setDisable(false);
            companyNameTextField.setDisable(false);
            legalDataTextField.setDisable(false);
            addressTextField.setDisable(false);
            managerTextField.setDisable(false);
            companyNameTextField.setText(supplier.getCompanyName());
            legalDataTextField.setText(supplier.getLegalData());
            addressTextField.setText(supplier.getAddress());
            managerTextField.setText(supplier.getManager());
        }
    }

    private void clearSelectClient() {
        companyNameTextField.setText("");
        legalDataTextField.setText("");
        addressTextField.setText("");
        managerTextField.setText("");
    }

    public void update() {
        if (checkConnect()) {
            Supplier supplier = tableAllSupplier.getSelectionModel().getSelectedItem();
            if (!(supplier == null)) {
                if (!(managerTextField.getText().isEmpty())) {
                    supplier.setManager(managerTextField.getText());
                }
                if (!companyNameTextField.getText().isEmpty()) {
                    supplier.setCompanyName(companyNameTextField.getText());
                }
                if (!addressTextField.getText().isEmpty()) {
                    supplier.setAddress(addressTextField.getText());
                }
                if (!legalDataTextField.getText().isEmpty()) {
                    supplier.setLegalData(legalDataTextField.getText());
                }

                ServiceFactory.SupplierServices().update(supplier);
                reloadWindow();
            }
        } else {
            showCheckConnectWindow();
        }
    }

    public void delete() {
        if (checkConnect()) {
            Supplier supplier = tableAllSupplier.getSelectionModel().getSelectedItem();
            if (!(supplier == null)) {
                List<Material> materialList = ServiceFactory.MaterialServices().loadAll();
                for (Material material : materialList) {
                    if (material.getSupplierId().equals(supplier.getId())) {
                        ServiceFactory.MaterialServices().delete(material.getId());
                    }
                }
                ServiceFactory.SupplierServices().delete(supplier.getId());
                reloadWindow();
            }
        } else {
            showCheckConnectWindow();
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
        if (checkConnect()) {
            updateSupplierButton.setDisable(true);
            deleteSupplierButton.setDisable(true);
            supplierMaterialsButton.setDisable(true);
            companyNameTextField.setDisable(true);
            legalDataTextField.setDisable(true);
            addressTextField.setDisable(true);
            managerTextField.setDisable(true);
            List<Supplier> listSupplier = ServiceFactory.SupplierServices().loadAll();
            ObservableList<Supplier> observableList;
            if (!(listSupplier == null)) {
                observableList = tableAllSupplier.getItems();
                observableList.clear();
                clearSelectClient();
                observableList.addAll(listSupplier);
            }
        } else {
            showCheckConnectWindow();
        }
    }
}