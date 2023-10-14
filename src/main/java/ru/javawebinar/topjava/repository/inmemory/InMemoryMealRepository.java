package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        log.info("save {}", meal);
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
        log.info("delete {}", mealId);
        Meal meal = repository.get(mealId);
        if (meal == null) {
            return false;
        }

        if (userId == meal.getUserId()) {
            return repository.remove(mealId) != null;
        } else {
            return false;
        }
    }

    @Override
    public Meal get(int mealId, int userId) {
        log.info("get {}", mealId);
        Meal meal = repository.get(mealId);
        if (meal == null) {
            return null;
        }

        if (userId == meal.getUserId()) {
            return meal;
        } else {
            return null;
        }
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll");
        return getFilteredList(meal -> meal.getUserId() == userId);
    }

    @Override
    public Collection<Meal> getFilteredListByDate(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getFilteredList");
        return getFilteredList(meal -> meal.getUserId() == userId && DateTimeUtil.isBetweenDates(meal.getDate(), startDate, endDate));
    }

    private Collection<Meal> getFilteredList(Predicate<Meal> predicate) {
        return repository.values()
                .stream()
                .filter(predicate)
                .sorted(Meal.MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }
}

