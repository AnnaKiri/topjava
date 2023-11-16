package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Date;
import java.util.Set;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator("registered", "meals");

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int GUEST_ID = START_SEQ + 2;
    public static final int NOT_FOUND = 10;

    public static final User user = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);
    public static final User guest = new User(GUEST_ID, "Guest", "guest@gmail.com", "guest");

    public static final Set<Role> roles = Set.of(Role.USER, Role.ADMIN);

    public static User getNewWithoutRoles() {
        return new User(null, "New", "new@gmail.com", "newPass", 1555, false, new Date(), null);
    }

    public static User getNewWithRoles() {
        User user = getNewWithoutRoles();
        user.setRoles(roles);
        return user;
    }

    public static User getUpdatedWithRoles() {
        User updated = new User(admin);
        updated.setEmail("update@gmail.com");
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        updated.setPassword("newPass");
        updated.setEnabled(false);
        return updated;
    }

    public static User getUpdatedWithoutRoles() {
        User updated = getUpdatedWithRoles();
        updated.setRoles(null);
        return updated;
    }
}
