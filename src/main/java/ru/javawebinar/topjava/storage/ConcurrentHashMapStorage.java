package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentHashMapStorage implements Storage<Meal> {

    private final AtomicInteger counter = new AtomicInteger(0);

    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Meal meal) {
        storage.replace(meal.getId(), meal);
    }

    @Override
    public void save(Meal meal) {
        int id = counter.incrementAndGet();
        meal.setId(id);
        storage.put(id, meal);
    }

    @Override
    public Meal get(int id) {
        return storage.get(id);
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
