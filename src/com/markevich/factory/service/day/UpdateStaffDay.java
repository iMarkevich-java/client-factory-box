package com.markevich.factory.service.day;

import businessObjectFactoryBox.Day;
import businessObjectFactoryBox.StaffDays;
import com.markevich.factory.Connect;
import com.markevich.factory.StatusMessage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class UpdateStaffDay {
    private final String command = "update-staff-day";

    public void updateStaffDay(StaffDays staffDay) {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter, staffDay);
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

    private void buildParameters(JSONWriter jsonWriter, StaffDays staffDay) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("staff-id").value(staffDay.getStaffId().toString());
        StringBuilder days = new StringBuilder();
        for (Day day : staffDay.getListDay()) {
            days.append(day.getDay().toString()).append("/").append(day.getOrderName()).append("/").append(day.getProductivity().toString()).append("/").append(day.getStaffId().toString()).append("/").append("#");
        }
        jsonWriter.key("days").value(days.toString());
        jsonWriter.endObject();
    }
}
