package ru.itis.study_manager.util.validator;

import ru.itis.study_manager.model.User;

public class UserValidator implements Validator<User> {
    private final Validator<String> username = new UsernameValidator();
    private final Validator<String> password = new PasswordValidator();

    @Override
    public boolean validate(User input) {
        return username.validate(input.getUsername()) &&
                password.validate(input.getPassword());
    }
}
