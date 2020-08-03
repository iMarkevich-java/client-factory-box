package com.markevich.factory.service.order;

import businessObjectFactoryBox.Order;
import com.markevich.factory.DataExchange;

import java.util.HashMap;
import java.util.Map;

public class UpdateOrder {
    public void updateOrder(Order order) {
        DataExchange connect = new DataExchange();
        connect.setCommand("update-order");
        Map<String, String> map = new HashMap<>();
        map.put("id", order.getId());
        map.put("size-order", order.getSizeOrder());
        map.put("stage", order.getStage());
        map.put("status", order.getStatus());
        map.put("client-id", order.getClientId());
        map.put("order-name", order.getOrderName());
        map.put("order-term", order.getOrderTerm());
        map.put("start-date", order.getStartDate());
        connect.setMap(map);
        connect.writer();
        connect.read();
        connect.closeStream();
    }
}
