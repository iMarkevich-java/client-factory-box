package com.markevich.factory.service.supplier;

import com.markevich.factory.DataExchange;

import java.util.HashMap;
import java.util.Map;

public class DeleteSupplier {
    public void deleteSupplier(String id) {
        DataExchange connect = new DataExchange();
        connect.setCommand("delete-supplier");
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        connect.setMap(map);
        connect.writer();
        connect.read();
        connect.closeStream();
    }
}
