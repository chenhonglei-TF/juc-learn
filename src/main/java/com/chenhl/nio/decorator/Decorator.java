package com.chenhl.nio.decorator;

/**
 * 装饰角色 相当于I/O体系中的FilterInputStream/FilterOutputStream，装饰模式的核心
 * 1. 实现抽象构建角色
 * 2. 持有抽象构建角色的引用
 *
 */
public class Decorator implements Component {

    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    public void doSomething() {
        component.doSomething();
    }
}
