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
    private int parentCounter = 0;

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
    public String marshall(Object obj, Class<?> clazz) throws IllegalAccessException {
        if (obj == null)
            return "";
        parentCounter++;

        Field[] fields = clazz.getDeclaredFields();
        if (parentCounter == 1) {
            strOut.append("{\n");
        }

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

            } else {
                if (f.getType().getSuperclass() != null) {
                    strOut.append("\"" + f.getName() + "\" : ");
                    levelCounter++;
                    marshall(f.get(obj), clazz);
                } else {
                    strOut.append("\"" + f.getName() + "\"" + " : " + types.get(Object.class).format(f.get(obj), this, levelCounter));
                }
            }
            strOut.append(",\n\n");
        }

        if (clazz.getSuperclass() != Object.class) {
            marshall(obj, clazz.getSuperclass());
        } else {
            strOut.deleteCharAt(strOut.length() - 2);
            levelCounter--;
            strOut.append(JSONTypeFormatter.spaceCount(levelCounter));
            strOut.append('}');
        }
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

