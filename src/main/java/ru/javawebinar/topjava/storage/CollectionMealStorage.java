package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CollectionMealStorage implements Storage<Meal> {

    private final AtomicInteger counter = new AtomicInteger(0);

    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();

    public CollectionMealStorage() {
        MealsUtil.meals.forEach(this::create);
    }

    @Override
    public Meal update(Meal meal) {
        return storage.replace(meal.getId(), meal) == null ? null : meal;
    }

    @Override
    public Meal create(Meal meal) {
        int id = counter.incrementAndGet();
        meal.setId(id);
        storage.put(id, meal);
        return meal;
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
}
