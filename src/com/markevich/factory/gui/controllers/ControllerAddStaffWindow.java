package com.markevich.factory.gui.controllers;


import biznesObgectFactory.Staff;
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
import java.util.ArrayList;
import java.util.List;

public class ControllerAddStaffWindow implements DBWindow {
    @FXML
    private ImageView photoImageView;
    @FXML
    private Label photoLabel;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private Label lastNameLabel;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private Label firstNameLabel;
    @FXML
    private SplitMenuButton positionSplitMenuButton;
    @FXML
    private MenuItem directorMenuItem;
    @FXML
    private MenuItem accountantMenuItem;
    @FXML
    private MenuItem workerMenuItem;
    @FXML
    private MenuItem managerMenuItem;
    @FXML
    private Label positionLabel;
    @FXML
    private DatePicker dateOfBirthDatePicker;
    @FXML
    private Label datePickerLabel;
    @FXML
    private TextField salaryTextField;
    @FXML
    private Label salaryLabel;
    @FXML
    private SplitMenuButton departmentSplitMenuButton;
    @FXML
    private MenuItem manufacturingMenuItem;
    @FXML
    private MenuItem accountingMenuItem;
    @FXML
    private MenuItem technicalMenuItem;
    @FXML
    private MenuItem marketingMenuItem;
    @FXML
    private Label departmentLabel;
    @FXML
    private TextArea addressTextField;
    @FXML
    private Label addressLabel;
    @FXML
    private TableView<Staff> tableAllStaff;
    private ObservableList<Staff> observableList;
    private List<Staff> list = new ArrayList<>();
    private String urlPhoto;

    @FXML
    private void showMainWindow() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.showWindow(WindowConfigs.StartWindow);
        appWindows.reloadWindow(WindowConfigs.StartWindow);
    }

    @FXML
    private void showStaffListWindow() {
        if (checkConnect()) {
            AppWindows appWindows = AppWindows.getInstance();
            appWindows.showWindow(WindowConfigs.StaffListWindow);
            appWindows.reloadWindow(WindowConfigs.StaffListWindow);
        } else {
            showCheckConnectWindow();
        }
    }

    private void showCheckConnectWindow() {
        CheckConnect checkConnect = new CheckConnect();
        checkConnect.createWindow();
    }

    private Boolean isNumber(String strNumber) {
        try {
            new BigDecimal(strNumber);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    private Boolean checkValueText() {
        Boolean bool = true;

        if (urlPhoto == null) {
            photoLabel.setText("Please select date");
            bool = false;
        } else {
            photoLabel.setText("");
        }
        if (lastNameTextField.getText().isEmpty()) {
            lastNameLabel.setText("Please enter text");
            bool = false;
        } else {
            lastNameLabel.setText("");
        }
        if (firstNameTextField.getText().isEmpty()) {
            firstNameLabel.setText("Please enter text");
            bool = false;
        } else {
            firstNameLabel.setText("");
        }
        if (positionSplitMenuButton.getText().isEmpty()) {
            positionLabel.setText("Please select position");
            bool = false;
        } else {
            positionLabel.setText("");
        }
        if (dateOfBirthDatePicker.getValue() == null) {
            datePickerLabel.setText("Please select date");
            bool = false;
        } else {
            datePickerLabel.setText("");
        }
        if (!isNumber(salaryTextField.getText())) {
            salaryLabel.setText("Please enter number");
            bool = false;
        } else {
            salaryLabel.setText("");
        }
        if (departmentSplitMenuButton.getText().isEmpty()) {
            departmentLabel.setText("Please select department");
            bool = false;
        } else {
            departmentLabel.setText("");
        }
        if (addressTextField.getText().isEmpty()) {
            addressLabel.setText("Please enter text");
            bool = false;
        } else {
            addressLabel.setText("");
        }
        return bool;
    }

    @FXML
    private void findPhoto() {
        if (checkConnect()) {
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            File file;
            try {
                file = fileChooser.showOpenDialog(stage).getAbsoluteFile();
                urlPhoto = file.toURI().toURL().toString();
            } catch (MalformedURLException | NullPointerException exception) {
                return;
            }
            Image image = new Image(urlPhoto);
            photoImageView.setImage(image);
        } else {
            showCheckConnectWindow();
        }

    }

    private Boolean checkConnect() {
        return ServiceFactory.ConnectService().connect().equals("OK");
    }

    @FXML
    private void setManufacturing() {
        departmentSplitMenuButton.setText(manufacturingMenuItem.getText());
    }

    @FXML
    private void setAccounting() {
        departmentSplitMenuButton.setText(accountingMenuItem.getText());
    }

    @FXML
    private void setTechnical() {
        departmentSplitMenuButton.setText(technicalMenuItem.getText());
    }

    @FXML
    private void setMarketing() {
        departmentSplitMenuButton.setText(marketingMenuItem.getText());
    }

    @FXML
    private void setDirector() {
        positionSplitMenuButton.setText(directorMenuItem.getText());
    }

    @FXML
    private void setAccountant() {
        positionSplitMenuButton.setText(accountantMenuItem.getText());
    }

    @FXML
    private void setWorker() {
        positionSplitMenuButton.setText(workerMenuItem.getText());
    }

    @FXML
    private void setManager() {
        positionSplitMenuButton.setText(managerMenuItem.getText());
    }

    @FXML
    private void save() {
        if (checkConnect()) {
            if (checkValueText()) {
                Staff staff = new Staff();
                staff.setAddress(addressTextField.getText());
                staff.setSalary(salaryTextField.getText());
                staff.setFirstName(firstNameTextField.getText());
                staff.setLastName(lastNameTextField.getText());
                staff.setDateOfBirth(dateOfBirthDatePicker.getValue().toString());
                staff.setDepartment(departmentSplitMenuButton.getText());
                staff.setPosition(positionSplitMenuButton.getText());
                staff.setPathPhoto(urlPhoto);
                staff.setId("0");
                ServiceFactory.StaffServices().save(staff);
                reloadWindow();
            }
        } else {
            showCheckConnectWindow();
        }
    }

    private void clearSelectStaff() {
        photoImageView.setImage(null);
        photoLabel.setText("");
        lastNameTextField.setText("");
        lastNameLabel.setText("");
        firstNameTextField.setText("");
        firstNameLabel.setText("");
        positionSplitMenuButton.setText("");
        positionLabel.setText("");
        dateOfBirthDatePicker.setValue(null);
        datePickerLabel.setText("");
        salaryTextField.setText("");
        salaryLabel.setText("");
        departmentSplitMenuButton.setText("");
        departmentLabel.setText("");
        addressTextField.setText("");
        addressLabel.setText("");
    }

    @Override
    public void setData(String data) {

    }

    @Override
    public void reloadWindow() {
        if (checkConnect()) {
            clearSelectStaff();
            list = ServiceFactory.StaffServices().loadAll();
            observableList = tableAllStaff.getItems();
            observableList.clear();
            observableList.addAll(list);
        } else {
            showCheckConnectWindow();
        }
    }
}
