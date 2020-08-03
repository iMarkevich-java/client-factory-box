package com.markevich.factory.service.day;

import businessObjectFactoryBox.Day;
import businessObjectFactoryBox.StaffDays;
import com.markevich.factory.DataExchange;

import java.util.HashMap;
import java.util.Map;

public class SaveStaffDay {
    public void addStaffDay(StaffDays staffDay) {
        DataExchange connect = new DataExchange();
        connect.setCommand("save-staff-day");
        StringBuilder days = new StringBuilder();
        for (Day day : staffDay.getListDay()) {
            days.append(day.getDay().toString()).append("/").append(day.getOrderName()).append("/").append(day.getProductivity().toString())
                    .append("/").append(day.getStaffId().toString()).append("/").append("#");
        }
        Map<String, String> map = new HashMap<>();
        map.put("staff-id", staffDay.getStaffId().toString());
        map.put("days", days.toString());
        connect.setMap(map);
        connect.writer();
        connect.read();
        connect.closeStream();
    }
}
