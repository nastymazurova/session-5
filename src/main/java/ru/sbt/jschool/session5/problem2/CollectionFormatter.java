package ru.sbt.jschool.session5.problem2;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * Created by user on 13.04.2018.
 */
public class CollectionFormatter implements JSONTypeFormatter<Collection> {
    @Override
    public String format(Collection collection, JSONFormatter formatter, int levelCounter) {
        Object[] obj = collection.toArray();
        StringBuilder str = new StringBuilder();
        levelCounter++;
        str.append("[ \n");
        for (int i = 0; i < Array.getLength(obj); i++) {
            str.append(JSONTypeFormatter.spaceCount(levelCounter));
            str.append("\"" + Array.get(obj, i) + "\"");
            if (i != Array.getLength(obj) - 1) {
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
