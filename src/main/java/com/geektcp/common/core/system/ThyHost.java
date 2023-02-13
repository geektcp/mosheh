package com.geektcp.common.core.system;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author geektcp on 2018/7/4.
 */
public class ThyHost {

    private ThyHost(){
    }

    private static volatile String cachedPublicIp;
    private static volatile String cachedPrivateIp;
    private static volatile String cachedLoopbackIp;
    private static volatile String cachedHostname;

    public static String getPublicIp() {
        if (null != cachedPublicIp) {
            return cachedPublicIp;
        }
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (final SocketException e) {
            Sys.p(e.getMessage());
        }
        if (Objects.isNull(netInterfaces)) {
            return "";
        }
        String localIpAddress = null;
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = netInterfaces.nextElement();
            Enumeration<InetAddress> ipAddresses = netInterface.getInetAddresses();
            while (ipAddresses.hasMoreElements()) {
                InetAddress ipAddress = ipAddresses.nextElement();
                if (isPublicIpAddress(ipAddress)) {
                    cachedPublicIp = ipAddress.getHostAddress();
                    return cachedPublicIp;
                }
                if (isLocalIpAddress(ipAddress)) {
                    localIpAddress = ipAddress.getHostAddress();
                }
            }
        }
        cachedPublicIp = localIpAddress;
        return localIpAddress;
    }

    public static String getHostName() {
        if(Objects.nonNull(cachedHostname)){
            return cachedHostname;
        }
        try {
            InetAddress result = InetAddress.getLocalHost();
            if(Objects.isNull(result)){
                return "";
            }
            cachedHostname = result.getHostName();
            return cachedHostname;
        } catch (final UnknownHostException e) {
            Sys.p(e.getMessage());
            return null;
        }

    }

    public static String getPrivateIp() {
        if(Objects.nonNull(cachedPrivateIp)){
            return cachedPrivateIp;
        }
        try {
            InetAddress result = InetAddress.getLocalHost();
            if(Objects.isNull(result)){
                return "";
            }
            cachedPrivateIp = result.getHostAddress();
            return cachedPrivateIp;
        } catch (final UnknownHostException e) {
            Sys.p(e.getMessage());
            return null;
        }

    }

    public static String getLoopbackIp() {
        if(Objects.nonNull(cachedLoopbackIp)){
            return cachedLoopbackIp;
        }
        cachedLoopbackIp = InetAddress.getLoopbackAddress().getHostAddress();
        return cachedLoopbackIp;
    }


    private static boolean isPublicIpAddress(final InetAddress ipAddress) {
        return !ipAddress.isSiteLocalAddress() && !ipAddress.isLoopbackAddress() && isIpV4Address(ipAddress);
    }

    private static boolean isLocalIpAddress(final InetAddress ipAddress) {
        return ipAddress.isSiteLocalAddress() && !ipAddress.isLoopbackAddress() && isIpV4Address(ipAddress);
    }

    private static boolean isIpV4Address(final InetAddress ipAddress) {
        if(Objects.isNull(ipAddress)){
            return false;
        }
        String ipRaw = ipAddress.getHostAddress();
        if(Objects.isNull(ipRaw)){
            return false;
        }
        boolean isIpV6 = ipRaw.contains(":");
        boolean isIpV4 = ipRaw.contains(".");
        return isIpV4 && !isIpV6;
    }


}
