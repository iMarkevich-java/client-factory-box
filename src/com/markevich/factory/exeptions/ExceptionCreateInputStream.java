package com.markevich.factory.exeptions;

import com.markevich.factory.StatusMessage;

public class ExceptionCreateInputStream extends RuntimeException {

    public ExceptionCreateInputStream(String className) {
        StatusMessage.setStatusMessage("Can't create input stream " + className);
    }
}
