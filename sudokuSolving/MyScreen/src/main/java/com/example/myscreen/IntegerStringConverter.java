package com.example.myscreen;
import javafx.util.StringConverter;

public class IntegerStringConverter extends StringConverter<Integer> {
    @Override
    public String toString(Integer object) {
        return object == null ? "" : object.toString();
    }

    @Override
    public Integer fromString(String string) {
        if (string == null || string.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return 0; // Handle invalid input by returning 0
        }
    }
}

