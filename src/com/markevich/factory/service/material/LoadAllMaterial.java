package com.markevich.factory.service.material;

import businessObjectFactoryBox.Material;
import com.markevich.factory.Connect;
import com.markevich.factory.StatusMessage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.util.ArrayList;
import java.util.List;

public class LoadAllMaterial {
    private final String command = "get-all-material";

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
        int statusCode = jsonObjectHeader.getInt("status-code");
        StatusMessage.setStatusMessage(command  + " : " + jsonObjectHeader.getString("status-message"), statusCode);
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
        jsonWriter.key("command-name").value(command);
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("material-list").value("");
        jsonWriter.endObject();
    }
}

