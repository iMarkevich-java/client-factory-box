package com.markevich.factory.gui.controllers;

import biznesObgectFactory.Day;
import biznesObgectFactory.Order;
import biznesObgectFactory.Staff;
import biznesObgectFactory.StaffDays;
import com.markevich.factory.gui.common.AppWindows;
import com.markevich.factory.gui.common.CheckConnect;
import com.markevich.factory.gui.common.DBWindow;
import com.markevich.factory.gui.common.WindowConfigs;
import com.markevich.factory.service.ServiceFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigInteger;
import java.util.List;

public class ControllerStaffDayWindow implements DBWindow {
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField productivityTextField;
    @FXML
    private Label productivityLabel;
    @FXML
    private Label staffNameLabel;
    @FXML
    private Label staffLabel;
    @FXML
    private Label orderNameLabel;
    @FXML
    private Label orderLabel;
    @FXML
    private DatePicker staffDayDatePicker;
    @FXML
    private Label dateLabel;
    private ObservableList<Day> observableListStaffDay;
    @FXML
    private TableView<Day> staffDayTableView;
    private ObservableList<Staff> observableListStaff;
    @FXML
    private TableView<Staff> staffTableView;
    private ObservableList<Order> observableListOrder;
    @FXML
    private TableView<Order> orderTableView;

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
    private void deleteDay() {
        if (checkValueText()) {
            Day day = staffDayTableView.getSelectionModel().getSelectedItem();
            ServiceFactory.StaffDayServices().deleteDay(day);
            List<Order> orderList = ServiceFactory.OrderServices().loadAll();
            Order order = null;
            for (Order orderTemp : orderList) {
                if (orderTemp.getOrderName().equals(day.getOrderName())) {
                    order = orderTemp;
                    break;
                }
            }
            order.deleteStage(day.getProductivity().toString());
            ServiceFactory.OrderServices().update(order);
            reloadWindow();
        } else {
            showCheckConnectWindow();
        }
    }

    private Boolean isNumber(String strNumber) {
        try {
            new BigInteger(strNumber);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    private Boolean checkValueText() {
        Boolean bool = true;

        if (staffDayDatePicker.getValue() == null) {
            dateLabel.setText("Please select date");
            bool = false;
        } else {
            dateLabel.setText("");
        }
        if (!isNumber(productivityTextField.getText())) {
            productivityLabel.setText("Please enter number");
            bool = false;
        } else {
            productivityLabel.setText("");
        }
        if (staffNameLabel.getText().isEmpty()) {
            staffLabel.setText("Check staff");
            bool = false;
        } else {
            staffLabel.setText("");
        }
        if (orderNameLabel.getText().isEmpty()) {
            orderLabel.setText("Check order");
            bool = false;
        } else {
            orderLabel.setText("");
        }


        return bool;
    }

    @FXML
    private void save() {
        if (checkConnect()) {
            StaffDays staffDay = new StaffDays();
            Day day = new Day();
            Staff staff = staffTableView.getSelectionModel().getSelectedItem();
            if (checkValueText()) {
                List<StaffDays> staffDaysList = ServiceFactory.StaffDayServices().loadAll();
                if (!(staffDaysList == null)) {
                    for (StaffDays staffDayTemp : staffDaysList) {
                        if (staffDayTemp.getStaffId().toString().equals(staff.getId())) {
                            staffDay = ServiceFactory.StaffDayServices().loadById(staff.getId());
                            day.setDay(staffDayDatePicker.getValue());
                            day.setProductivity(new BigInteger(productivityTextField.getText()));
                            day.setOrderName(orderNameLabel.getText());
                            day.setStaffId(new BigInteger(staff.getId()));
                            staffDay.addDay(day);
                            ServiceFactory.StaffDayServices().update(staffDay);
                            Order order = orderTableView.getSelectionModel().getSelectedItem();
                            String productivity = productivityTextField.getText();
                            order.addStage(productivity);
                            ServiceFactory.OrderServices().update(order);
                            reloadWindow();
                            return;
                        }
                    }
                }
                staffDay.setStaffId(new BigInteger(staff.getId()));
                day.setDay(staffDayDatePicker.getValue());
                day.setProductivity(new BigInteger(productivityTextField.getText()));
                day.setOrderName(orderNameLabel.getText());
                day.setStaffId(new BigInteger(staff.getId()));
                staffDay.addDay(day);
                ServiceFactory.StaffDayServices().save(staffDay);
                Order order = orderTableView.getSelectionModel().getSelectedItem();
                order.addStage(productivityTextField.getText());
                ServiceFactory.OrderServices().update(order);
                reloadWindow();
            }
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void setStaff() {
        if (checkConnect()) {
            saveButton.setDisable(false);
            deleteButton.setDisable(true);
            Staff staff = staffTableView.getSelectionModel().getSelectedItem();
            staffNameLabel.setText(staff.getFirstName() + " " + staff.getLastName());
            StaffDays staffDay = ServiceFactory.StaffDayServices().loadById(staff.getId());
            observableListStaffDay = staffDayTableView.getItems();
            observableListStaffDay.clear();
            observableListStaffDay.addAll(staffDay.getListDay());
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void setStaffDay() {
        if (checkConnect()) {
            saveButton.setDisable(true);
            deleteButton.setDisable(false);
            Day day = staffDayTableView.getSelectionModel().getSelectedItem();
            if (!(day == null)) {
                staffDayDatePicker.setValue(day.getDay());
                productivityTextField.setText(day.getProductivity().toString());
                Staff staff = ServiceFactory.StaffServices().loadById(day.getStaffId().toString());
                staffNameLabel.setText(staff.getFirstName() + " " + staff.getLastName());
                orderNameLabel.setText(day.getOrderName());
            }
        } else {
            showCheckConnectWindow();
        }
    }

    @FXML
    private void setOrder() {
        Order order = orderTableView.getSelectionModel().getSelectedItem();
        orderNameLabel.setText(order.getOrderName());

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
            saveButton.setDisable(true);
            deleteButton.setDisable(true);
            staffNameLabel.setText("");
            orderNameLabel.setText("");
            productivityTextField.setText("");
            staffDayDatePicker.setValue(null);
            Staff staff = staffTableView.getSelectionModel().getSelectedItem();
            observableListStaffDay = staffDayTableView.getItems();
            observableListStaffDay.clear();
            if (!(staff == null)) {
                StaffDays days = ServiceFactory.StaffDayServices().loadById(staff.getId());
                observableListStaffDay.addAll(days.getListDay());
            }
            List<Staff> staffList = ServiceFactory.StaffServices().loadAll();
            observableListStaff = staffTableView.getItems();
            observableListStaff.clear();
            observableListStaff.addAll(staffList);
            List<Order> orderList = ServiceFactory.OrderServices().loadAll();
            observableListOrder = orderTableView.getItems();
            observableListOrder.clear();
            observableListOrder.addAll(orderList);
        } else {
            showCheckConnectWindow();
        }
    }
}
