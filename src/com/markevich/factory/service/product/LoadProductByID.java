package com.markevich.factory.service.product;

import biznesObgectFactory.Product;
import com.markevich.factory.Connect;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class LoadProductByID {

    protected LoadProductByID() {
    }

    public Product loadProductByID(String id) {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter, id);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        Product product = new Product();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        Integer statusCode = jsonObjectHeader.getInt("status-code");
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
        JSONObject object = jsonArray.getJSONObject(0);
        product.setOrderId(object.getString("order-id"));
        product.setMaterial(object.getString("material"));
        product.setTexture(object.getString("texture"));
        product.setImage(object.getString("image"));
        product.setColor(object.getString("color"));
        product.setSizeBox(object.getString("size-box"));
        product.setId(object.getString("id"));
        connect.closeStream();
        return product;
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("get-product-by-id");
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter, String id) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("id").value(id);
        jsonWriter.endObject();
    }
}
