package com.markevich.factory.gui.controllers;

import biznesObgectFactory.Order;
import biznesObgectFactory.Product;
import com.markevich.factory.gui.common.AppWindows;
import com.markevich.factory.gui.common.CheckConnect;
import com.markevich.factory.gui.common.DBWindow;
import com.markevich.factory.gui.common.WindowConfigs;
import com.markevich.factory.service.ServiceFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

public class ControllerAddProductWindow implements DBWindow {

    @FXML
    private SplitMenuButton sizeBoxSplitMenuButton;
    @FXML
    private SplitMenuButton materialSplitMenuButton;
    @FXML
    private ImageView textureImageView;
    @FXML
    private ImageView textureProductImageView;
    @FXML
    private ImageView imageImageView;
    @FXML
    private ImageView imageProductImageView;
    @FXML
    private Label orderNameLabel;
    @FXML
    private Label sizeBoxLabel;
    @FXML
    private Label imageLabel;
    @FXML
    private Label textureLabel;
    @FXML
    private Label sizeBoxProductLabel;
    @FXML
    private Label dimensionTextField;
    @FXML
    private Label productIdLabel;
    @FXML
    private Label colorLabel;
    @FXML
    private Label colorProductLabel;
    @FXML
    private Label materialLabel;
    @FXML
    private Label materialProductLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button updateButton;
    @FXML
    private ColorPicker colorColorPicker;
    private String orderId;
    private String urlImage;
    private String urlTexture;
    private FileChooser fileChooser;

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
            appWindows.reloadWindow(WindowConfigs.OrdersClientWindow);
        } else {
            showCheckConnectWindow();
        }
    }

    private void showCheckConnectWindow() {
        CheckConnect checkConnect = new CheckConnect();
        checkConnect.createWindow();
    }

    @Override
    public void setData(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public void reloadWindow() {
        if (checkConnect()) {
            Order order = ServiceFactory.OrderServices().loadById(orderId);
            orderNameLabel.setText(order.getOrderName());
            clearSelectOrder();
            saveButton.setDisable(false);
            updateButton.setDisable(true);
            List<Product> list = ServiceFactory.ProductServices().loadAll();
            for (Product product : list) {
                if (product.getOrderId().equals(orderId)) {
                    saveButton.setDisable(true);
                    updateButton.setDisable(false);
                    dimensionTextField.setText(product.getDimensions());
                    sizeBoxProductLabel.setText(product.getSizeBox());
                    materialProductLabel.setText(product.getMaterial());
                    colorProductLabel.setText(product.getColor());
                    colorProductLabel.setTextFill(Paint.valueOf(product.getColor()));
                    productIdLabel.setText(product.getId());
                    if (!(product.getImage() == null)) {
                        imageProductImageView.setImage(new Image(product.getImage()));
                    }
                    if (!(product.getTexture() == null)) {
                        textureProductImageView.setImage(new Image(product.getTexture()));
                    }
                    break;
                }
            }
        } else {
            showCheckConnectWindow();
        }
    }

    private Boolean checkValueText() {
        Boolean bool = true;

        if (colorColorPicker.getValue() == null) {
            colorLabel.setText("Please select color");
            bool = false;
        } else {
            colorLabel.setText("");
        }
        if (sizeBoxSplitMenuButton.getText().isEmpty()) {
            sizeBoxLabel.setText("Please select size");
            bool = false;
        } else {
            sizeBoxLabel.setText("");
        }
        if (materialSplitMenuButton.getText().isEmpty()) {
            materialLabel.setText("Please select material");
            bool = false;
        } else {
            materialLabel.setText("");
        }
        if (urlImage == null) {
            imageLabel.setText("Please enter image");
            bool = false;
        } else {
            imageLabel.setText("");
        }
        if (urlTexture == null) {
            textureLabel.setText("Please select texture");
            bool = false;
        } else {
            textureLabel.setText("");
        }
        return bool;
    }

    @FXML
    private void save() {
        if (checkConnect()) {
            if (checkValueText()) {
                Product product = new Product();
                product.setColor(colorColorPicker.getValue().toString());
                product.setMaterial(materialSplitMenuButton.getText());
                product.setTexture(urlImage);
                product.setImage(urlTexture);
                product.setSizeBox(sizeBoxSplitMenuButton.getText());
                product.setOrderId(orderId);
                product.setId("0");
                ServiceFactory.ProductServices().save(product);
                reloadWindow();
            }
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void update() {
        if (checkConnect()) {
            if (!productIdLabel.getText().isEmpty()) {
                Product product = ServiceFactory.ProductServices().loadById(productIdLabel.getText());
                if (!(colorColorPicker.getValue() == null)) {
                    product.setColor(colorColorPicker.getValue().toString());
                }
                if (!materialSplitMenuButton.getText().isEmpty()) {
                    product.setMaterial(materialSplitMenuButton.getText());
                }
                if (!sizeBoxSplitMenuButton.getText().isEmpty()) {
                    product.setSizeBox(sizeBoxSplitMenuButton.getText());
                }
                if (!(urlImage == null)) {
                    product.setImage(urlImage);
                }
                if (!(urlTexture == null)) {
                    product.setTexture(urlTexture);
                }
                product.setOrderId(orderId);
                ServiceFactory.ProductServices().update(product);
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

    @FXML
    private void findTexture() {
        if (checkConnect()) {
            Stage stage = new Stage();
            fileChooser = new FileChooser();
            File file;
            try {
                file = fileChooser.showOpenDialog(stage).getAbsoluteFile();
                urlTexture = file.toURI().toURL().toString();
            } catch (MalformedURLException | NullPointerException exception) {
                return;
            }
            Image image = new Image(urlTexture);
            textureImageView.setImage(image);
        } else {
            showCheckConnectWindow();
        }
    }

    private void clearSelectOrder() {
        colorColorPicker.setValue(null);
        imageImageView.setImage(null);
        textureImageView.setImage(null);
        materialSplitMenuButton.setText("");
        sizeBoxSplitMenuButton.setText("");
        saveButton.setDisable(false);
        dimensionTextField.setText("");
        sizeBoxProductLabel.setText("");
        sizeBoxLabel.setText("");
        materialProductLabel.setText("");
        materialLabel.setText("");
        colorProductLabel.setText("");
        colorLabel.setText("");
        productIdLabel.setText("");
        textureProductImageView.setImage(null);
        imageProductImageView.setImage(null);
        textureLabel.setText("");
        imageLabel.setText("");
    }

    private Boolean checkConnect() {
        return ServiceFactory.ConnectService().connect().equals("OK");
    }

    @FXML
    private void getMaximumSize() {
        sizeBoxSplitMenuButton.setText("maximum");
    }

    @FXML
    private void getMiddleSize() {
        sizeBoxSplitMenuButton.setText("middle");
    }

    @FXML
    private void getMinimumSize() {
        sizeBoxSplitMenuButton.setText("minimum");
    }

    @FXML
    private void getLeatherMaterial() {
        materialSplitMenuButton.setText("leather");
    }

    @FXML
    private void getCardboardMaterial() {
        materialSplitMenuButton.setText("cardboard");
    }

    @FXML
    private void getMetalMaterial() {
        materialSplitMenuButton.setText("metal");
    }

    @FXML
    private void getPlasticMaterial() {
        materialSplitMenuButton.setText("plastic");
    }

    @FXML
    private void getWoodMaterial() {
        materialSplitMenuButton.setText("wod");
    }
}