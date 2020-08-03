package com.markevich.factory.service.supplier;

import businessObjectFactoryBox.Supplier;
import com.markevich.factory.DataExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadAllSupplier {
    public List<Supplier> loadAllSupplier() {
        DataExchange connect = new DataExchange();
        connect.setCommand("get-all-supplier");
        connect.writer();
        JSONObject jsonObject = connect.read();
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
        List<Supplier> listSupplier = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Supplier supplier = new Supplier();
            JSONObject object = jsonArray.getJSONObject(i);
            supplier.setCompanyName(object.getString("company-name"));
            supplier.setLegalData(object.getString("legal-data"));
            supplier.setAddress(object.getString("address"));
            supplier.setManager(object.getString("manager"));
            supplier.setId(object.getString("id"));
            listSupplier.add(supplier);
        }
        connect.closeStream();
        return listSupplier;
    }
}
