package com.markevich.factory.service.order;

import businessObjectFactoryBox.Order;
import com.markevich.factory.DataExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoadOrderByID {
    public Order loadOrderByID(String id) {
        DataExchange connect = new DataExchange();
        connect.setCommand("get-order-by-id");
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        connect.setMap(map);
        connect.writer();
        JSONObject jsonObject = connect.read();
        Order order = new Order();
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            order.setId(object.getString("id"));
            order.setSizeOrder(object.getString("size-order"));
            order.setStatus(object.getString("status"));
            order.setStage(object.getString("stage"));
            order.setClientId(object.getString("client-id"));
            order.setOrderName(object.getString("order-name"));
            order.setOrderTerm(object.getString("order-term"));
            order.setStartDate(object.getString("start-date"));
        }
        connect.closeStream();
        return order;
    }
}
