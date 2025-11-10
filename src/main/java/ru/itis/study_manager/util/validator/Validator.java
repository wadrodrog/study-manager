package ru.itis.study_manager.util.validator;

public interface Validator<T> {
    boolean validate(T input);
}
