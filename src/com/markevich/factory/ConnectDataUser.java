package com.markevich.factory;

public class ConnectDataUser {

    public static final ConnectDataUser connectDataUser = new ConnectDataUser();
    private static String ipConnect;
    private static Integer portConnect;

    public static String getIp() {
        return ipConnect;
    }

    public static void setIp(String ip) {
        ipConnect = ip;
    }

    public static Integer getPort() {
        return portConnect;
    }

    public static void setPort(Integer port) {
        portConnect = port;
    }
}
