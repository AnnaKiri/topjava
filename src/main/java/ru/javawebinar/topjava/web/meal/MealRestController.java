package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getFilteredList(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getFilteredList");
        startDate = startDate != null ? startDate : LocalDate.MIN;
        endDate = endDate != null ? endDate : LocalDate.MAX;
        startTime = startTime != null ? startTime : LocalTime.MIN;
        endTime = endTime != null ? endTime : LocalTime.MAX;
        return MealsUtil.getFilteredTos(service.getFilteredList(authUserId(), startDate, endDate),
                SecurityUtil.authUserCaloriesPerDay(),
                startTime,
                endTime);
    }

    public Meal get(int mealId) {
        log.info("get {}", mealId);
        return service.get(mealId, authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int mealId) {
        log.info("delete {}", mealId);
        service.delete(mealId, authUserId());
    }

    public void update(Meal meal, int mealId) {
        log.info("update {} with id={}", meal, mealId);
        assureIdConsistent(meal, mealId);
        service.update(meal, authUserId());
    }
}