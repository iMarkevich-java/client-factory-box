package com.markevich.factory.gui.exceptions;


import com.markevich.factory.gui.common.WindowConfigs;

public class UnknownWindowException extends RuntimeException {

    public UnknownWindowException(WindowConfigs config) {
        super("Can not find window with name - " + config);
    }
}
