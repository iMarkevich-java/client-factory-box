package com.markevich.factory.service.staff;

import businessObjectFactoryBox.Staff;
import com.markevich.factory.DataExchange;

import java.util.HashMap;
import java.util.Map;

public class UpdateStaff {
    public void updateStaff(Staff staff) {
        DataExchange connect = new DataExchange();
        connect.setCommand("update-staff");
        Map<String, String> map = new HashMap<>();
        map.put("id", staff.getId());
        map.put("address", staff.getAddress());
        map.put("salary", staff.getSalary());
        map.put("first-name", staff.getFirstName());
        map.put("last-name", staff.getLastName());
        map.put("date-of-birth", staff.getDateOfBirth());
        map.put("department", staff.getDepartment());
        map.put("position", staff.getPosition());
        map.put("path-photo", staff.getPathPhoto());
        connect.setMap(map);
        connect.writer();
        connect.read();
        connect.closeStream();
    }
}
