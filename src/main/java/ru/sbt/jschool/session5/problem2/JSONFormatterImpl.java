package ru.sbt.jschool.session5.problem2;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by user on 10.04.2018.
 */
public class JSONFormatterImpl implements JSONFormatter {
    private Map<Class, JSONTypeFormatter> types = new HashMap<>();
    private List<String> primitiveTypes = new ArrayList<>();
    StringBuilder strOut = new StringBuilder();
    private int levelCounter = 1;

    public JSONFormatterImpl() {
        primitiveTypesAdd();
        types.put(Date.class, new DateFormatter());
        types.put(Object.class, new ObjectFormatter());
        types.put(String.class, new StringFormatter());
        types.put(Array.class, new ArrayFormatter());
        types.put(Collection.class, new CollectionFormatter());
    }

    private void primitiveTypesAdd() {
        primitiveTypes.add("int");
        primitiveTypes.add("long");
        primitiveTypes.add("double");
        primitiveTypes.add("float");
        primitiveTypes.add("char");
        primitiveTypes.add("byte");
        primitiveTypes.add("short");
        primitiveTypes.add("boolean");
    }

    @Override
    public String marshall(Object obj) throws IllegalAccessException {
        if (obj == null)
            return "";
        Class c = obj.getClass();
        strOut.append("{\n");
        do {
            Field[] fields = c.getDeclaredFields();

            for (Field f :
                    fields) {
                strOut.append(JSONTypeFormatter.spaceCount(levelCounter));
                if (primitiveTypes.contains(f.getType().toString())) {
                    strOut.append("\"" + f.getName() + "\"" + " : " + f.get(obj).toString());
                } else if (types.containsKey(f.getType())) {
                    strOut.append("\"" + f.getName() + "\"" + " : " + types.get(f.getType()).format(f.get(obj), this, levelCounter));
                } else if (f.getType().isArray()) {
                    strOut.append("\"" + f.getName() + "\"" + " : " + types.get(Array.class).format(f.get(obj), this, levelCounter));
                } else if (f.get(obj) instanceof Collection) {

                    strOut.append("\"" + f.getName() + "\"" + " : " + types.get(Collection.class).format(f.get(obj), this, levelCounter));

                } else if (c != Object.class) {
                    strOut.append("\"" + f.getName() + "\" : ");
                    levelCounter++;
                    marshall(f.get(obj));
                    levelCounter--;
                } else {
                    strOut.append("\"" + f.getName() + "\"" + " : " + types.get(Object.class).format(f.get(obj), this, levelCounter));
                }
                strOut.append(",\n\n");
            }

            strOut.deleteCharAt(strOut.length() - 2);
            //levelCounter--;

            c = c.getSuperclass();
        } while (c != Object.class);

        strOut.deleteCharAt(strOut.length()-2);
        strOut.append(JSONTypeFormatter.spaceCount(levelCounter-1) + '}');
        //strOut.append('}');
        return strOut.toString();
    }

    @Override
    public <T> boolean addType(Class<T> clazz, JSONTypeFormatter<T> format) {
        if (types.containsKey(clazz)) {
            return true;
        } else {
            types.put(clazz, format);
            return false;
        }
    }
}

