package com.markevich.factory.gui.exceptions;


import com.markevich.factory.StatusMessage;
import com.markevich.factory.gui.common.WindowConfigs;

public class UnknownWindowException extends RuntimeException {

    public UnknownWindowException(WindowConfigs config) {
        StatusMessage.setStatusMessage("Can not find window with name - " + config);
    }
}
