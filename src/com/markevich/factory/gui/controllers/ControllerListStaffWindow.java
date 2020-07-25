package com.markevich.factory.gui.controllers;

import businessObjectFactoryBox.Staff;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControllerListStaffWindow implements DBWindow {
    private final List<Staff> list = new ArrayList<>();
    @FXML
    private Button photoButton;
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
    private String urlPhoto;

    @FXML
    private void showMainWindow() {
        AppWindows appWindows = AppWindows.getInstance();
        appWindows.showWindow(WindowConfigs.StartWindow);
        appWindows.reloadWindow(WindowConfigs.StartWindow);

    }

    @FXML
    private void showAddStaffWindow() {
        if (checkConnect()) {
            AppWindows appWindows = AppWindows.getInstance();
            appWindows.showWindow(WindowConfigs.StaffAddWindow);
            appWindows.reloadWindow(WindowConfigs.StaffAddWindow);
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

    @FXML
    private void update() {
        if (checkConnect()) {
            Staff staff = tableAllStaff.getSelectionModel().getSelectedItem();
            if (!(staff == null)) {
                if (!(urlPhoto == null)) {
                    staff.setPathPhoto(urlPhoto);
                }
                if (isNumber(salaryTextField.getText())) {
                    staff.setSalary(salaryTextField.getText());
                    salaryLabel.setText("");
                } else {
                    salaryLabel.setText("Enter wrong number!!!");
                    return;
                }
                if (!addressTextField.getText().isEmpty()) {
                    staff.setAddress(addressTextField.getText());
                }
                if (!firstNameTextField.getText().isEmpty()) {
                    staff.setFirstName(firstNameTextField.getText());
                }
                if (!lastNameTextField.getText().isEmpty()) {
                    staff.setLastName(lastNameTextField.getText());
                }
                if (!(dateOfBirthDatePicker.getValue() == null)) {
                    staff.setDateOfBirth(dateOfBirthDatePicker.getValue().toString());
                }
                if (!departmentSplitMenuButton.getText().isEmpty()) {
                    staff.setDepartment(departmentSplitMenuButton.getText());
                }
                if (positionSplitMenuButton.getText().isEmpty()) {
                    staff.setPosition(positionSplitMenuButton.getText());
                }
                ServiceFactory.StaffServices().update(staff);
                reloadWindow();
            }
        } else {
            showCheckConnectWindow();
        }
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

    private void clearSelectStaff() {
        urlPhoto = null;
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

    @FXML
    public void selectStaff() {
        Staff staff = tableAllStaff.getSelectionModel().getSelectedItem();
        if (staff != null) {
            photoButton.setDisable(false);
            lastNameTextField.setDisable(false);
            firstNameTextField.setDisable(false);
            positionSplitMenuButton.setDisable(false);
            dateOfBirthDatePicker.setDisable(false);
            salaryTextField.setDisable(false);
            departmentSplitMenuButton.setDisable(false);
            addressTextField.setDisable(false);

            photoImageView.setImage(new Image(staff.getPathPhoto()));
            lastNameTextField.setText(staff.getLastName());
            firstNameTextField.setText(staff.getFirstName());
            positionSplitMenuButton.setText(staff.getPosition());
            dateOfBirthDatePicker.setValue(LocalDate.parse(staff.getDateOfBirth()));
            salaryTextField.setText(staff.getSalary());
            departmentSplitMenuButton.setText(staff.getDepartment());
            addressTextField.setText(staff.getAddress());
        }
    }

    @FXML
    private void delete() {
        if (checkConnect()) {
            Staff staff = tableAllStaff.getSelectionModel().getSelectedItem();
            if (!(staff == null)) {
                ServiceFactory.StaffServices().delete(staff.getId());
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
            photoButton.setDisable(true);
            lastNameTextField.setDisable(true);
            firstNameTextField.setDisable(true);
            positionSplitMenuButton.setDisable(true);
            dateOfBirthDatePicker.setDisable(true);
            salaryTextField.setDisable(true);
            departmentSplitMenuButton.setDisable(true);
            addressTextField.setDisable(true);

            List<Staff> listStaff = ServiceFactory.StaffServices().loadAll();
            observableList = tableAllStaff.getItems();
            observableList.clear();
            clearSelectStaff();
            observableList.addAll(listStaff);
            tableAllStaff.refresh();
        } else {
            showCheckConnectWindow();
        }
    }
}