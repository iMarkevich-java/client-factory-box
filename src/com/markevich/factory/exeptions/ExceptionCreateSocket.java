package com.markevich.factory.exeptions;

public class ExceptionCreateSocket extends RuntimeException {
    public ExceptionCreateSocket(String className) {
        System.out.println("Can't create socket: " + className + "!!!");
    }
}
