package com.markevich.factory.service.supplier;

import businessObjectFactoryBox.Supplier;
import com.markevich.factory.Connect;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class SaveSupplier {
    protected SaveSupplier() {
    }

    public void saveSupplier(Supplier supplier) {
        Connect connect = new Connect();
        JSONWriter jsonWriter = connect.getJsonWriter();
        jsonWriter.object();
        buildHeadersSection(jsonWriter);
        buildParameters(jsonWriter, supplier);
        jsonWriter.endObject();
        connect.flush();
        JSONTokener jsonTokener = connect.getJsonTokener();
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        int statusCode = jsonObjectHeader.getInt("status-code");
        String statusMessage = jsonObjectHeader.getString("status-message");
        System.out.println("Status code: " + statusCode + "\nStatus massage: " + statusMessage);
        connect.closeStream();
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value("save-supplier");
        jsonWriter.endObject();
    }

    private void buildParameters(JSONWriter jsonWriter, Supplier supplier) {
        jsonWriter.key("parameters");
        jsonWriter.object();
        jsonWriter.key("id").value(supplier.getId());
        jsonWriter.key("address").value(supplier.getAddress());
        jsonWriter.key("company-name").value(supplier.getCompanyName());
        jsonWriter.key("legal-data").value(supplier.getLegalData());
        jsonWriter.key("manager").value(supplier.getManager());
        jsonWriter.endObject();
    }
}


