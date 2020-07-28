package com.markevich.factory.gui.exceptions;

import com.markevich.factory.StatusMessage;
import com.markevich.factory.gui.common.WindowConfigs;

public class CanNotCreateWindowException extends RuntimeException {

    public CanNotCreateWindowException(Throwable cause, WindowConfigs windowName) {
        StatusMessage.setStatusMessage("Can not create Window: " + windowName + cause);
    }
}
