package com.markevich.factory.service.product;

import com.markevich.factory.DataExchange;

import java.util.HashMap;
import java.util.Map;

public class DeleteProduct {
    public void deleteProduct(String id) {
        DataExchange connect = new DataExchange();
        connect.setCommand("delete-product");
        Map<String, String> map = new HashMap<>();
        map.put("product-id", id);
        connect.setMap(map);
        connect.writer();
        connect.read();
        connect.closeStream();
    }
}
