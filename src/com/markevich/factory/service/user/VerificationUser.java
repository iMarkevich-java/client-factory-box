package com.markevich.factory.service.user;

import businessObjectFactoryBox.User;
import com.markevich.factory.DataExchange;
import com.markevich.factory.ConnectDataUser;

import java.util.HashMap;
import java.util.Map;

public class VerificationUser {
    public String verificationUser(User user) {

        if ((ConnectDataUser.getIp() == null) || (ConnectDataUser.getPort() == null)) {
            return "Unauthorized";
        }
        DataExchange connect = new DataExchange();
        connect.setCommand("verification-user");
        Map<String, String> map = new HashMap<>();
        map.put("user-name", user.getName());
        map.put("user-password", String.valueOf(user.getPassword()));
        connect.setMap(map);
        connect.writer();
        connect.read();
        connect.closeStream();
        return connect.getMessage();
    }
}
