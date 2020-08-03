package com.markevich.factory.service.day;

import businessObjectFactoryBox.Day;
import com.markevich.factory.DataExchange;

import java.util.HashMap;
import java.util.Map;

public class DeleteDay {
    public void deleteDay(Day day) {
        DataExchange connect = new DataExchange();
        connect.setCommand("get-delete-staff-day");
        Map<String, String> map = new HashMap<>();
        String dayStr = day.getDay().toString() + "/" + day.getOrderName() + "/" + day.getProductivity().toString() +
                "/" + day.getStaffId().toString() + "/";
        map.put("days", dayStr);
        connect.setMap(map);
        connect.writer();
        connect.read();
        connect.closeStream();
    }
}
