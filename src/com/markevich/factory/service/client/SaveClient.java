package com.markevich.factory.service.client;

import biznesObgectFactory.Client;
import com.markevich.factory.Connect;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class SaveClient {

    protected SaveClient() {
    }

    public void saveClient(Client client) {
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
        Integer statusCode = jsonObjectHeader.getInt("status-code");
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
        connect.closeStream();
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("save-client");
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter, Client client) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("id").value(client.getId());
        jsonWriter.key("company-name").value(client.getCompanyName());
        jsonWriter.key("legal-data").value(client.getLegalData());
        jsonWriter.key("manager").value(client.getManager());
        jsonWriter.key("address").value(client.getAddress());
        jsonWriter.endObject();
    }
}
