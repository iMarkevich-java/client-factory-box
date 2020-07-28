package com.markevich.factory;

public class StatusMessage {
    private static final StringBuilder statusMessage = new StringBuilder();
    private static final StatusMessage message = new StatusMessage();

    public static String getStatusMessage() {
        return statusMessage.toString();
    }

    public static void setStatusMessage(String message , int code) {
        statusMessage.append("Status message: ").append(message).append(", ").append("status code: ").append(code).append("\n");
    }

    public static void setStatusMessage(String message) {
        statusMessage.append(message).append("\n");
    }
}
