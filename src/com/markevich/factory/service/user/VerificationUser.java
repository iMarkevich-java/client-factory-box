package com.markevich.factory.service.user;

import businessObjectFactoryBox.User;
import com.markevich.factory.Connect;
import com.markevich.factory.ConnectDataUser;
import com.markevich.factory.StatusMessage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class VerificationUser {
    private final String command = "verification-user";

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
        int statusCode = jsonObjectHeader.getInt("status-code");
        StatusMessage.setStatusMessage(command + " : " + jsonObjectHeader.getString("status-message"), statusCode);
        connect.closeStream();
        return jsonObjectHeader.getString("status-message");
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value(command);
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
