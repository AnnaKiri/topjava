package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {

    void clear();

    void update(Meal meal);

    void save(Meal meal);

    Meal get(int id);

    void delete(int id);

    List<Meal> getAll();

    int size();
}
