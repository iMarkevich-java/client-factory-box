package com.markevich.factory.service.staff;

import businessObjectFactoryBox.Staff;
import com.markevich.factory.DataExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoadStaffByID {
    public Staff loadStaffByID(String id) {
        DataExchange connect = new DataExchange();
        connect.setCommand("get-staff-by-id");
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        connect.setMap(map);
        connect.writer();
        JSONObject jsonObject = connect.read();
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
        Staff staff = new Staff();
        JSONObject object = jsonArray.getJSONObject(0);
        staff.setId(object.getString("id"));
        staff.setFirstName(object.getString("first-name"));
        staff.setLastName(object.getString("last-name"));
        staff.setDateOfBirth(object.getString("date-of-birth"));
        staff.setSalary(object.getString("salary"));
        staff.setAddress(object.getString("address"));
        staff.setDepartment(object.getString("department"));
        staff.setPosition(object.getString("position"));
        staff.setPathPhoto(object.getString("path-photo"));
        connect.closeStream();
        return staff;
    }
}
