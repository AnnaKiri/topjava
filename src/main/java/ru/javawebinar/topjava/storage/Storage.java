package ru.javawebinar.topjava.storage;

import java.util.List;

public interface Storage<T> {

    T update(T object);

    T create(T object);

    T get(int id);

    void delete(int id);

    List<T> getAll();
}
