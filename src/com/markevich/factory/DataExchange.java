package com.markevich.factory;

import com.markevich.factory.exeptions.ExceptionCloseInputStream;
import com.markevich.factory.exeptions.ExceptionCloseOutputStream;
import com.markevich.factory.exeptions.ExceptionCloseSocket;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataExchange {
    private Socket socket = null;
    private OutputStreamWriter outputStream;
    private InputStream inputStream;
    private String command;
    private Map<String, String> map  = new HashMap<>();
    private String message;

    private Socket connectSocket() {
        if (socket == null) {
            Socket socketForStream = null;
            try {
                socketForStream = new Socket(ConnectDataUser.getIp(), ConnectDataUser.getPort());
            } catch (IOException | NullPointerException e) {
                message = "Server not find";
                StatusMessage.setStatusMessage(message, 500);
            }
            return socketForStream;
        }
        return socket;
    }

    public void writer() {
        socket = connectSocket();
        try {
            outputStream = new OutputStreamWriter(socket.getOutputStream());
        } catch (IOException | NullPointerException e) {
            StatusMessage.setStatusMessage("Server lost connect");
            return;
        }
        JSONWriter jsonWriter = new JSONWriter(outputStream);
        jsonWriter.object();
        jsonWriter.key("headers");
        jsonWriter.object();
        jsonWriter.key("command-name").value(command);
        jsonWriter.endObject();
        jsonWriter.key("parameters");
        jsonWriter.object();
        if(!(map.isEmpty())) {
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                jsonWriter.key(key).value(map.get(key));
            }
        }
        jsonWriter.endObject();
        jsonWriter.endObject();
        flush();
    }

    public JSONObject read() {
        socket = connectSocket();
        try {
            inputStream = socket.getInputStream();
        } catch (IOException | NullPointerException e) {
            StatusMessage.setStatusMessage("Server lost connect");
        }
        if(inputStream == null) {
            message = "Not connect";
            return new JSONObject();
        }
        JSONTokener jsonTokener = new JSONTokener(inputStream);
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        JSONObject jsonObjectHeader = jsonObject.getJSONObject("headers");
        int statusCode = jsonObjectHeader.getInt("status-code");
        message = jsonObjectHeader.getString("status-message");
        StatusMessage.setStatusMessage(command + " : " + message, statusCode);
        return jsonObject;
    }

    private void flush() {
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeStream() {
        try {
            if(!(outputStream == null)) {
                outputStream.close();
            }
        } catch (IOException e) {
            throw new ExceptionCloseInputStream(getClass().getName());
        }
        try {
            if(!(inputStream == null)) {
                inputStream.close();
            }
        } catch (IOException e) {
            throw new ExceptionCloseOutputStream(getClass().getName());
        }
        try {
            if(!(socket == null)) {
                socket.close();
            }
        } catch (IOException e) {
            throw new ExceptionCloseSocket(getClass().getName());
        }
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getMessage() {
        return message;
    }
}
