package ru.sbt.jschool.session5.problem2;


/**
 * Created by user on 12.04.2018.
 */
public class StringFormatter implements JSONTypeFormatter<String> {
    @Override
    public String format(String str, JSONFormatter formatter, int levelCounter) {
        return str;
    }
}
