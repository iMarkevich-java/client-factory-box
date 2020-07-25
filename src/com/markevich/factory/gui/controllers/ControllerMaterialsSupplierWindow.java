package com.markevich.factory.gui.controllers;

import businessObjectFactoryBox.Material;
import com.markevich.factory.gui.common.AppWindows;
import com.markevich.factory.gui.common.CheckConnect;
import com.markevich.factory.gui.common.DBWindow;
import com.markevich.factory.gui.common.WindowConfigs;
import com.markevich.factory.service.ServiceFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;

public class ControllerMaterialsSupplierWindow implements DBWindow {
    @FXML
    private Button findImageButton;
    @FXML
    private ImageView imageImageView;
    @FXML
    private TextField sizeTextField;
    @FXML
    private TextField unitTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private SplitMenuButton materialNameSplitMenuButton;
    @FXML
    private TextField priceTextField;
    @FXML
    private Label unitLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label sizeLabel;
    @FXML
    private TableView<Material> tableSupplierMaterials;
    private ObservableList<Material> observableList;
    private String supplierId;
    private String urlImage;
    private FileChooser fileChooser;

    @FXML
    private void showMainWindow() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.showWindow(WindowConfigs.StartWindow);
        appWindows.reloadWindow(WindowConfigs.StartWindow);
    }

    @FXML
    private void showAddMaterialWindow() {
        if (checkConnect()) {
            if (!(supplierId == null)) {
                AppWindows appWindows = AppWindows.getInstance();
                appWindows.showWindow(WindowConfigs.MaterialAddWindow);
                appWindows.getWindowByConfig(WindowConfigs.MaterialAddWindow).getController().setData(supplierId);
                appWindows.reloadWindow(WindowConfigs.MaterialAddWindow);
            }
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void showSupplierListWindow() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.showWindow(WindowConfigs.SupplierListWindow);
        appWindows.reloadWindow(WindowConfigs.SupplierListWindow);
    }

    private void showCheckConnectWindow() {
        CheckConnect checkConnect = new CheckConnect();
        checkConnect.createWindow();
    }

    @FXML
    private void selectMaterial() {
        Material material = tableSupplierMaterials.getSelectionModel().getSelectedItem();
        if (material != null) {
            materialNameSplitMenuButton.setDisable(false);
            priceTextField.setDisable(false);
            amountTextField.setDisable(false);
            unitTextField.setDisable(false);
            sizeTextField.setDisable(false);
            findImageButton.setDisable(false);

            materialNameSplitMenuButton.setText(material.getMaterialName());
            priceTextField.setText(material.getPrice());
            amountTextField.setText(material.getAmount());
            unitTextField.setText(material.getUnit());
            sizeTextField.setText(material.getSize());
            imageImageView.setImage(new Image(material.getPathImage()));
        }
    }

    private Boolean isNumber(String strNumber) {
        try {
            new BigDecimal(strNumber);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    @FXML
    private void update() {
        if (checkConnect()) {
            Material material = tableSupplierMaterials.getSelectionModel().getSelectedItem();
            if (!(material == null)) {
                boolean bool = true;
                if (isNumber(sizeTextField.getText())) {
                    material.setSize(sizeTextField.getText());
                } else {
                    sizeLabel.setText("Please enter size");
                    bool = false;
                }
                if (!unitTextField.getText().isEmpty()) {
                    material.setUnit(unitTextField.getText());
                } else {
                    unitLabel.setText("Please enter unit");
                    bool = false;
                }
                if (isNumber(amountTextField.getText())) {
                    material.setAmount(amountTextField.getText());
                } else {
                    amountLabel.setText("Please enter number");
                    bool = false;
                }
                if (isNumber(priceTextField.getText())) {
                    material.setPrice(priceTextField.getText());
                } else {
                    priceLabel.setText("Please enter number");
                    bool = false;
                }
                if (!(materialNameSplitMenuButton.getText().isEmpty())) {
                    material.setMaterialName(materialNameSplitMenuButton.getText());
                }
                if (!(supplierId == null)) {
                    material.setSupplierId(supplierId);
                }
                if (!(urlImage == null)) {
                    material.setPathImage(urlImage);
                }
                if (bool) {
                    ServiceFactory.MaterialServices().update(material);
                    reloadWindow();
                }
            }
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void findImage() {
        if (checkConnect()) {
            Stage stage = new Stage();
            fileChooser = new FileChooser();
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

    private void clearSelect() {
        imageImageView.setImage(null);
        materialNameSplitMenuButton.setText("");
        priceTextField.setText("");
        amountTextField.setText("");
        unitTextField.setText("");
        sizeTextField.setText("");
        urlImage = null;
    }

    @FXML
    private void getLeatherMaterial() {
        materialNameSplitMenuButton.setText("Leather");
    }

    @FXML
    private void getCardboardMaterial() {
        materialNameSplitMenuButton.setText("Cardboard");
    }

    @FXML
    private void getMetalMaterial() {
        materialNameSplitMenuButton.setText("Metal");
    }

    @FXML
    private void getPlasticMaterial() {
        materialNameSplitMenuButton.setText("Plastic");
    }

    @FXML
    private void getWoodMaterial() {
        materialNameSplitMenuButton.setText("Wood");
    }


    @FXML
    private void delete() {
        if (checkConnect()) {
            Material material = tableSupplierMaterials.getSelectionModel().getSelectedItem();
            if (!(material == null)) {
                ServiceFactory.MaterialServices().delete(material.getId());
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
    public void setData(String supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public void reloadWindow() {
        if (checkConnect()) {
            materialNameSplitMenuButton.setDisable(true);
            priceTextField.setDisable(true);
            amountTextField.setDisable(true);
            unitTextField.setDisable(true);
            sizeTextField.setDisable(true);
            findImageButton.setDisable(true);
            List<Material> listMaterial = ServiceFactory.MaterialServices().loadAll();
            observableList = tableSupplierMaterials.getItems();
            observableList.clear();
            for (Material material : listMaterial) {
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