package com.markevich.factory.service.order;

import com.markevich.factory.DataExchange;

import java.util.HashMap;
import java.util.Map;

public class DeleteOrder {
    public void deleteOrder(String id) {
        DataExchange connect = new DataExchange();
        connect.setCommand("delete-order");
        Map<String, String> map = new HashMap<>();
        map.put("order-id", id);
        connect.setMap(map);
        connect.writer();
        connect.read();
        connect.closeStream();
    }
}
