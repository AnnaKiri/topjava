package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int NOT_FOUND = 10;

    public static final Meal userMeal1 = new Meal(START_SEQ + 3, LocalDateTime.of(2023, Month.OCTOBER, 15, 10, 30), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(START_SEQ + 4, LocalDateTime.of(2023, Month.OCTOBER, 15, 14, 30), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(START_SEQ + 5, LocalDateTime.of(2023, Month.OCTOBER, 15, 19, 0), "Ужин", 500);
    public static final Meal userMeal4 = new Meal(START_SEQ + 6, LocalDateTime.of(2023, Month.OCTOBER, 17, 0, 0), "Еда на граничное значение", 500);
    public static final Meal userMeal5 = new Meal(START_SEQ + 7, LocalDateTime.of(2023, Month.OCTOBER, 17, 12, 30), "Обед", 800);
    public static final Meal userMeal6 = new Meal(START_SEQ + 8, LocalDateTime.of(2023, Month.OCTOBER, 17, 18, 0), "Ужин", 1000);
    public static final Meal adminMeal1 = new Meal(START_SEQ + 9, LocalDateTime.of(2023, Month.OCTOBER, 18, 10, 0), "Завтрак", 500);
    public static final Meal adminMeal2 = new Meal(START_SEQ + 10, LocalDateTime.of(2023, Month.OCTOBER, 18, 13, 0), "Обед", 700);
    public static final Meal adminMeal3 = new Meal(START_SEQ + 11, LocalDateTime.of(2023, Month.OCTOBER, 18, 19, 0), "Ужин", 1200);
    public static final Meal adminMeal4 = new Meal(START_SEQ + 12, LocalDateTime.of(2023, Month.OCTOBER, 20, 10, 10), "Завтрак", 500);
    public static final Meal adminMeal5 = new Meal(START_SEQ + 13, LocalDateTime.of(2023, Month.OCTOBER, 20, 14, 0), "Обед", 500);
    public static final Meal adminMeal6 = new Meal(START_SEQ + 14, LocalDateTime.of(2023, Month.OCTOBER, 20, 18, 0), "Ужин", 500);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2023, Month.OCTOBER, 21, 12, 30), "Ужин", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeal1);
        updated.setDateTime(LocalDateTime.of(2023, Month.OCTOBER, 21, 11, 30));
        updated.setDescription("updated meal");
        updated.setCalories(10);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
