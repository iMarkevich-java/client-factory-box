package com.markevich.factory.service.product;

import businessObjectFactoryBox.Product;
import com.markevich.factory.Connect;
import com.markevich.factory.StatusMessage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.util.ArrayList;
import java.util.List;

public class LoadAllProduct {
    private final String command = "get-all-product";

    protected LoadAllProduct() {
    }

    public List<Product> loadAllProduct() {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        List<Product> listProduct = new ArrayList<>();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        int statusCode = jsonObjectHeader.getInt("status-code");
        StatusMessage.setStatusMessage(command  + " : " + jsonObjectHeader.getString("status-message"), statusCode);
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
        for (int i = 0; i < jsonArray.length(); i++) {
            Product product = new Product();
            JSONObject object = jsonArray.getJSONObject(i);
            product.setColor(object.getString("color"));
            product.setTexture(object.getString("texture"));
            product.setImage(object.getString("image"));
            product.setId(object.getString("id"));
            product.setOrderId(object.getString("order-id"));
            product.setMaterial(object.getString("material"));
            product.setSizeBox(object.getString("size-box"));
            listProduct.add(product);
        }
        connect.closeStream();
        return listProduct;
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
        jsonWriter.key("product-list").value("");
        jsonWriter.endObject();
    }
}
