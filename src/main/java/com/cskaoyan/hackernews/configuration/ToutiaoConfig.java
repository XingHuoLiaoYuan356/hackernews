package com.cskaoyan.hackernews.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;


@Component
public class ToutiaoConfig {


    @Value("${server.address}")
    public String ServerAddress ;//="192.168.2.100";

     /*{
        try {
            ServerAddress= InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            ServerAddress="localhost";
        }
    }*/

    @Value("${server.port}")
    public String ServerPort;

    @Value("${server.context-path}")
    public String ContextPath;

    @Value("${imgae-dir}")
    public   String IMAGE_DIR ;

    public String getIMAGE_DIR() {
        return IMAGE_DIR;
    }

    public void setIMAGE_DIR(String IMAGE_DIR) {
        this.IMAGE_DIR = IMAGE_DIR;
    }

    public String getServerAddress() {
        return ServerAddress;
    }

    public void setServerAddress(String serverAddress) {
        ServerAddress = serverAddress;
    }

    public String getServerPort() {
        return ServerPort;
    }

    public void setServerPort(String serverPort) {
        ServerPort = serverPort;
    }

    public String getContextPath() {
        return ContextPath;
    }

    public void setContextPath(String contextPath) {
        ContextPath = contextPath;
    }
}
