package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

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
        if (!crudUserRepository.existsById(userId)) {
            return null;
        }

        User user = crudUserRepository.getReferenceById(userId);
        meal.setUser(user);

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
        User user = crudUserRepository.getReferenceById(userId);
        return crudMealRepository.findByIdAndUser(id, user);
    }

    @Override
    public List<Meal> getAll(int userId) {
        User user = crudUserRepository.getReferenceById(userId);
        return crudMealRepository.findAllByUserOrderByDateTimeDesc(user);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudMealRepository.getBetweenHalfOpen(startDateTime, endDateTime, userId);
    }

    @Override
    public Meal getMealWithUser(int id, int userId) {
        return crudMealRepository.getMealWithUser(id, userId);
    }
}
