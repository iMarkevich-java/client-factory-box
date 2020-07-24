package com.markevich.factory.service.staff;

import biznesObgectFactory.Staff;
import com.markevich.factory.Connect;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.util.ArrayList;
import java.util.List;

public class LoadAllStaff {

    public List<Staff> loadAllStaff() {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        List<Staff> listStaff = new ArrayList<>();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        Integer statusCode = jsonObjectHeader.getInt("status-code");
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
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

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("get-all-staff");
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("client-list").value("all");
        jsonWriter.endObject();
    }
}
