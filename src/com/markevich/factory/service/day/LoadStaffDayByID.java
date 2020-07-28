package com.markevich.factory.service.day;

import businessObjectFactoryBox.Day;
import businessObjectFactoryBox.StaffDays;
import com.markevich.factory.Connect;
import com.markevich.factory.StatusMessage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.math.BigInteger;
import java.time.LocalDate;

public class LoadStaffDayByID {
    private final String command = "get-staff-day-by-id";

    public StaffDays loadStaffDayByID(String id) {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter, id);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        int statusCode = jsonObjectHeader.getInt("status-code");
        StatusMessage.setStatusMessage(command  + " : " + jsonObjectHeader.getString("status-message"), statusCode);
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
        StaffDays staffDay = new StaffDays();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            if (object.getString("staff-id").equals("")) {
                break;
            }
            staffDay.setStaffId(new BigInteger(object.getString("staff-id")));
            String daysString = object.getString("days");
            String[] daysArray = daysString.split("#");
            for (String dayStr : daysArray) {
                String[] dayArray = dayStr.split("/");
                if (dayArray.length <= 1) {
                    break;
                }
                Day day = new Day();
                if (!(dayArray[0].equals(""))) {
                    day.setDay(LocalDate.parse(dayArray[0]));
                }
                if (!(dayArray[1].equals(""))) {
                    day.setOrderName(dayArray[1]);
                }
                if (!(dayArray[2].equals(""))) {
                    day.setProductivity(new BigInteger(dayArray[2]));
                }
                if (!(dayArray[3].equals(""))) {
                    day.setStaffId(new BigInteger(dayArray[3]));
                }
                staffDay.addDay(day);
            }
        }
        connect.closeStream();
        return staffDay;
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value(command);
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter, String id) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("staff-id").value(id);
        jsonWriter.endObject();
    }
}
