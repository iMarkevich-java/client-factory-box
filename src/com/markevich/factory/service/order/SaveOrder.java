package com.markevich.factory.service.order;

import businessObjectFactoryBox.Order;
import com.markevich.factory.Connect;
import com.markevich.factory.StatusMessage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class SaveOrder {
    private final String command = "save-order";

    public void saveOrder(Order order) {
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
        int statusCode = jsonObjectHeader.getInt("status-code");
        StatusMessage.setStatusMessage(command  + " : " + jsonObjectHeader.getString("status-message"), statusCode);
        connect.closeStream();
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value(command);
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
