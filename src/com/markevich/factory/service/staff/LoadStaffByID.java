package com.markevich.factory.service.staff;

import biznesObgectFactory.Staff;
import com.markevich.factory.Connect;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class LoadStaffByID {

    public LoadStaffByID() {
    }

    public Staff loadStaffByID(String id) {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter, id);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        Integer statusCode = jsonObjectHeader.getInt("status-code");
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
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

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("get-staff-by-id");
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter, String id) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("id").value(id);
        jsonWriter.endObject();
    }
}
