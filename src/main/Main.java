package main;

import components.Connection;
import components.Port;
import components.Type;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        Port port1;
        Port port2;
        Port port3;
        Port out1;
        Port out2;
        Port out3;

        port1 = new Port("Human", "in");
        System.out.println("hello, world " + port1.getId());

        port2 = new Port("Time", "in");
        System.out.println("hello, world " + port2.getId());

        port3 = new Port("Food", "in");
        System.out.println("hello, world " + port3.getId());

        out1 = new Port("Human", "out");
        out2 = new Port("Time", "out");
        out3 = new Port("Food", "out");

        Connection connection1 = new Connection(out1, port1);

        ArrayList<Double> values = new ArrayList<>();

        values.add(42.0);
        values.add(30.0);
        out1.update("Human", values);

        Connection connection2 = new Connection(out2, port2);

        values = new ArrayList<>();

        values.add(4.0);
        values.add(20.0);
        out2.update("Time", values);


        Connection connection3 = new Connection(out3, port3);

        values = new ArrayList<>();

        values.add(666.0);
        out3.update("Food", values);

    }
}
