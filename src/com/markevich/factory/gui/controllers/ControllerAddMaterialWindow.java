package com.markevich.factory.gui.controllers;

import businessObjectFactoryBox.Material;
import com.markevich.factory.gui.common.AppWindows;
import com.markevich.factory.gui.common.CheckConnect;
import com.markevich.factory.gui.common.DBWindow;
import com.markevich.factory.gui.common.WindowConfigs;
import com.markevich.factory.service.ServiceFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;

public class ControllerAddMaterialWindow implements DBWindow {

    @FXML
    private void showMainWindow() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.showWindow(WindowConfigs.StartWindow);
        appWindows.reloadWindow(WindowConfigs.StartWindow);
    }

    @FXML
    private void showSupplierMaterialsWindow() {
        if (checkConnect()) {
            AppWindows appWindows = AppWindows.getInstance();
            appWindows.showWindow(WindowConfigs.SupplierMaterialsWindow);
            appWindows.reloadWindow(WindowConfigs.SupplierMaterialsWindow);
        } else {
            showCheckConnectWindow();
        }

    }

    private void showCheckConnectWindow() {
        CheckConnect checkConnect = new CheckConnect();
        checkConnect.createWindow();
    }

    @FXML
    private ImageView imageImageView;
    private String urlImage;

    @FXML
    private void findImage() {
        if (checkConnect()) {
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            File file;
            try {
                file = fileChooser.showOpenDialog(stage).getAbsoluteFile();
                urlImage = file.toURI().toURL().toString();
            } catch (MalformedURLException | NullPointerException exception) {
                return;
            }
            Image image = new Image(urlImage);
            imageImageView.setImage(image);
        } else {
            showCheckConnectWindow();
        }

    }

    private String supplierId;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField unitTextField;
    @FXML
    private TextField sizeTextField;
    @FXML
    private SplitMenuButton materialNameSplitMenuButton;

    @FXML
    private void save() {
        if (checkConnect()) {
            if (checkValueText()) {
                Material material = new Material();
                material.setPathImage(urlImage);
                material.setSize(sizeTextField.getText());
                material.setUnit(unitTextField.getText());
                material.setAmount(amountTextField.getText());
                material.setPrice(priceTextField.getText());
                material.setMaterialName(materialNameSplitMenuButton.getText());
                material.setSupplierId(supplierId);
                material.setId("0");
                ServiceFactory.MaterialServices().save(material);
                reloadWindow();
            }
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void getLeatherMaterial() {
        if (checkConnect()) {
            materialNameSplitMenuButton.setText("Leather");
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void getCardboardMaterial() {
        if (checkConnect()) {
            materialNameSplitMenuButton.setText("Cardboard");
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void getMetalMaterial() {
        if (checkConnect()) {
            materialNameSplitMenuButton.setText("Metal");
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void getPlasticMaterial() {
        if (checkConnect()) {
            materialNameSplitMenuButton.setText("Plastic");
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void getWoodMaterial() {
        if (checkConnect()) {
            materialNameSplitMenuButton.setText("Wood");
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private Label priceLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label unitLabel;
    @FXML
    private Label sizeLabel;
    @FXML
    private Label imageLabel;
    @FXML
    private Label materialLabel;

    private Boolean checkValueText() {
        boolean bool = true;

        if (materialNameSplitMenuButton.getText().isEmpty()) {
            materialLabel.setText("Please select material");
            bool = false;
        } else {
            materialLabel.setText("");
        }
        if (!isNumber(priceTextField.getText())) {
            priceLabel.setText("Please enter price");
            bool = false;
        } else {
            priceLabel.setText("");
        }
        if (!isNumber(amountTextField.getText())) {
            amountLabel.setText("Please enter amount");
            bool = false;
        } else {
            amountLabel.setText("");
        }
        if (unitTextField.getText().isEmpty()) {
            unitLabel.setText("Please enter unit");
            bool = false;
        } else {
            unitLabel.setText("");
        }
        if (sizeTextField.getText().isEmpty()) {
            sizeLabel.setText("Please enter size");
            bool = false;
        } else {
            sizeLabel.setText("");
        }
        if (urlImage == null) {
            imageLabel.setText("Please enter image");
            bool = false;
        } else {
            imageLabel.setText("");
        }
        return bool;
    }

    private Boolean isNumber(String strNumber) {
        try {
            new BigDecimal(strNumber);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    private void clearSelect() {
        imageImageView.setImage(null);
        materialNameSplitMenuButton.setText("");
        materialLabel.setText("");
        priceTextField.setText("");
        priceLabel.setText("");
        amountTextField.setText("");
        amountLabel.setText("");
        unitTextField.setText("");
        unitLabel.setText("");
        sizeTextField.setText("");
        sizeLabel.setText("");
        urlImage = null;
        imageLabel.setText("");
    }

    private Boolean checkConnect() {
        return ServiceFactory.ConnectService().connect().equals("OK");
    }

    @Override
    public void setData(String supplierId) {
        this.supplierId = supplierId;
    }

    @FXML
    public TableView<Material> tableSupplierMaterials;

    @Override
    public void reloadWindow() {
        if (checkConnect()) {
            List<Material> list = ServiceFactory.MaterialServices().loadAll();
            ObservableList<Material> observableList;
            observableList = tableSupplierMaterials.getItems();
            observableList.clear();
            for (Material material : list) {
                if (material.getSupplierId().equals(supplierId)) {
                    observableList.add(material);
                }
            }
            clearSelect();
        } else {
            showCheckConnectWindow();
        }
    }
}