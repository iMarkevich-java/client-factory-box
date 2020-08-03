package com.markevich.factory.service.material;

import businessObjectFactoryBox.Material;
import com.markevich.factory.DataExchange;

import java.util.HashMap;
import java.util.Map;

public class UpdateMaterial {
    public void updateMaterial(Material material) {
        DataExchange connect = new DataExchange();
        connect.setCommand("update-material");
        Map<String, String> map = new HashMap<>();
        map.put("id", material.getId());
        map.put("supplier-id", material.getSupplierId());
        map.put("material-name", material.getMaterialName());
        map.put("price", material.getPrice());
        map.put("amount", material.getAmount());
        map.put("unit", material.getUnit());
        map.put("size", material.getSize());
        map.put("path-image", material.getPathImage());
        connect.setMap(map);
        connect.writer();
        connect.read();
        connect.closeStream();
    }
}
