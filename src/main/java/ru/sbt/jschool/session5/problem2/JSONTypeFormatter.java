package ru.sbt.jschool.session5.problem2;

import java.util.Map;

/**
 * Created by user on 10.04.2018.
 */
public interface JSONTypeFormatter<T> {
    String format(T t, JSONFormatter formatter, int levelCounter);
    static String spaceCount(int levelCounter) {
        String strOfSpace = "";
        for (int i = 0; i < levelCounter; i++) {
            strOfSpace += "    ";
        }
        return strOfSpace;
    }
}
