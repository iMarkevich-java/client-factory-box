package com.markevich.factory.service.user;

import biznesObgectFactory.User;
import com.markevich.factory.Connect;
import com.markevich.factory.ConnectDataUser;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class VerificationUser {

    public VerificationUser() {
    }

    public String verificationUser(User user) {

        if ((ConnectDataUser.getIp() == null) || (ConnectDataUser.getPort() == null)) {
            return "Unauthorized";
        }
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter, user);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        Integer statusCode = jsonObjectHeader.getInt("status-code");
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
        connect.closeStream();
        return statusMessage;
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("verification-user");
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter, User user) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("user-name").value(user.getName());
        jsonWriter.key("user-password").value(String.valueOf(user.getPassword()));
        jsonWriter.endObject();
    }
}
