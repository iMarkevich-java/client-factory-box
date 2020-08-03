package com.markevich.factory.service.connectSocket;

import com.markevich.factory.DataExchange;

public class ConnectServer {
    public String connect() {
        DataExchange connect = new DataExchange();
        connect.setCommand("verification-connect");
        connect.writer();
        connect.read();
        connect.closeStream();
        return connect.getMessage();
    }
}
