package com.markevich.factory.exeptions;

import com.markevich.factory.StatusMessage;

public class ExceptionCloseSocket extends RuntimeException {
    public ExceptionCloseSocket(String className) {
        StatusMessage.setStatusMessage("Can't close socket " + className);
    }
}
