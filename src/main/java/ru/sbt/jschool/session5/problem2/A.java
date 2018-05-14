package ru.sbt.jschool.session5.problem2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 10.04.2018.
 */
public class A {
    D[] dArray = new D[5];
    private void arrayImpl(int i) {
        for (int j = 0; j < i; j++) {
            dArray[j] = new D(j, "hi");
        }
    }
    public A(){
        arrayImpl(5);
    }
    String[] strArray = {"Hello", "I'm", "strArray"};
    char[] charArray = {'5', '1', 'a'};
    int[] intArray = {1, 2, 3};
    int a = 5;
    B fromAObject = new B();
    String b = "Hello";
    Date date = new Date();
    List<Integer> integerList = Arrays.asList(5, 15, 45);

}
