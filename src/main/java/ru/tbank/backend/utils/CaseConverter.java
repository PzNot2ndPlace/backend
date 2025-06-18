package ru.tbank.backend.utils;

public class CaseConverter {

    public static String toNormalCase(String oldString) {
        if (oldString == null || oldString.isEmpty()) {
            return oldString;
        }

        String result = Character.toUpperCase(oldString.charAt(0)) +
                oldString.substring(1).toLowerCase();

        return result.trim();
    }
}