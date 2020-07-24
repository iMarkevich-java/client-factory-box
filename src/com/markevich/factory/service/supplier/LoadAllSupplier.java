package com.markevich.factory.service.supplier;

import biznesObgectFactory.Supplier;
import com.markevich.factory.Connect;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.util.ArrayList;
import java.util.List;

public class LoadAllSupplier {

    public List<Supplier> loadAllSupplier() {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        List<Supplier> listSupplier = new ArrayList<>();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        Integer statusCode = jsonObjectHeader.getInt("status-code");
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
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

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("get-all-supplier");
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("client-list").value("bame");
        jsonWriter.endObject();
    }
}
