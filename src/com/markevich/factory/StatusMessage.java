package com.markevich.factory;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatusMessage {
    private static StringBuilder statusMessage = new StringBuilder();
    private static final StatusMessage message = new StatusMessage();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static String getStatusMessage() {
        return statusMessage.toString();
    }

    public static void setStatusMessage(String message) {
        checkValueStatusMessage();
        statusMessage.append(formatter.format(LocalDateTime.now())).append(" ").append(message).append("\n");
    }

    public static void setStatusMessage(String message, int code) {
        checkValueStatusMessage();
        statusMessage.append(formatter.format(LocalDateTime.now())).append(" ").append("Status message: ").append(message).append(", ").append("status code: ").append(code).append("\n");
    }

    public static void checkValueStatusMessage() {
        FileWriter fileWriter = null;
        if(statusMessage.capacity() > 10000) {
            try {
                fileWriter = new FileWriter("log.txt", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileWriter != null) {
                    fileWriter.write(statusMessage.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            statusMessage = new StringBuilder();
        }
    }
}
