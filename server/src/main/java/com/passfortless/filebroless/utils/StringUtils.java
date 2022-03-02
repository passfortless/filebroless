package com.passfortless.filebroless.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {

    public boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    public boolean isNotBlank(String value) {
        return !isBlank(value);
    }
}
