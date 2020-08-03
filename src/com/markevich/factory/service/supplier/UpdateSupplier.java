package com.markevich.factory.service.supplier;

import businessObjectFactoryBox.Supplier;
import com.markevich.factory.DataExchange;

import java.util.HashMap;
import java.util.Map;

public class UpdateSupplier {
    public void updateSupplier(Supplier supplier) {
        DataExchange connect = new DataExchange();
        connect.setCommand("update-supplier");
        Map<String, String> map = new HashMap<>();
        map.put("id", supplier.getId());
        map.put("address", supplier.getAddress());
        map.put("company-name", supplier.getCompanyName());
        map.put("legal-data", supplier.getLegalData());
        map.put("manager", supplier.getManager());
        connect.setMap(map);
        connect.writer();
        connect.read();
        connect.closeStream();
    }
}
