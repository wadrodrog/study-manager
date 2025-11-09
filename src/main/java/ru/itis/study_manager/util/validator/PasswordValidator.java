package ru.itis.study_manager.util.validator;

public class PasswordValidator extends RegexValidator {
    public PasswordValidator() {
        super("^.{8,255}$");
    }
}
