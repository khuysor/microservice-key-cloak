package com.huysor.saas.common.utils;

public class StringUtils {
    static boolean isValidString(String string) {
        return string != null && (!string.isBlank());
    }
}
