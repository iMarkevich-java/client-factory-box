package com.markevich.factory.service.staff;

import businessObjectFactoryBox.Staff;
import com.markevich.factory.Connect;
import com.markevich.factory.StatusMessage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.util.ArrayList;
import java.util.List;

public class LoadAllStaff {
    private final String command = "get-all-staff";

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
        int statusCode = jsonObjectHeader.getInt("status-code");
        StatusMessage.setStatusMessage(command + " : " + jsonObjectHeader.getString("status-message"), statusCode);
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
        jsonWriter.key("command-name").value(command);
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("client-list").value("all");
        jsonWriter.endObject();
    }
}
