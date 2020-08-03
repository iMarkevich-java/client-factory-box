package com.markevich.factory.service.day;

import com.markevich.factory.DataExchange;

import java.util.HashMap;
import java.util.Map;

public class DeleteStaffDay {
    public void deleteStaffDays(String id) {
        DataExchange connect = new DataExchange();
        connect.setCommand("get-delete-staff-days");
        Map<String, String> map = new HashMap<>();
        map.put("staff-id", id);
        connect.setMap(map);
        connect.writer();
        connect.read();
        connect.closeStream();
    }
}
