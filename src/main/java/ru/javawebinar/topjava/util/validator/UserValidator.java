package ru.javawebinar.topjava.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
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
        return String.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String userEmail = (String) target;

        if (!StringUtils.hasLength(userEmail)) {
            return;
        }

        try {
            userService.getByEmail(userEmail);
        } catch (NotFoundException e) {
            return;
        }
        errors.rejectValue("email", "Duplicate.userTo.email");
    }
}
