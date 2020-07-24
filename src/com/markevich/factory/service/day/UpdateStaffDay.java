package com.markevich.factory.service.day;

import biznesObgectFactory.Day;
import biznesObgectFactory.StaffDays;
import com.markevich.factory.Connect;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class UpdateStaffDay {

    protected UpdateStaffDay() {
    }

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
        Integer statusCode = jsonObjectHeader.getInt("status-code");
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
        connect.closeStream();
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("update-staff-day");
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter, StaffDays staffDay) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("staff-id").value(staffDay.getStaffId().toString());
        String days = "";
        for (Day day : staffDay.getListDay()) {
            days = days + day.getDay().toString() + "/" + day.getOrderName() + "/"
                    + day.getProductivity().toString() + "/" + day.getStaffId().toString() + "/" + "#";
        }
        jsonWriter.key("days").value(days);
        jsonWriter.endObject();
    }
}
