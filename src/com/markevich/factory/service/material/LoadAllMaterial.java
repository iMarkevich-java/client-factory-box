package com.markevich.factory.service.material;

import biznesObgectFactory.Material;
import com.markevich.factory.Connect;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.util.ArrayList;
import java.util.List;

public class LoadAllMaterial {

    protected LoadAllMaterial() {
    }

    public List<Material> loadAllMaterial() {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        List<Material> listMaterial = new ArrayList<>();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        Integer statusCode = jsonObjectHeader.getInt("status-code");
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
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

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("get-all-material");
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("material-list").value("");
        jsonWriter.endObject();
    }
}

