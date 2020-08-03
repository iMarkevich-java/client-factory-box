package com.markevich.factory.service.client;

import businessObjectFactoryBox.Client;
import com.markevich.factory.DataExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoadClientByID {
    public Client loadClientByID(String id) {
        DataExchange connect = new DataExchange();
        connect.setCommand("get-client-by-id");
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        connect.setMap(map);
        connect.writer();
        JSONObject jsonObject = connect.read();
        Client client = new Client();
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            client.setCompanyName(object.getString("company-name"));
            client.setLegalData(object.getString("legal-data"));
            client.setAddress(object.getString("address"));
            client.setManager(object.getString("manager"));
            client.setId(object.getString("id"));
        }
        connect.closeStream();
        return client;
    }
}
