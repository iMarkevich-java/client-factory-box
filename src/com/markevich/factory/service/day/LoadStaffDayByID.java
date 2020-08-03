package com.markevich.factory.service.day;

import businessObjectFactoryBox.Day;
import businessObjectFactoryBox.StaffDays;
import com.markevich.factory.DataExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class LoadStaffDayByID {
    public StaffDays loadStaffDayByID(String id) {
        DataExchange connect = new DataExchange();
        connect.setCommand("get-staff-day-by-id");
        Map<String, String> map = new HashMap<>();
        map.put("staff-id", id);
        connect.setMap(map);
        connect.writer();
        JSONObject jsonObject = connect.read();
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
}
