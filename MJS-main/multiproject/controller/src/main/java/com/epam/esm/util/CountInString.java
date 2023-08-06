package com.epam.esm.util;

public class CountInString {

    private CountInString() {
    }

    public static int getCountOfElInStr(String str) {
        if (str != null) {
            return str.split(",").length;
        }
        return 0;
    }


}
