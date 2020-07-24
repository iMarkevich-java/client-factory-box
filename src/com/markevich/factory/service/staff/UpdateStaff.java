package com.markevich.factory.service.staff;

import biznesObgectFactory.Staff;
import com.markevich.factory.Connect;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class UpdateStaff {

    public UpdateStaff() {
    }

    public void updateStaff(Staff staff) {
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
        Integer statusCode = jsonObjectHeader.getInt("status-code");
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
        connect.closeStream();
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("update-staff");
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter, Staff staff) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("id").value(staff.getId());
        jsonWriter.key("address").value(staff.getAddress());
        jsonWriter.key("first-name").value(staff.getFirstName());
        jsonWriter.key("last-name").value(staff.getLastName());
        jsonWriter.key("date-of-birth").value(staff.getDateOfBirth());
        jsonWriter.key("department").value(staff.getDepartment());
        jsonWriter.key("salary").value(staff.getSalary());
        jsonWriter.key("position").value(staff.getPosition());
        jsonWriter.key("path-photo").value(staff.getPathPhoto());
        jsonWriter.endObject();
    }
}
