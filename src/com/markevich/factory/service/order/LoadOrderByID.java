package com.markevich.factory.service.order;

import businessObjectFactoryBox.Order;
import com.markevich.factory.Connect;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class LoadOrderByID {

    protected LoadOrderByID() {
    }

    public Order loadOrderByID(String id) {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter, id);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        Order order = new Order();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        int statusCode = jsonObjectHeader.getInt("status-code");
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
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

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("get-order-by-id");
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter, String id) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("id").value(id);
        jsonWriter.endObject();
    }
}
