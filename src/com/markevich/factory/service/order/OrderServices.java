package com.markevich.factory.service.order;

import businessObjectFactoryBox.Order;
import com.markevich.factory.service.Service;

import java.util.List;

public class OrderServices implements Service<Order> {

    @Override
    public List<Order> loadAll() {
        LoadAllOrder loadAllOrder = new LoadAllOrder();
        return loadAllOrder.loadAllOrder();
    }

    @Override
    public Order loadById(String id) {
        LoadOrderByID loadOrderByID = new LoadOrderByID();
        return loadOrderByID.loadOrderByID(id);
    }

    @Override
    public void save(Order order) {
        SaveOrder saveOrder = new SaveOrder();
        saveOrder.saveOrder(order);
    }

    @Override
    public void update(Order order) {
        UpdateOrder updateOrder = new UpdateOrder();
        updateOrder.updateOrder(order);
    }

    @Override
    public void delete(String id) {
        DeleteOrder deleteOrder = new DeleteOrder();
        deleteOrder.deleteOrder(id);
    }
}
