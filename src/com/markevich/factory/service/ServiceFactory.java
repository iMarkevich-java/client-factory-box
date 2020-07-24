package com.markevich.factory.service;

import com.markevich.factory.service.client.ClientServices;
import com.markevich.factory.service.connectSocket.ConnectServer;
import com.markevich.factory.service.day.StaffDayServices;
import com.markevich.factory.service.material.MaterialServices;
import com.markevich.factory.service.order.OrderServices;
import com.markevich.factory.service.product.ProductServices;
import com.markevich.factory.service.staff.StaffServices;
import com.markevich.factory.service.supplier.SupplierServices;
import com.markevich.factory.service.user.UserServices;

public class ServiceFactory {


    private static final ServiceFactory service = new ServiceFactory();

    public static StaffDayServices StaffDayServices() {
        return new StaffDayServices();
    }


    public static ConnectServer ConnectService() {
        return new ConnectServer();
    }

    public static UserServices UserServices() {
        return new UserServices();
    }

    public static ClientServices ClientServices() {
        return new ClientServices();
    }

    public static MaterialServices MaterialServices() {
        return new MaterialServices();
    }

    public static OrderServices OrderServices() {
        return new OrderServices();
    }

    public static StaffServices StaffServices() {
        return new StaffServices();
    }

    public static ProductServices ProductServices() {
        return new ProductServices();
    }

    public static SupplierServices SupplierServices() {
        return new SupplierServices();
    }
}
