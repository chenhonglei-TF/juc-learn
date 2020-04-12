package com.chenhl.myproject.nio.decorator;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

public class Main {

    public static void main(String[] args) {
        Component component = new ConcreteDecorator2(new ConcreteDecorator1(new ConcreteComponent()));
//        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(".")));
        component.doSomething();

        System.out.println("-----");
        Component component1 = new ConcreteDecorator1(new ConcreteComponent());
        component1.doSomething();

        System.out.println("-----");
        Component component2 = new ConcreteComponent();
        component2.doSomething();
    }
}
