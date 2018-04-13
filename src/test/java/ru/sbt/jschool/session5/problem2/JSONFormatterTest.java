package ru.sbt.jschool.session5.problem2;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by user on 10.04.2018.
 */
public class JSONFormatterTest {
    JSONFormatterImpl jsonFormatter = new JSONFormatterImpl();

    @Test public void testOne() {
        A a = new A();

        try {
            assertEquals("{\n" +
                    "    \"array\" : [ \n" +
                    "        \"5\",\n" +
                    "        \"1\",\n" +
                    "        \"a\"\n" +
                    "    ],\n" +
                    "\n" +
                    "    \"a\" : 5,\n" +
                    "\n" +
                    "    \"b\" : Hello,\n" +
                    "\n" +
                    "    \"date\" : 13.04.2018,\n" +
                    "\n" +
                    "    \"integerList\" : [ \n" +
                    "        \"5\",\n" +
                    "        \"15\",\n" +
                    "        \"45\"\n" +
                    "    ],\n" +
                    "\n" +
                    "    \"aFROMB\" : 45,\n" +
                    "}",jsonFormatter.marshall(a, A.class));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
