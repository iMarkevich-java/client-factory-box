package com.markevich.factory.service.supplier;

import biznesObgectFactory.Supplier;
import com.markevich.factory.service.Service;

import java.util.List;

public class SupplierServices implements Service<Supplier> {

    @Override
    public List<Supplier> loadAll() {
        LoadAllSupplier loadAllSupplier = new LoadAllSupplier();
        return loadAllSupplier.loadAllSupplier();
    }

    @Override
    public Supplier loadById(String id) {
        LoadSupplierByID loadSupplierByID = new LoadSupplierByID();
        return loadSupplierByID.loadSupplierByID(id);
    }

    @Override
    public void save(Supplier supplier) {
        SaveSupplier saveSupplier = new SaveSupplier();
        saveSupplier.saveSupplier(supplier);
    }

    @Override
    public void update(Supplier supplier) {
        UpdateSupplier updateSupplier = new UpdateSupplier();
        updateSupplier.updateSupplier(supplier);
    }

    @Override
    public void delete(String id) {
        DeleteSupplier deleteSupplier = new DeleteSupplier();
        deleteSupplier.deleteSupplier(id);
    }
}
