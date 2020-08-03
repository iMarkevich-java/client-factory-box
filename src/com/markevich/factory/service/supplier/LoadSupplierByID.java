package com.markevich.factory.service.supplier;

import businessObjectFactoryBox.Supplier;
import com.markevich.factory.DataExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoadSupplierByID {
    public Supplier loadSupplierByID(String id) {
        DataExchange connect = new DataExchange();
        connect.setCommand("get-supplier-by-id");
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        connect.setMap(map);
        connect.writer();
        JSONObject jsonObject = connect.read();
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
        Supplier supplier = new Supplier();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            supplier.setCompanyName(object.getString("company-name"));
            supplier.setLegalData(object.getString("legal-data"));
            supplier.setAddress(object.getString("address"));
            supplier.setManager(object.getString("manager"));
            supplier.setId(object.getString("id"));
        }
        connect.closeStream();
        return supplier;
    }
}


