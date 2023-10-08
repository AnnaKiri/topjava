package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage<T> {

    void clear();

    void update(T object);

    void save(T object);

    T get(int id);

    void delete(int id);

    List<T> getAll();

    int size();
}
