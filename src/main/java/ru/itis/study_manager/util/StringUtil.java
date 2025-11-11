package ru.itis.study_manager.util;

public class StringUtil {
    public static boolean isEmpty(String string) {
        return string == null || string.trim().isBlank();
    }

    public static String truncate(String string, int length) {
        if (string == null) {
            return null;
        }
        string = string.trim();
        return string.substring(0, Math.min(length, string.length()));
    }
}
