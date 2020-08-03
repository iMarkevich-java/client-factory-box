package com.markevich.factory.service.product;

import businessObjectFactoryBox.Product;
import com.markevich.factory.DataExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadAllProduct {
    public List<Product> loadAllProduct() {
        DataExchange connect = new DataExchange();
        connect.setCommand("get-all-product");
        connect.writer();
        JSONObject jsonObject = connect.read();
        List<Product> listProduct = new ArrayList<>();
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
}
