package ru.sbt.jschool.session5.problem2;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by user on 10.04.2018.
 */
public class JSONFormatterTest {
    JSONFormatterImpl jsonFormatter = new JSONFormatterImpl();
    @Test public void testOne() throws IllegalAccessException {
        A a = new A();

        try {
            assertEquals("{\n" +
                    "    \"dArray\" : [\n" +
                    "        {\n" +
                    "            \"i\" : 0,\n" +
                    "            \"str\" : hi\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"i\" : 1,\n" +
                    "            \"str\" : hi\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"i\" : 2,\n" +
                    "            \"str\" : hi\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"i\" : 3,\n" +
                    "            \"str\" : hi\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"i\" : 4,\n" +
                    "            \"str\" : hi\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"strArray\" : [\n" +
                    "        {\n" +
                    "            \"Hello\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"I'm\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"strArray\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"charArray\" : [ \n" +
                    "        \"5\",\n" +
                    "        \"1\",\n" +
                    "        \"a\"\n" +
                    "    ],\n" +
                    "    \"intArray\" : [ \n" +
                    "        \"1\",\n" +
                    "        \"2\",\n" +
                    "        \"3\"\n" +
                    "    ],\n" +
                    "    \"a\" : 5,\n" +
                    "    \"fromAObject\" : \n" +
                    "    {\n" +
                    "        \"aFromB\" : 45\n" +
                    "        \"strFromC\" : Hi, I'm from class \"C\"\n" +
                    "    },\n" +
                    "    \"b\" : Hello,\n" +
                    "    \"date\" : 14.05.2018,\n" +
                    "    \"integerList\" : [ \n" +
                    "        \"5\",\n" +
                    "        \"15\",\n" +
                    "        \"45\"\n" +
                    "    ]\n" +
                    "}", jsonFormatter.marshall(a));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
