package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int mealId, int userId) {
        Meal meal = repository.get(mealId);
        if (meal == null) {
            return false;
        }

        if (userId == meal.getId()) {
            return repository.remove(mealId) != null;
        } else {
            return false;
        }
    }

    @Override
    public Meal get(int mealId, int userId) {
        Meal meal = repository.get(mealId);
        if (meal == null) {
            return null;
        }

        if (userId == meal.getId()) {
            return meal;
        } else {
            return null;
        }
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(Meal.MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }
}

