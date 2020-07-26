package com.markevich.factory;

import com.markevich.factory.exeptions.ExceptionCloseInputStream;
import com.markevich.factory.exeptions.ExceptionCloseOutputStream;
import com.markevich.factory.exeptions.ExceptionCloseSocket;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Connect {
    private Socket socket = null;
    private OutputStreamWriter outputStream;
    private InputStream inputStream;

    private Socket connectSocket() {
        if (socket == null) {
            Socket socketForStream = null;
            try {
                socketForStream = new Socket(ConnectDataUser.getIp(), ConnectDataUser.getPort());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return socketForStream;
        }
        return socket;
    }

    public JSONWriter getJsonWriter() {
        socket = connectSocket();
        try {
            outputStream = new OutputStreamWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONWriter(outputStream);
    }

    public JSONTokener getJsonTokener() {
        socket = connectSocket();
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONTokener(inputStream);
    }

    public void flush() {
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeStream() {
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new ExceptionCloseInputStream(getClass().getName());
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new ExceptionCloseOutputStream(getClass().getName());
        }
        try {
            socket.close();
        } catch (IOException e) {
            throw new ExceptionCloseSocket(getClass().getName());
        }
    }
}
