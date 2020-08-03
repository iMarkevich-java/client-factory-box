package com.markevich.factory.service.client;

import businessObjectFactoryBox.Client;
import com.markevich.factory.DataExchange;

import java.util.HashMap;
import java.util.Map;

public class UpdateClient {
    public void updateClient(Client client) {
        DataExchange connect = new DataExchange();
        connect.setCommand("update-client");
        Map<String, String> map = new HashMap<>();
        map.put("id", client.getId());
        map.put("company-name", client.getCompanyName());
        map.put("legal-data", client.getLegalData());
        map.put("manager", client.getManager());
        map.put("address", client.getAddress());
        connect.setMap(map);
        connect.writer();
        connect.read();
        connect.closeStream();
    }
}
