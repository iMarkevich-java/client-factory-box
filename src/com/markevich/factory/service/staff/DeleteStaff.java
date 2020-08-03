package com.markevich.factory.service.staff;

import com.markevich.factory.DataExchange;

import java.util.HashMap;
import java.util.Map;

public class DeleteStaff {
    public void deleteStaff(String id) {
        DataExchange connect = new DataExchange();
        connect.setCommand("delete-staff");
        Map<String, String> map = new HashMap<>();
        map.put("staff-id", id);
        connect.setMap(map);
        connect.writer();
        connect.read();
        connect.closeStream();
    }
}
