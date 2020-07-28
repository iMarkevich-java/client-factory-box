package com.markevich.factory.exeptions;

import com.markevich.factory.StatusMessage;

public class ExceptionCreateOutputStream extends RuntimeException {
    public ExceptionCreateOutputStream(String className) {
        StatusMessage.setStatusMessage("Can't create output stream: " + className + "!!!!!");
    }
}
