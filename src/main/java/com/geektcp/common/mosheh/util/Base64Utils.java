package com.geektcp.common.mosheh.util;

import com.geektcp.common.mosheh.system.Sys;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author geektcp on 2023/9/13 21:05.
 */
public class Base64Utils {

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "GET";
    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_OPTION = "OPTION";

    private static final int CONNECT_TIME_OUT = 1000 * 3;   // ms
    private static final int READ_TIME_OUT = 1000 * 10;     // ms
    private static final int MAX_TIME_OUT = 1000 * 4 * 3600;     // ms


    private static final boolean useCache = false;


    public static String getBase64FromUrl(String imgUrl) {
        return getBase64FromUrl(imgUrl, METHOD_GET, CONNECT_TIME_OUT, READ_TIME_OUT, useCache);
    }

    public static String getBase64FromUrl(String imgUrl,
                                          Integer timeout) {
        return getBase64FromUrl(imgUrl, METHOD_GET, timeout, timeout, useCache);
    }

    public static String getBase64FromUrl(String imgUrl,
                                          String method,
                                          Integer timeout) {
        return getBase64FromUrl(imgUrl, method, timeout, timeout, useCache);
    }

    public static String getBase64FromUrl(String imgUrl,
                                          String method,
                                          Integer timeout,
                                          Boolean useCache) {

        return getBase64FromUrl(imgUrl, method, timeout, timeout, useCache);
    }


    /**
     * @param imgUrl         any image url
     * @param method         any HTTP METHOD ,String type
     * @param connectTimeout connect tcp port time out
     * @param readTimeout    read data from socket tunnel time out
     * @param useCache       is or not use http protocol cache
     * @return result, String type, base64 data of the image or any other resource
     */
    public static String getBase64FromUrl(String imgUrl,
                                          String method,
                                          Integer connectTimeout,
                                          Integer readTimeout,
                                          Boolean useCache) {
        if (invalidCheck(imgUrl, method, connectTimeout, readTimeout, useCache)) {
            return "";
        }

        InputStream inStream = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpConnection = null;
        try {
            SslUtils.ignoreSsl();
            URL url = new URL(imgUrl);
            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setRequestMethod(method);
            httpConnection.setConnectTimeout(connectTimeout);
            httpConnection.setReadTimeout(readTimeout);
            httpConnection.setUseCaches(useCache);

            httpConnection.connect();
            inStream = httpConnection.getInputStream();

            outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            return encode(outStream.toByteArray());
        } catch (Exception e) {
            Sys.p(e.getMessage());
        } finally {
            try {
                if (Objects.nonNull(inStream)) {
                    inStream.close();
                }
                if (Objects.nonNull(outStream)) {
                    outStream.close();
                }
                if (Objects.nonNull(httpConnection)) {
                    httpConnection.disconnect();
                }
            } catch (Exception e) {
                Sys.p(e.getMessage());
            }
        }

        return null;
    }

    /////////////////////////////////////////////////////
    private static String encode(byte[] image) {
        BASE64Encoder decoder = new BASE64Encoder();
        return replaceEnter(decoder.encode(image));
    }

    private static String replaceEnter(String str) {
        String reg = "[\n-\r]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

    private static boolean invalidCheck(String imgUrl,
                                        String method,
                                        Integer connectTimeout,
                                        Integer readTimeout,
                                        Boolean useCache) {
        if (StringUtils.isEmpty(imgUrl)) {
            return true;
        }

        if (StringUtils.isEmpty(method)) {
            return true;
        }

        if (Objects.isNull(connectTimeout)) {
            return true;
        }
        if (connectTimeout < 0) {
            return true;
        }
        if (connectTimeout > MAX_TIME_OUT) {
            return true;
        }

        if (Objects.isNull(readTimeout)) {
            return true;
        }
        if (readTimeout < 0) {
            return true;
        }
        if (readTimeout > MAX_TIME_OUT) {
            return true;
        }

        return Objects.isNull(useCache);
    }

}
