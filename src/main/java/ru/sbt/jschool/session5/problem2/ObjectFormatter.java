package ru.sbt.jschool.session5.problem2;


/**
 * Created by user on 10.04.2018.
 */
public class ObjectFormatter implements JSONTypeFormatter<Object> {
    @Override
    public String format(Object object, JSONFormatter formatter, int levelCounter) {
        String objToString = object.toString();
        return objToString;
    }
}
