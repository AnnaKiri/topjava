package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
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

        List<UserMealWithExcess> mealsTo2 = filteredByOneCycle(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo2.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

        System.out.println(filteredByOneStream(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dailyCaloriesMap = new HashMap<>();
        for (UserMeal meal : meals) {
            dailyCaloriesMap.merge(meal.getDate(), meal.getCalories(), Integer::sum);
        }

        List<UserMealWithExcess> mealsTo = new ArrayList<>();
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                UserMealWithExcess userMealWithExcess = createNewUserMealWithExcess(meal, dailyCaloriesMap.get(meal.getDate()) > caloriesPerDay);
                mealsTo.add(userMealWithExcess);
            }
        }
        return mealsTo;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dailyCaloriesMap = meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate,
                        Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createNewUserMealWithExcess(meal, dailyCaloriesMap.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByOneCycle(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dailyCaloriesMap = new HashMap<>();
        Map<LocalDate, AtomicBoolean> excessMap = new HashMap<>();
        List<UserMealWithExcess> filteredMeals = new ArrayList<>();
        for (UserMeal meal : meals) {
            fillUserMealWithExcessList(dailyCaloriesMap, excessMap, filteredMeals, meal, startTime, endTime, caloriesPerDay);
        }
        return filteredMeals;
    }

    public static List<UserMealWithExcess> filteredByOneStream(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        class Accumulator {
            final Map<LocalDate, Integer> dailyCaloriesMap = new HashMap<>();
            final Map<LocalDate, AtomicBoolean> excessMap = new HashMap<>();
            final List<UserMealWithExcess> filteredMeals = new ArrayList<>();

            void add(UserMeal meal) {
                fillUserMealWithExcessList(dailyCaloriesMap, excessMap, filteredMeals, meal, startTime, endTime, caloriesPerDay);
            }

            Accumulator combine(Accumulator another) {
                throw new UnsupportedOperationException("Parallel stream not supported for this operation.");
            }
        }

        return meals.stream().collect(Collector.of(Accumulator::new, Accumulator::add, Accumulator::combine, acc -> acc.filteredMeals));
    }

    private static UserMealWithExcess createNewUserMealWithExcess(UserMeal userMeal, AtomicBoolean excess) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess);
    }

    private static UserMealWithExcess createNewUserMealWithExcess(UserMeal userMeal, boolean excess) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess);
    }

    private static void fillUserMealWithExcessList(Map<LocalDate, Integer> dailyCaloriesMap,
                                                   Map<LocalDate, AtomicBoolean> excessMap,
                                                   List<UserMealWithExcess> filteredMeals,
                                                   UserMeal meal,
                                                   LocalTime startTime,
                                                   LocalTime endTime,
                                                   int caloriesPerDay) {
        LocalDate date = meal.getDate();
        int caloriesForDay = dailyCaloriesMap.merge(date, meal.getCalories(), Integer::sum);
        boolean excessFlag = caloriesForDay > caloriesPerDay;
        AtomicBoolean excessFlagForDay = excessMap.computeIfAbsent(date, key -> new AtomicBoolean(excessFlag));
        excessFlagForDay.compareAndSet(false, excessFlag);

        if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
            filteredMeals.add(createNewUserMealWithExcess(meal, excessFlagForDay));
        }
    }
}
