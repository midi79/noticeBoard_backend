package com.midi279.portfolio.noticeboard.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate convertStringToLocalDate(String dateString) {
        return LocalDate.parse(dateString, formatter);
    }

    public static String convertLocalDateToString(LocalDate date) {
        return date.format(formatter);
    }
}
