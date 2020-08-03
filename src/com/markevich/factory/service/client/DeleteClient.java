package com.markevich.factory.service.client;

import com.markevich.factory.DataExchange;

import java.util.HashMap;
import java.util.Map;

public class DeleteClient {
    public void deleteClient(String id) {
        DataExchange connect = new DataExchange();
        connect.setCommand("delete-client");
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        connect.setMap(map);
        connect.writer();
        connect.read();
        connect.closeStream();
    }
}
