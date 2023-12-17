package ru.javawebinar.topjava.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;

@Component
public class MealValidator implements Validator {
    private final MealRepository mealRepository;

    @Autowired
    public MealValidator(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Meal meal = (Meal) target;
        LocalDateTime mealDateTime = meal.getDateTime();
        User user = meal.getUser();

        if (mealDateTime == null || user == null || user.getId() == null) {
            return;
        }

        if (!mealRepository.getBetweenHalfOpen(mealDateTime, mealDateTime.plusMinutes(1), user.getId()).isEmpty()) {
            errors.rejectValue("dateTime", "Duplicate.meal.dateTime");
        }
    }
}
