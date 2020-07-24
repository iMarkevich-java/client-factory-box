package com.markevich.factory.service.client;

import biznesObgectFactory.Client;
import com.markevich.factory.service.Service;

import java.util.List;

public class ClientServices implements Service<Client> {


    @Override
    public List<Client> loadAll() {
        LoadAllClient loadAllClient = new LoadAllClient();
        return loadAllClient.loadAllClient();
    }

    @Override
    public Client loadById(String id) {
        LoadClientByID loadClientByID = new LoadClientByID();
        return loadClientByID.loadClientByID(id);
    }

    @Override
    public void save(Client client) {
        SaveClient saveClient = new SaveClient();
        saveClient.saveClient(client);
    }

    @Override
    public void update(Client client) {
        UpdateClient updateClient = new UpdateClient();
        updateClient.updateClient(client);
    }

    @Override
    public void delete(String id) {
        DeleteClient deleteClient = new DeleteClient();
        deleteClient.deleteClient(id);
    }
}
