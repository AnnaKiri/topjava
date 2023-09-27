package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, List<UserMeal>> map = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate date = meal.getDate();

            if (!map.containsKey(date)) {
                map.put(date, new ArrayList<>());
            }
            map.get(date).add(meal);
        }

        List<UserMealWithExcess> mealsTo = new ArrayList<>();
        for (Map.Entry<LocalDate, List<UserMeal>> entry : map.entrySet()) {
            int sumCaloriesPerDay = 0;
            List<UserMeal> listMeals = entry.getValue();
            for (UserMeal meal : listMeals) {
                sumCaloriesPerDay = sumCaloriesPerDay + meal.getCalories();
            }

            for (UserMeal meal : listMeals) {
                if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                    UserMealWithExcess userMeal = createNewUserMealWithExcess(meal, sumCaloriesPerDay > caloriesPerDay);
                    mealsTo.add(userMeal);
                }
            }
        }
        return mealsTo;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesMap = meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate,
                        Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(element -> TimeUtil.isBetweenHalfOpen(element.getTime(), startTime, endTime))
                .map(a -> createNewUserMealWithExcess(a, caloriesMap.get(a.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static UserMealWithExcess createNewUserMealWithExcess(UserMeal userMeal, boolean excess) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess);
    }
}
