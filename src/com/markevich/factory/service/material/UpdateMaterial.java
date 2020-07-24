package com.markevich.factory.service.material;

import biznesObgectFactory.Material;
import com.markevich.factory.Connect;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class UpdateMaterial {

    protected UpdateMaterial() {
    }

    public void updateMaterial(Material material) {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter, material);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        Integer statusCode = jsonObjectHeader.getInt("status-code");
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
        connect.closeStream();
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("update-material");
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter, Material material) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("id").value(material.getId());
        jsonWriter.key("supplier-id").value(material.getSupplierId());
        jsonWriter.key("material-name").value(material.getMaterialName());
        jsonWriter.key("price").value(material.getPrice());
        jsonWriter.key("amount").value(material.getAmount());
        jsonWriter.key("unit").value(material.getUnit());
        jsonWriter.key("size").value(material.getSize());
        jsonWriter.key("path-image").value(material.getPathImage());
        jsonWriter.endObject();
    }
}
