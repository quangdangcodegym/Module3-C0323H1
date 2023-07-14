package com.codegym.manager_products.utils;

import java.util.regex.Pattern;

public class ValidatesUtils {
    public static final String NAME_REGEX = "^[A-Za-z][A-Za-z0-9_ ]{7,19}$";
    public static final String DESCRIPTION_REGEX = "^[A-Za-z][A-Za-z0-9_ ]{14,49}$";
    public static final String PRICE_REGEX = "^[1-9][0-9]{4,8}$";
    public static final String DATE_REGEX = "^(2\\d{3})[\\-](0?[1-9]|1[012])[\\-](0?[1-9]|[12][0-9]|3[01])$";

    public static final String SIZE_REGEX = "^(M|L|XL)$";

    public static boolean isNameValid(String name) {
        return Pattern.matches(NAME_REGEX, name);
    }
    public static boolean isDesValid(String des){
        return Pattern.matches(DESCRIPTION_REGEX, des);
    }
    public static boolean isPriceValid(String price) {
        return Pattern.matches(PRICE_REGEX, price);
    }
    public static boolean isDateValid(String date) {
        return Pattern.matches(DATE_REGEX, date);
    }
    public static boolean isSizeValid(String size) {
        return Pattern.matches(SIZE_REGEX, size);
    }


}
