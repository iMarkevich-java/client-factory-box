package com.markevich.factory.service.order;

import biznesObgectFactory.Order;
import com.markevich.factory.Connect;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class UpdateOrder {

    protected UpdateOrder() {
    }

    public void updateOrder(Order order) {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter, order);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        Integer statusCode = jsonObjectHeader.getInt("status-code");
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
        connect.closeStream();
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("update-order");
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter, Order order) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("id").value(order.getId());
        jsonWriter.key("size-order").value(order.getSizeOrder());
        jsonWriter.key("stage").value(order.getStage());
        jsonWriter.key("status").value(order.getStatus());
        jsonWriter.key("client-id").value(order.getClientId());
        jsonWriter.key("order-name").value(order.getOrderName());
        jsonWriter.key("order-term").value(order.getOrderTerm());
        jsonWriter.key("start-date").value(order.getStartDate());
        jsonWriter.endObject();
    }
}
