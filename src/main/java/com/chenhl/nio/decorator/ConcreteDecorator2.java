package com.chenhl.nio.decorator;

/**
 * 具体装饰角色 相当于I/O体系中的DataInputStream
 */
public class ConcreteDecorator2 extends Decorator {

    public ConcreteDecorator2(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnotherThing();
    }

    private void doAnotherThing() {
        System.out.println("功能C");
    }
}
