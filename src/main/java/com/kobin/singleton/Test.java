package com.kobin.singleton;

/**
 * Created by @author shibinbin on 2017/12/15.
 */

public class Test {

    public static void main(String[] args) {
        Test test = new Test();
        test t = new test();
        System.out.println(test.a);
    }
    static int a = 0;
}

class test{
    static int a = 1;
}