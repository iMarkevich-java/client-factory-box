package com.markevich.factory.service.material;

import businessObjectFactoryBox.Material;
import com.markevich.factory.Connect;
import com.markevich.factory.StatusMessage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class UpdateMaterial {
    private final String command = "update-material";

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
        int statusCode = jsonObjectHeader.getInt("status-code");
        StatusMessage.setStatusMessage(command  + " : " + jsonObjectHeader.getString("status-message"), statusCode);
        connect.closeStream();
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value(command);
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
