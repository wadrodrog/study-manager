package ru.itis.study_manager.util.validator;

import java.util.regex.Pattern;

public class RegexValidator implements Validator {
    private final Pattern pattern;

    public RegexValidator(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public boolean validate(String input) {
        if (input == null) {
            return false;
        }
        return pattern.matcher(input).matches();
    }
}
