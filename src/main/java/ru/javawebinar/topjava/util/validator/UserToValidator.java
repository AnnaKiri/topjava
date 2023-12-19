package ru.javawebinar.topjava.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@Component
public class UserToValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserToValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserTo userTo = (UserTo) target;

        if (!StringUtils.hasLength(userTo.getEmail())) {
            return;
        }

        try {
            User userByEmail = userService.getByEmail(userTo.getEmail());
            if (userTo.getId() == null || !userByEmail.getId().equals(userTo.getId())) {
                errors.rejectValue("email", "Duplicate.userTo.email");
            }
        } catch (NotFoundException ignored) {

        }
    }
}