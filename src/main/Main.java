package main;

import components.Type;

public class Main {
    public static void main(String[] args) {
        Type typ = new Type();
        typ.setTypeValue(420.0);
        System.out.println("hello, world " + typ.getTypeValue("Real"));
    }
}
