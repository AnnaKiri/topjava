package ru.javawebinar.topjava.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;

@Component
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz) || UserTo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Integer id = null;
        String email = null;
        String errorCode = null;

        if (target instanceof User user) {
            email = user.getEmail();
            id = user.getId();
            errorCode = "Duplicate.user.email";
        } else if (target instanceof UserTo userTo) {
            email = userTo.getEmail();
            id = userTo.getId();
            errorCode = "Duplicate.userTo.email";
        }

        if (!StringUtils.hasLength(email)) {
            return;
        }

        User userByEmail = userRepository.getByEmail(email);
        if (userByEmail == null) {
            return;
        }

        boolean newUser = id == null;
        boolean emailBelongsUpdatedUser = id != null && id.equals(userByEmail.getId());
        if (newUser || !emailBelongsUpdatedUser) {
            errors.rejectValue("email", errorCode);
        }
    }
}
