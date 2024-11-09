package com.onenine.distributelock.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtil {
    public static String getIpAddress(){
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
