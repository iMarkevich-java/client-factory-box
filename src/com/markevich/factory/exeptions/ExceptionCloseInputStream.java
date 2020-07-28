package com.markevich.factory.exeptions;

import com.markevich.factory.StatusMessage;

public class ExceptionCloseInputStream extends RuntimeException {
    public ExceptionCloseInputStream(String className) {
        StatusMessage.setStatusMessage("Can't close input stream: " + className + "!!!");
    }
}
