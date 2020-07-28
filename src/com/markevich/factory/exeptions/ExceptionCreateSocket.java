package com.markevich.factory.exeptions;

import com.markevich.factory.StatusMessage;

public class ExceptionCreateSocket extends RuntimeException {
    public ExceptionCreateSocket(String className) {
        StatusMessage.setStatusMessage("Can't create socket: " + className + "!!!");
    }
}
