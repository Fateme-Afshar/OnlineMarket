package com.example.onlinemarket.utils;

public class PersianNumberUtils {

    public static String convert(float faNumbers) {
        int number=(int) faNumbers;
        String numbers=String.valueOf(number);
        String[][] mChars = new String[][]{
                {"0", "۰"},
                {"1", "۱"},
                {"2", "۲"},
                {"3", "۳"},
                {"4", "۴"},
                {"5", "۵"},
                {"6", "۶"},
                {"7", "۷"},
                {"8", "۸"},
                {"9", "۹"}
        };

        for (String[] num : mChars) {

            numbers = numbers.replace(num[0], num[1]);

        }

        return numbers;
    }

/*    public static String convertFloat(float faNumbers) {
        String numbers=String.valueOf(faNumbers);
        String[][] mChars = new String[][]{
                {"0", "۰"},
                {"1", "۱"},
                {"2", "۲"},
                {"3", "۳"},
                {"4", "۴"},
                {"5", "۵"},
                {"6", "۶"},
                {"7", "۷"},
                {"8", "۸"},
                {"9", "۹"}
        };

        for (String[] num : mChars) {

            numbers = numbers.replace(num[0], num[1]);

        }

        return numbers;
    }*/
}
