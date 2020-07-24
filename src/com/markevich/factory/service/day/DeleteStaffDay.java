package com.markevich.factory.service.day;

import biznesObgectFactory.Day;
import com.markevich.factory.Connect;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class DeleteStaffDay {

    protected DeleteStaffDay() {
    }

    public void deleteStaffDay(Day day) {
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
        Integer statusCode = jsonObjectHeader.getInt("status-code");
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
        connect.closeStream();
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("delete-day");
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
