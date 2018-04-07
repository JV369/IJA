package main;

import components.Port;
import components.Type;


public class Main {

    public static void main(String[] args) {
        Port port1;
        Port port2;
        Port port3;

        port1 = new Port("Human");
        System.out.println("hello, world " + port1.getId());

        port2 = new Port("Time");
        System.out.println("hello, world " + port2.getId());

        port3 = new Port("Food");
        System.out.println("hello, world " + port3.getId());
    }
}
