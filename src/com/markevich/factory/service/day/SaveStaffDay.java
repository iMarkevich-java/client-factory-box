package com.markevich.factory.service.day;

import businessObjectFactoryBox.Day;
import businessObjectFactoryBox.StaffDays;
import com.markevich.factory.Connect;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class SaveStaffDay {
    public void addStaffDay(StaffDays staffDay) {
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
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
        connect.closeStream();
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("save-staff-day");
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter, StaffDays staffDay) {
        StringBuilder days = new StringBuilder();
        for (Day day : staffDay.getListDay()) {
            days.append(day.getDay().toString()).append("/").append(day.getOrderName()).append("/").append(day.getProductivity().toString()).append("/").append(day.getStaffId().toString()).append("/").append("#");
        }
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("staff-id").value(staffDay.getStaffId().toString());
        jsonWriter.key("days").value(days.toString());
        jsonWriter.endObject();
    }
}
