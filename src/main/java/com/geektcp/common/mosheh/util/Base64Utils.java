package com.geektcp.common.mosheh.util;

import com.geektcp.common.mosheh.exception.BaseException;
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

    public static final String HEAD_LOCATION = "LOCATION";

    private static final int CONNECT_TIME_OUT = 1000 * 3;   // ms
    private static final int READ_TIME_OUT = 1000 * 10;     // ms
    private static final int MAX_TIME_OUT = 1000 * 4 * 3600;     // ms


    private static final boolean USE_CACHE = false;
    private static final boolean FOLLOW_REDIRECTS = false;


    public static String getBase64FromUrl(String imgUrl) {
        return getBase64FromUrl(imgUrl, METHOD_GET,
                CONNECT_TIME_OUT, READ_TIME_OUT, USE_CACHE, FOLLOW_REDIRECTS);
    }

    public static String getBase64FromUrl(String imgUrl,
                                          Integer timeout) {
        return getBase64FromUrl(imgUrl, METHOD_GET, timeout, timeout, USE_CACHE, FOLLOW_REDIRECTS);
    }

    public static String getBase64FromUrl(String imgUrl,
                                          String method,
                                          Integer timeout) {
        return getBase64FromUrl(imgUrl, method, timeout, timeout, USE_CACHE, FOLLOW_REDIRECTS);
    }

    public static String getBase64FromUrl(String imgUrl,
                                          String method,
                                          Integer timeout,
                                          Boolean useCache,
                                          Boolean followRedirects) {
        return getBase64FromUrl(imgUrl, method, timeout, timeout, useCache, followRedirects);
    }


    /**
     * setUseCaches and setUseCaches must before getResponseCode
     * because getResponseCode update HttpURLConnection status to connected status
     *
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
                                          Boolean useCache,
                                          Boolean followRedirects
    ) {
        if (invalidCheck(imgUrl, method,
                connectTimeout, readTimeout, useCache, followRedirects)) {
            return "";
        }

        InputStream inStream = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpConnection = null;

        try {
            httpConnection = HttpUtils.buildConnect(imgUrl, method,
                    connectTimeout, readTimeout, useCache, followRedirects);
            if (Objects.isNull(httpConnection)) {
                return "";
            }
            int code = httpConnection.getResponseCode();
            String msg = httpConnection.getResponseMessage();
            if (code >= 300 && code < 400) {
                String redirectURL = httpConnection.getHeaderField(HEAD_LOCATION);
                if (StringUtils.isEmpty(redirectURL)) {
                    Sys.p(code + "|" + msg);
                    throw new BaseException(700, msg);
                }
                httpConnection.disconnect();
                httpConnection = HttpUtils.buildConnect(redirectURL, method,
                        connectTimeout, readTimeout, useCache, followRedirects);
                code = httpConnection.getResponseCode();
                msg = httpConnection.getResponseMessage();
            }

            if (code != 200) {
                Sys.p(code + "|" + msg);
                throw new BaseException(701, msg);
            }

            httpConnection.connect();                      // maybe connect timeout
            inStream = httpConnection.getInputStream();    // maybe read data timeout

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
            HttpUtils.close(inStream, outStream, httpConnection);
        }

        return "";
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
                                        Boolean useCache,
                                        Boolean followRedirects) {
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
        if (Objects.isNull(useCache)) {
            return true;
        }
        return Objects.isNull(followRedirects);
    }


}
