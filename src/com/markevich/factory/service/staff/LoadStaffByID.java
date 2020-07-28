package com.markevich.factory.service.staff;

import businessObjectFactoryBox.Staff;
import com.markevich.factory.Connect;
import com.markevich.factory.StatusMessage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class LoadStaffByID {
    private final String command = "get-staff-by-id";

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
        int statusCode = jsonObjectHeader.getInt("status-code");
        StatusMessage.setStatusMessage(command + " : " + jsonObjectHeader.getString("status-message"), statusCode);
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
        jsonWriter.key("command-name").value(command);
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter, String id) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("id").value(id);
        jsonWriter.endObject();
    }
}
