package ru.javawebinar.topjava.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (!StringUtils.hasLength(user.getEmail())) {
            return;
        }

        try {
            User userByEmail = userService.getByEmail(user.getEmail());
            if (user.getId() == null || !userByEmail.getId().equals(user.getId())) {
                errors.rejectValue("email", "Duplicate.user.email");
            }
        } catch (NotFoundException ignored) {

        }
    }
}
