package ru.itis.study_manager.converter;

public interface Converter<S, T> {
    T convert (S s);
}
