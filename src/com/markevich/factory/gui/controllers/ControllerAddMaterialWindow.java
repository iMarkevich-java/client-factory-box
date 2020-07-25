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
import java.net.MalformedURLException;
import java.util.List;

public class ControllerAddMaterialWindow implements DBWindow {
    @FXML
    public TableView<Material> tableSupplierMaterials;
    @FXML
    private SplitMenuButton materialNameSplitMenuButton;
    @FXML
    private ImageView imageImageView;
    @FXML
    private Label materialLabel;
    @FXML
    private TextField priceTextField;
    @FXML
    private Label priceLabel;
    @FXML
    private TextField amountTextField;
    @FXML
    private Label amountLabel;
    @FXML
    private TextField unitTextField;
    @FXML
    private Label unitLabel;
    @FXML
    private TextField sizeTextField;
    @FXML
    private Label sizeLabel;
    @FXML
    private Label imageLabel;
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
    private void showSupplierMaterialsWindow() {
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

    @Override
    public void setData(String supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public void reloadWindow() {
        if (checkConnect()) {
            List<Material> list = ServiceFactory.MaterialServices().loadAll();
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

    private Boolean checkValueText() {
        boolean bool = true;

        if (materialNameSplitMenuButton.getText().isEmpty()) {
            materialLabel.setText("Please select material");
            bool = false;
        } else {
            materialLabel.setText("");
        }
        if (priceTextField.getText().isEmpty()) {
            priceLabel.setText("Please enter price");
            bool = false;
        } else {
            priceLabel.setText("");
        }
        if (amountTextField.getText().isEmpty()) {
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

    private Boolean checkConnect() {
        return ServiceFactory.ConnectService().connect().equals("OK");
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
}