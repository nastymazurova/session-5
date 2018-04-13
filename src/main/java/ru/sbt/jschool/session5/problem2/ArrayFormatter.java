package ru.sbt.jschool.session5.problem2;

import java.lang.reflect.Array;
import java.util.Map;

/**
 * Created by user on 12.04.2018.
 */
public class ArrayFormatter implements JSONTypeFormatter<Object> {
    @Override
    public String format(Object array, JSONFormatter formatter,int levelCounter) {
        StringBuilder str = new StringBuilder();
        levelCounter++;
        str.append("[ \n");
        for (int i = 0; i < Array.getLength(array); i++) {
            str.append(JSONTypeFormatter.spaceCount(levelCounter));
            str.append("\"" + Array.get(array, i) + "\"");
            if (i != Array.getLength(array) - 1) {
                str.append(",\n");
            }
        }
        levelCounter--;
        str.append('\n');
        str.append(JSONTypeFormatter.spaceCount(levelCounter));
        str.append("]");
        return str.toString();
    }
}
