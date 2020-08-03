package com.markevich.factory.service.order;

import businessObjectFactoryBox.Order;
import com.markevich.factory.DataExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadAllOrder {
    public List<Order> loadAllOrder() {
        DataExchange connect = new DataExchange();
        connect.setCommand("get-all-order");
        connect.writer();
        JSONObject jsonObject = connect.read();
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
        List<Order> listOrder = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Order order = new Order();
            JSONObject object = jsonArray.getJSONObject(i);
            order.setId(object.getString("id"));
            order.setSizeOrder(object.getString("sizeOrder"));
            order.setStatus(object.getString("status"));
            order.setStage(object.getString("stage"));
            order.setClientId(object.getString("client-id"));
            order.setOrderName(object.getString("order-name"));
            order.setOrderTerm(object.getString("order-term"));
            order.setStartDate(object.getString("start-date"));
            listOrder.add(order);
        }
        connect.closeStream();
        return listOrder;
    }
}
