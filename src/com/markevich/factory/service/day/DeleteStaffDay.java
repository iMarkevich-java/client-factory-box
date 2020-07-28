package com.markevich.factory.service.day;

import com.markevich.factory.Connect;
import com.markevich.factory.StatusMessage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class DeleteStaffDay {
    private final String command = "get-delete-staff-days";

    public void deleteStaffDays(String staffId) {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter, staffId);
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

    private void buildParameters(JSONWriter jsonWriter, String staffId) {

        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("staff-id").value(staffId);
        jsonWriter.endObject();
    }
}
