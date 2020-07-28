package com.markevich.factory.service.supplier;

import businessObjectFactoryBox.Supplier;
import com.markevich.factory.Connect;
import com.markevich.factory.StatusMessage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class SaveSupplier {
    private final String command = "save-supplier";

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
        StatusMessage.setStatusMessage(command + " : " + jsonObjectHeader.getString("status-message"), statusCode);
        connect.closeStream();
    }

    private void buildHeadersSection(JSONWriter jsonWriter) {
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value(command);
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


