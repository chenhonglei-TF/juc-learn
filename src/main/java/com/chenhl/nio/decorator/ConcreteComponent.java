package com.chenhl.nio.decorator;

/**
 * 具体构建角色
 * 相当于I/O体系中的FileInputStream/FileOutputStream 节点流
 */
public class ConcreteComponent implements Component {
    public void doSomething() {
        System.out.println("功能A");
    }
}
