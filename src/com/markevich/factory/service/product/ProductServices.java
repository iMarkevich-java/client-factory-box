package com.markevich.factory.service.product;

import businessObjectFactoryBox.Product;
import com.markevich.factory.service.Service;

import java.util.List;

public class ProductServices implements Service<Product> {

    @Override
    public List<Product> loadAll() {
        LoadAllProduct loadAllProduct = new LoadAllProduct();
        return loadAllProduct.loadAllProduct();
    }

    @Override
    public Product loadById(String id) {
        LoadProductByID loadProductByID = new LoadProductByID();
        return loadProductByID.loadProductByID(id);
    }

    @Override
    public void save(Product product) {
        SaveProduct saveProduct = new SaveProduct();
        saveProduct.saveProduct(product);
    }

    @Override
    public void update(Product product) {
        UpdateProduct updateProduct = new UpdateProduct();
        updateProduct.updateProduct(product);
    }

    @Override
    public void delete(String id) {
        DeleteProduct deleteProduct = new DeleteProduct();
        deleteProduct.deleteProduct(id);
    }
}
