package ru.javawebinar.topjava.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MealValidator implements Validator {

    private final MealRepository mealRepository;

    @Autowired
    public MealValidator(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Meal.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Meal meal = (Meal) target;
        LocalDateTime mealDateTime = meal.getDateTime();

        if (mealDateTime == null) {
            return;
        }

        List<Meal> meals = mealRepository.getBetweenHalfOpen(mealDateTime, mealDateTime.plusMinutes(1), SecurityUtil.authUserId());
        if (!meals.isEmpty() && (meal.getId() == null || !meals.getFirst().getId().equals(meal.getId()))) {
            errors.rejectValue("dateTime", "Duplicate.meal.dateTime");
        }
    }
}
