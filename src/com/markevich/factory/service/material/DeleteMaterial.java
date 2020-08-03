package com.markevich.factory.service.material;

import com.markevich.factory.DataExchange;

import java.util.HashMap;
import java.util.Map;

public class DeleteMaterial {
    public void deleteMaterial(String id) {
        DataExchange connect = new DataExchange();
        connect.setCommand("delete-material");
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        connect.setMap(map);
        connect.writer();
        connect.read();
        connect.closeStream();
    }
}
