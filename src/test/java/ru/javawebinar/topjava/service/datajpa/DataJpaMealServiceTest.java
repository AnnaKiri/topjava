package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getMealWithUser() {
        Meal actual = service.getMealWithUser(MEAL1_ID, USER_ID);
        MEAL_MATCHER.assertMatch(actual, meal1);
        USER_MATCHER.assertMatch(actual.getUser(), user);
    }

    @Test
    public void getMealAnotherUser() {
        Assert.assertThrows(NotFoundException.class, () -> service.getMealWithUser(MEAL1_ID, ADMIN_ID));
    }
}
