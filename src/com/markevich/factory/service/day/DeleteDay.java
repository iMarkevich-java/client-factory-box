package com.markevich.factory.service.day;

import businessObjectFactoryBox.Day;
import com.markevich.factory.Connect;
import com.markevich.factory.StatusMessage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class DeleteDay {
    private  final String command = "get-delete-staff-day";

    public void deleteDay(Day day) {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter, day);
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

    private void buildParameters(JSONWriter jsonWriter, Day day) {
        String dayStr = "";

        dayStr = dayStr + day.getDay().toString() + "/" + day.getOrderName() + "/"
                + day.getProductivity().toString() + "/" + day.getStaffId().toString() + "/";

        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("days").value(dayStr);
        jsonWriter.endObject();
    }
}
