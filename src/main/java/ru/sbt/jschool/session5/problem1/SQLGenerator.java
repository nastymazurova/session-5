package ru.sbt.jschool.session5.problem1;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class SQLGenerator {
    private String tableName = null;
    private <T> void tableNameInit(Class<T> clazz) {
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation a :
                annotations) {
            if (a instanceof Table) {
                Table table = (Table) a;
                tableName = table.name();
            }
        }
    }
    private List<String> columnList = new ArrayList<>();
    private List<String> primaryKeyList = new ArrayList<>();
    private <T> void columnAndPrimaryKeyInit(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field f :
                fields) {
            Annotation[] annotationFromFields = f.getAnnotations();
            for (Annotation a :
                    annotationFromFields) {
                if (a instanceof Column) {
                    Column column = (Column) a;
                    if (column.name().equals("")) {
                        columnList.add(f.getName().toLowerCase());
                    } else {
                        columnList.add(column.name().toLowerCase());
                    }
                } else if (a instanceof PrimaryKey) {
                    PrimaryKey primaryKey = (PrimaryKey) a;
                    if (primaryKey.name().equals("")) {
                        primaryKeyList.add(f.getName().toLowerCase());
                    } else {
                        primaryKeyList.add(primaryKey.name().toLowerCase());
                    }
                }
            }
        }
    }
    public <T> String insert(Class<T> clazz) {
        List<String> columnAndPrimaryKeyList = new ArrayList<>();
        tableNameInit(clazz);
        Field[] fields = clazz.getDeclaredFields();
        for (Field f :
                fields) {
            Annotation[] annotationFromFields = f.getAnnotations();
            for (Annotation a :
                    annotationFromFields) {
                if (a instanceof Column) {
                    Column column = (Column) a;
                    if (column.name().equals("")) {
                        columnAndPrimaryKeyList.add(f.getName().toLowerCase());
                    } else {
                        columnAndPrimaryKeyList.add(column.name().toLowerCase());
                    }
                } else if (a instanceof PrimaryKey) {
                    PrimaryKey primaryKey = (PrimaryKey) a;
                    if (primaryKey.name().equals("")) {
                        columnAndPrimaryKeyList.add(f.getName().toLowerCase());
                    } else {
                        columnAndPrimaryKeyList.add(primaryKey.name().toLowerCase());
                    }
                }
            }
        }
        StringBuilder strOutList = new StringBuilder();
        StringBuilder strOutValues = new StringBuilder();
        for (int i = 0; i < columnAndPrimaryKeyList.size(); i++) {
            strOutList.append(columnAndPrimaryKeyList.get(i));
            strOutValues.append('?');
            if (i != columnAndPrimaryKeyList.size() - 1) {
                strOutList.append(", ");
                strOutValues.append(", ");
            }
        }
        return "INSERT INTO " + tableName + '(' + strOutList + ")" + " VALUES (" + strOutValues + ")";
    }

    public <T> String update(Class<T> clazz) {
        tableNameInit(clazz);
        columnAndPrimaryKeyInit(clazz);
        StringBuilder strOutColumns = new StringBuilder();
        for (int i = 0; i < columnList.size(); i++) {
            strOutColumns.append(columnList.get(i));
            strOutColumns.append(" = ?");
            if (i != columnList.size() - 1) {
                strOutColumns.append(", ");
            }
        }
        StringBuilder strOutPrimaryKey = new StringBuilder();
        for (int i = 0; i < primaryKeyList.size(); i++) {
            strOutPrimaryKey.append(primaryKeyList.get(i));
            strOutPrimaryKey.append(" = ?");
            if (i != primaryKeyList.size() - 1) {
                strOutPrimaryKey.append(" AND ");
            }
        }
        return "UPDATE " + tableName + " SET " + strOutColumns + " WHERE " + strOutPrimaryKey;
    }

    public <T> String delete(Class<T> clazz) {
        tableNameInit(clazz);
        columnAndPrimaryKeyInit(clazz);
        StringBuilder strOutPrimaryKey = new StringBuilder();
        for (int i = 0; i < primaryKeyList.size(); i++) {
            strOutPrimaryKey.append(primaryKeyList.get(i));
            strOutPrimaryKey.append(" = ?");
            if (i != primaryKeyList.size() - 1) {
                strOutPrimaryKey.append(" AND ");
            }
        }

        return "DELETE FROM " + tableName + " WHERE " + strOutPrimaryKey;
    }

    public <T> String select(Class<T> clazz) {
        columnAndPrimaryKeyInit(clazz);
        StringBuilder strOutColumns = new StringBuilder();
        for (int i = 0; i < columnList.size(); i++) {
            strOutColumns.append(columnList.get(i));
            if (i != columnList.size() - 1) {
                strOutColumns.append(", ");
            }
        }
        tableNameInit(clazz);
        StringBuilder strOutPrimaryKey = new StringBuilder();
        for (int i = 0; i < primaryKeyList.size(); i++) {
            strOutPrimaryKey.append(primaryKeyList.get(i));
            strOutPrimaryKey.append(" = ?");
            if (i != primaryKeyList.size() - 1) {
                strOutPrimaryKey.append(" AND ");
            }
        }
        return "SELECT " + strOutColumns + " FROM " + tableName + " WHERE " + strOutPrimaryKey;
    }
}
