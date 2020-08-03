package com.markevich.factory.service.material;

import businessObjectFactoryBox.Material;
import com.markevich.factory.DataExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadAllMaterial {
    public List<Material> loadAllMaterial() {
        DataExchange connect = new DataExchange();
        connect.setCommand("get-all-material");
        connect.writer();
        JSONObject jsonObject = connect.read();
        List<Material> listMaterial = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
        for (int i = 0; i < jsonArray.length(); i++) {
            Material material = new Material();
            JSONObject object = jsonArray.getJSONObject(i);
            material.setId(object.getString("id"));
            material.setSupplierId(object.getString("supplier-id"));
            material.setMaterialName(object.getString("material-name"));
            material.setPrice(object.getString("price"));
            material.setAmount(object.getString("amount"));
            material.setUnit(object.getString("unit"));
            material.setSize(object.getString("size"));
            material.setPathImage(object.getString("path-image"));
            listMaterial.add(material);
        }
        connect.closeStream();
        return listMaterial;
    }
}

