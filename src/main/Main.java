package main;

import components.Type;

public class Main {
    public static void main(String[] args) {
        Type typ = new Type();
        System.out.println("hello, world " + typ.getTypeValue("Real"));
    }
}