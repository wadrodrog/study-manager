package ru.itis.study_manager.util;

import java.util.regex.Pattern;

public class RegexUtil {
    private final Pattern pattern;

    public RegexUtil(String patternString) {
        this.pattern = Pattern.compile(patternString);
    }

    public boolean validate(String input) {
        return pattern.matcher(input).matches();
    }
}
