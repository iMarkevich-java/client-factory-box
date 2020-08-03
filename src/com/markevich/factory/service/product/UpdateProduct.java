package com.markevich.factory.service.product;

import businessObjectFactoryBox.Product;
import com.markevich.factory.DataExchange;

import java.util.HashMap;
import java.util.Map;

public class UpdateProduct {
    public void updateProduct(Product product) {
        DataExchange connect = new DataExchange();
        connect.setCommand("update-product");
        Map<String, String> map = new HashMap<>();
        map.put("id", product.getId());
        map.put("material", product.getMaterial());
        map.put("size-box", product.getSizeBox());
        map.put("order-id", product.getOrderId());
        map.put("color", product.getColor());
        map.put("image", product.getImage());
        map.put("texture", product.getTexture());
        connect.setMap(map);
        connect.writer();
        connect.read();
        connect.closeStream();
    }
}
