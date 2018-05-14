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
    private int levelCounter = 0;

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
        strOut.append(JSONTypeFormatter.spaceCount(levelCounter) + "{\n");
        levelCounter++;
        if (types.containsKey(obj.getClass())) {
            strOut.append(JSONTypeFormatter.spaceCount(levelCounter) + "\"" + types.get(obj.getClass()).format(obj, this, levelCounter) + "\"\n");
        } else {
            do {
                Field[] fields = c.getDeclaredFields();

                for (int i = 0; i < fields.length; i++) {
                    strOut.append(JSONTypeFormatter.spaceCount(levelCounter));
                    if (primitiveTypes.contains(fields[i].getType().toString())) {
                        strOut.append("\"" + fields[i].getName() + "\"" + " : " + fields[i].get(obj).toString());
                    } else if (types.containsKey(fields[i].getType())) {
                        strOut.append("\"" + fields[i].getName() + "\"" + " : " + types.get(fields[i].getType()).format(fields[i].get(obj), this, levelCounter));
                    } else if (fields[i].getType().isArray()) {
                        if (!fields[i].get(obj).getClass().getComponentType().isPrimitive()) {
                            strOut.append("\"" + fields[i].getName() + "\"" + " : ");
                            notPrimitiveArrayFormatter(fields[i], obj);
                        } else {
                            strOut.append("\"" + fields[i].getName() + "\"" + " : " + types.get(Array.class).format(fields[i].get(obj), this, levelCounter));
                        }
                    } else if (fields[i].get(obj) instanceof Collection) {

                        strOut.append("\"" + fields[i].getName() + "\"" + " : " + types.get(Collection.class).format(fields[i].get(obj), this, levelCounter));

                    } else if (c != Object.class) {
                        strOut.append("\"" + fields[i].getName() + "\" : " + "\n");
                        marshall(fields[i].get(obj));
                    } else {
                        strOut.append("\"" + fields[i].getName() + "\"" + " : " + types.get(Object.class).format(fields[i].get(obj), this, levelCounter));
                    }
                    if (i != fields.length - 1) {
                        strOut.append(",\n");
                    } else {
                        strOut.append("\n");
                    }
                }
                c = c.getSuperclass();
            } while (c != Object.class);
        }

        levelCounter--;
        strOut.append(JSONTypeFormatter.spaceCount(levelCounter) + "}");
        return strOut.toString();
    }

    private void notPrimitiveArrayFormatter(Field f, Object obj) throws IllegalAccessException {
        strOut.append("[\n");
        levelCounter++;
        for (int i = 0; i < Array.getLength(f.get(obj)); i++) {
            marshall(Array.get(f.get(obj), i));
            if (i != Array.getLength(f.get(obj)) - 1) {
                strOut.append(",\n");
            } else {
                strOut.append("\n");
            }
        }
        levelCounter--;
        strOut.append(JSONTypeFormatter.spaceCount(levelCounter) + "]");
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

