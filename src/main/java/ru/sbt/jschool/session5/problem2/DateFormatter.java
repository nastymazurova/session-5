package ru.sbt.jschool.session5.problem2;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 10.04.2018.
 */
public class DateFormatter implements JSONTypeFormatter<Date> {
    @Override public String format(Date date, JSONFormatter formatter, int levelCounter) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateToString = simpleDateFormat.format(date);
        return dateToString;
    }
}
