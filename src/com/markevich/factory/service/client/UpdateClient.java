package com.markevich.factory.service.client;

import businessObjectFactoryBox.Client;
import com.markevich.factory.Connect;
import com.markevich.factory.StatusMessage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class UpdateClient {
    private final String command = "update-client";

    public void updateClient(Client client) {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter, client);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        int statusCode = jsonObjectHeader.getInt("status-code");
        StatusMessage.setStatusMessage(command + " : " + jsonObjectHeader.getString("status-message"), statusCode);
        connect.closeStream();
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value(command);
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter, Client client) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("id").value(client.getId());
        jsonWriter.key("companyName").value(client.getCompanyName());
        jsonWriter.key("legalData").value(client.getLegalData());
        jsonWriter.key("manager").value(client.getManager());
        jsonWriter.key("address").value(client.getAddress());
        jsonWriter.endObject();
    }
}
