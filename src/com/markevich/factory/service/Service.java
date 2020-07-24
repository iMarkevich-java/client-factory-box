package com.markevich.factory.service;

import java.util.List;

public interface Service<T> {
    List<T> loadAll();

    T loadById(String id);

    void save(T t);

    void update(T t);

    void delete(String id);
}
