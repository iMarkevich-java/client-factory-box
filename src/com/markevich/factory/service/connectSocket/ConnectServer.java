package com.markevich.factory.service.connectSocket;

import com.markevich.factory.Connect;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class ConnectServer {

    public ConnectServer() {
    }

    public String connect() {
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
        Integer statusCode = jsonObjectHeader.getInt("status-code");
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
        connect.closeStream();
        return statusMessage;
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("verification-connect");
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("connect-data").value("");
        jsonWriter.endObject();
    }
}
