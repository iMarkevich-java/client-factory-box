package com.markevich.factory.exeptions;

import com.markevich.factory.StatusMessage;

public class ExceptionCloseOutputStream extends RuntimeException {
    public ExceptionCloseOutputStream(String className) {
        StatusMessage.setStatusMessage("Can't close output stream: " + className + "!!!");
    }
}
