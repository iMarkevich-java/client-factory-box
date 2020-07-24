package com.markevich.factory.service.day;

import biznesObgectFactory.Day;
import biznesObgectFactory.StaffDays;
import com.markevich.factory.Connect;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoadAllStaffDay {

    protected LoadAllStaffDay() {
    }

    public List<StaffDays> loadAllStaffDay() {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        List<StaffDays> listStaffDay = new ArrayList<>();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        Integer statusCode = jsonObjectHeader.getInt("status-code");
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
        for (int i = 0; i < jsonArray.length(); i++) {
            StaffDays staffDay = new StaffDays();
            JSONObject object = jsonArray.getJSONObject(i);
            if (object.getString("staff-id").equals("")) {
                break;
            }
            staffDay.setStaffId(new BigInteger(object.getString("staff-id")));
            String daysString = (object.getString("days"));
            String[] daysArray = daysString.split("#");
            for (int j = 0; j < daysArray.length; j++) {
                String dayStr = daysArray[j];
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
            listStaffDay.add(staffDay);
        }
        connect.closeStream();
        return listStaffDay;
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("get-all-staff-day");
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("staff-day-list").value("");
        jsonWriter.endObject();
    }
}

