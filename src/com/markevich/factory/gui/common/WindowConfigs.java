package com.markevich.factory.gui.common;

public enum WindowConfigs {

    CheckConnectWindow("/com/markevich/factory/gui/fxml/check_connect_window.fxml", "Check connect window"),
    StaffDayWindow("/com/markevich/factory/gui/fxml/staff_day.fxml", "Staff day window"),
    StartWindow("/com/markevich/factory/gui/fxml/start/start_window.fxml", "Start window"),
    ClientAddWindow("/com/markevich/factory/gui/fxml/client/add_client_window.fxml", "Client add window"),
    ClientListWindow("/com/markevich/factory/gui/fxml/client/client_list_window.fxml", "Client list window"),
    OrdersClientWindow("/com/markevich/factory/gui/fxml/order/orders_client_window.fxml", "Orders client window"),
    OrderAddWindow("/com/markevich/factory/gui/fxml/order/add_order_window.fxml", "Order add window"),
    ProductAddWindow("/com/markevich/factory/gui/fxml/product/add_product_window.fxml", "Product add window"),
    StaffAddWindow("/com/markevich/factory/gui/fxml/staff/add_staff_window.fxml", "Staff add window"),
    StaffListWindow("/com/markevich/factory/gui/fxml/staff/staff_list_window.fxml", "Staff list window"),
    SupplierListWindow("/com/markevich/factory/gui/fxml/supplier/supplier_list_window.fxml", "Supplier list window"),
    SupplierAddWindow("/com/markevich/factory/gui/fxml/supplier/add_supplier_window.fxml", "Supplier add window"),
    MaterialsListWindow("/com/markevich/factory/gui/fxml/materials/materials_list_window.fxml", "Material list window"),
    MaterialAddWindow("/com/markevich/factory/gui/fxml/materials/add_material_window.fxml", "Material add window"),
    SupplierMaterialsWindow("/com/markevich/factory/gui/fxml/materials/supplier_materials_window.fxml", "Supplier materials window"),
    AllDBWindow("/com/markevich/factory/gui/fxml/all_db_factory.fxml", "All data base window");

    private final String xmlUi;

    private final String title;

    WindowConfigs(String xmlUi, String title) {
        this.xmlUi = xmlUi;
        this.title = title;
    }

    public String getXmlUi() {
        return xmlUi;
    }

    public String getTitle() {
        return title;
    }
}
