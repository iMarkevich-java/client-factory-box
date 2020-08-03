package com.markevich.factory.service.client;

import businessObjectFactoryBox.Client;
import com.markevich.factory.DataExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadAllClient {
    public List<Client> loadAllClient() {
        DataExchange connect = new DataExchange();
        connect.setCommand("get-all-client");
        connect.writer();
        JSONObject jsonObject = connect.read();
        JSONArray jsonArray = jsonObject.getJSONArray("response-data");
        List<Client> listClient = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Client client = new Client();
            JSONObject object = jsonArray.getJSONObject(i);
            client.setCompanyName(object.getString("company-name"));
            client.setLegalData(object.getString("legal-data"));
            client.setAddress(object.getString("address"));
            client.setManager(object.getString("manager"));
            client.setId(object.getString("id"));
            listClient.add(client);
        }
        connect.closeStream();
        return listClient;
    }
}

