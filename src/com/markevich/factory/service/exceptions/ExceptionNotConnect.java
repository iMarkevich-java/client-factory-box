package com.markevich.factory.service.exceptions;

import com.markevich.factory.StatusMessage;

public class ExceptionNotConnect extends RuntimeException {
    public ExceptionNotConnect(String className) {
        StatusMessage.setStatusMessage("Can't created connect socket: " + className);
    }
}
