package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    public static final Comparator<Meal> MEAL_COMPARATOR =
            Comparator.comparing(Meal::getDateTime).reversed();

    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.mealsForUser.forEach(meal -> save(meal, SecurityUtil.USER_ID));
        MealsUtil.mealsForAdmin.forEach(meal -> save(meal, SecurityUtil.ADMIN_ID));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);
        Map<Integer, Meal> userRepository = repository.computeIfAbsent(userId, (id) -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userRepository.put(meal.getId(), meal);
            return meal;
        }

        Meal mealFromRepository = userRepository.get(meal.getId());
        if (mealFromRepository == null) {
            return null;
        }

        return userRepository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int mealId, int userId) {
        log.info("delete {}", mealId);
        Map<Integer, Meal> userRepository = repository.computeIfAbsent(userId, (id) -> new ConcurrentHashMap<>());
        Meal meal = userRepository.get(mealId);
        return meal != null && userRepository.remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId, int userId) {
        log.info("get {}", mealId);
        return repository.computeIfAbsent(userId, (id) -> new ConcurrentHashMap<>()).get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return getFilteredList(meal -> true, userId);
    }

    @Override
    public List<Meal> getFilteredListByDate(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getFilteredList");
        return getFilteredList(meal -> DateTimeUtil.isBetweenDates(meal.getDate(), startDate, endDate), userId);
    }

    private List<Meal> getFilteredList(Predicate<Meal> predicate, int userId) {
        return repository.computeIfAbsent(userId, (id) -> new ConcurrentHashMap<>())
                .values()
                .stream()
                .filter(predicate)
                .sorted(MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }
}

