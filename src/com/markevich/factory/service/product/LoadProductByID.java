package com.markevich.factory.service.product;

import businessObjectFactoryBox.Product;
import com.markevich.factory.DataExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoadProductByID {
    public Product loadProductByID(String id) {
        DataExchange connect = new DataExchange();
        connect.setCommand("get-product-by-id");
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        connect.setMap(map);
        connect.writer();
        JSONObject jsonObject = connect.read();
        Product product = new Product();
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
}
