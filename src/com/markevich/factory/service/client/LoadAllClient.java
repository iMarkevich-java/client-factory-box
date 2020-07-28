package com.markevich.factory.service.client;

import businessObjectFactoryBox.Client;
import com.markevich.factory.Connect;
import com.markevich.factory.StatusMessage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.util.ArrayList;
import java.util.List;

public class LoadAllClient {
    private final String command = "get-all-client";

    public List<Client> loadAllClient() {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        int statusCode = jsonObjectHeader.getInt("status-code");
        StatusMessage.setStatusMessage(command + " : " + jsonObjectHeader.getString("status-message"), statusCode);
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
        List<Client> listClient = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Client client = new Client();
            JSONObject object = jsonArray.getJSONObject(i);
            client.setCompanyName(object.getString("company-name"));
            client.setLegalData(object.getString("legal-data"));
            client.setAddress(object.getString("address"));
            client.setManager(object.getString("manager"));
            client.setId(object.getString("id"));
            listClient.add(client);
        }
        connect.closeStream();
        return listClient;
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value(command);
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("client-list").value("");
        jsonWriter.endObject();
    }
}

