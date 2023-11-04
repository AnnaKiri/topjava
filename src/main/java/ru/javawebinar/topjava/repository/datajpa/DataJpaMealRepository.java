package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudMealRepository;
    private final CrudUserRepository crudUserRepository;

    public DataJpaMealRepository(CrudMealRepository crudMealRepository, CrudUserRepository crudUserRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        Optional<User> user = crudUserRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }
        meal.setUser(user.get());
        if (meal.isNew()) {
            crudMealRepository.save(meal);
            return meal;
        }

        return get(meal.id(), userId) != null ? crudMealRepository.save(meal) : null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return crudMealRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Optional<User> user = crudUserRepository.findById(userId);
        return user.isPresent() ? crudMealRepository.findByIdAndUser(id, user.get()) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        Optional<User> user = crudUserRepository.findById(userId);
        return user.isPresent() ? crudMealRepository.findAllByUserOrderByDateTimeDesc(user.get()) : new ArrayList<>();

    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
//        return crudMealRepository.getBetweenHalfOpen(startDateTime, endDateTime, userId);
        Optional<User> user = crudUserRepository.findById(userId);
        return user.isPresent()
                ? crudMealRepository.findAllByUserAndDateTimeGreaterThanEqualAndDateTimeLessThanOrderByDateTimeDesc(user.get(), startDateTime, endDateTime)
                : new ArrayList<>();
    }

    @Override
    public Meal getMealWithUser(int id, int userId) {
        return crudMealRepository.getMealWithUser(id, userId);
    }
}
