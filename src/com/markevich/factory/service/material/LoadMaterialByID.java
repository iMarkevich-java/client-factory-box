package com.markevich.factory.service.material;

import businessObjectFactoryBox.Material;
import com.markevich.factory.DataExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoadMaterialByID {
    public Material loadMaterialByID(String id) {
        DataExchange connect = new DataExchange();
        connect.setCommand("get-material-by-id");
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        connect.setMap(map);
        connect.writer();
        JSONObject jsonObject = connect.read();
        Material material = new Material();
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            material.setId(object.getString("id"));
            material.setSupplierId(object.getString("supplier-id"));
            material.setMaterialName(object.getString("material-name"));
            material.setPrice(object.getString("price"));
            material.setAmount(object.getString("amount"));
            material.setUnit(object.getString("unit"));
            material.setSize(object.getString("size"));
            material.setPathImage(object.getString("path-image"));
        }
        connect.closeStream();
        return material;
    }
}
