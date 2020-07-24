package com.markevich.factory.exeptions;

public class ExceptionCreateOutputStream extends RuntimeException {
    public ExceptionCreateOutputStream(String className) {
        System.out.println("Can't create output stream: " + className + "!!!!!");
    }
}
