package com.geektcp.common.core.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @author tanghaiyang on 2018/7/4.
 */
public class LocalHostUtils {

    private static volatile String cachedIpAddress;

    public String getIp() {
        if (null != cachedIpAddress) {
            return cachedIpAddress;
        }
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (final SocketException ex) {
            // throw new JobException(ex);
        }
        String localIpAddress = null;
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = netInterfaces.nextElement();
            Enumeration<InetAddress> ipAddresses = netInterface.getInetAddresses();
            while (ipAddresses.hasMoreElements()) {
                InetAddress ipAddress = ipAddresses.nextElement();
                if (isPublicIpAddress(ipAddress)) {
                    String publicIpAddress = ipAddress.getHostAddress();
                    cachedIpAddress = publicIpAddress;
                    return publicIpAddress;
                }
                if (isLocalIpAddress(ipAddress)) {
                    localIpAddress = ipAddress.getHostAddress();
                }
            }
        }
        cachedIpAddress = localIpAddress;
        return localIpAddress;
    }

    public String getHostName() {
        return getLocalHost().getHostName();
    }

    private boolean isPublicIpAddress(final InetAddress ipAddress) {
        return !ipAddress.isSiteLocalAddress() && !ipAddress.isLoopbackAddress() && !isV6IpAddress(ipAddress);
    }

    private boolean isLocalIpAddress(final InetAddress ipAddress) {
        return ipAddress.isSiteLocalAddress() && !ipAddress.isLoopbackAddress() && !isV6IpAddress(ipAddress);
    }

    private boolean isV6IpAddress(final InetAddress ipAddress) {
        return ipAddress.getHostAddress().contains(":");
    }

    private static InetAddress getLocalHost() {
        InetAddress result = null;
        try {
            result = InetAddress.getLocalHost();
        } catch (final UnknownHostException ex) {
            // throw new JobException(ex);
        }
        return result;
    }
}
