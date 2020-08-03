package com.markevich.factory.service.staff;

import businessObjectFactoryBox.Staff;
import com.markevich.factory.DataExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadAllStaff {
    public List<Staff> loadAllStaff() {
        DataExchange connect = new DataExchange();
        connect.setCommand("get-all-staff");
        connect.writer();
        JSONObject jsonObject = connect.read();
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
        List<Staff> listStaff = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Staff staff = new Staff();
            JSONObject object = jsonArray.getJSONObject(i);
            staff.setId(object.getString("id"));
            staff.setFirstName(object.getString("first-name"));
            staff.setLastName(object.getString("last-name"));
            staff.setDateOfBirth(object.getString("date-of-birth"));
            staff.setSalary(object.getString("salary"));
            staff.setAddress(object.getString("address"));
            staff.setDepartment(object.getString("department"));
            staff.setPosition(object.getString("position"));
            staff.setPathPhoto(object.getString("path-photo"));
            listStaff.add(staff);
        }
        connect.closeStream();
        return listStaff;
    }
}
