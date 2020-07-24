package com.markevich.factory.service.exceptions;

public class ExceptionNotConnect extends RuntimeException {
    public ExceptionNotConnect(String className) {
        System.out.println("Can't created connect socket: " + className);
    }
}
