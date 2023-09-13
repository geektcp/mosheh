package com.geektcp.common.mosheh.util;

import com.geektcp.common.mosheh.exception.BaseException;
import com.geektcp.common.mosheh.system.Sys;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * @author geektcp on 2023/9/14 0:12.
 */
public class HttpUtils {

    public static HttpURLConnection buildConnect(String imgUrl,
                                                 String method,
                                                 Integer connectTimeout,
                                                 Integer readTimeout,
                                                 Boolean useCache,
                                                 Boolean followRedirects) {
        try {
            SslUtils.ignoreSsl();
            URL url = new URL(imgUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod(method);
            httpConnection.setUseCaches(useCache);
            httpConnection.setInstanceFollowRedirects(followRedirects);
            httpConnection.setConnectTimeout(connectTimeout);
            httpConnection.setReadTimeout(readTimeout);
            return httpConnection;
        } catch (Exception e) {
            throw new BaseException(752, e.getMessage());
        }
    }

    public static boolean close(InputStream inStream, OutputStream outStream, HttpURLConnection httpConnection) {
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
            return true;
        } catch (Exception e) {
            Sys.p(e.getMessage());
            return false;
        }
    }
}
