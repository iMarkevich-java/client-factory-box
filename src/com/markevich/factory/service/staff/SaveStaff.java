package com.markevich.factory.service.staff;

import businessObjectFactoryBox.Staff;
import com.markevich.factory.Connect;
import com.markevich.factory.StatusMessage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class SaveStaff {
    private final String command = "save-staff";

    public SaveStaff() {
    }

    public void saveStaff(Staff staff) {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter, staff);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        int statusCode = jsonObjectHeader.getInt("status-code");
        StatusMessage.setStatusMessage(command + " : " + jsonObjectHeader.getString("status-message"), statusCode);
        connect.closeStream();
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value(command);
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter, Staff staff) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("id").value(staff.getId());
        jsonWriter.key("address").value(staff.getAddress());
        jsonWriter.key("salary").value(staff.getSalary());
        jsonWriter.key("first-name").value(staff.getFirstName());
        jsonWriter.key("last-name").value(staff.getLastName());
        jsonWriter.key("date-of-birth").value(staff.getDateOfBirth());
        jsonWriter.key("department").value(staff.getDepartment());
        jsonWriter.key("position").value(staff.getPosition());
        jsonWriter.key("path-photo").value(staff.getPathPhoto());
        jsonWriter.endObject();
    }
}



