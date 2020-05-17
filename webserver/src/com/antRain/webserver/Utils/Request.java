package com.antRain.webserver.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装请求信息
 * 
 * @author antRain QQ:2464210826
 */
public class Request {
    private String requestInfo;
    private String method;
    private String url;
    private String queryStr;
    private Map<String, List<String>> parameterMap;

    public Request(InputStream is) {
        parameterMap = new HashMap<>();
        byte[] datas = new byte[1024 * 1024];
        try {
            int len = is.read(datas);
            this.requestInfo = new String(datas, 0, len);
            // System.out.println(requestInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        parseRequestInfo();

    }

    public Request(Socket client) throws IOException {
        this(client.getInputStream());
    }

    /**
     * 解析字符串
     */
    private void parseRequestInfo() {
        // 获取请求方式
        this.method = this.requestInfo.substring(0, this.requestInfo.indexOf("/")).trim();
        // 获取请求的url
        // System.out.println(requestInfo);
        int startInx = this.requestInfo.indexOf("/");
        int endInx = this.requestInfo.indexOf("HTTP/") - 1;
        // System.out.println(""+startInx+" "+endInx);
        this.url = this.requestInfo.substring(startInx, endInx);

        int queryIdx = this.url.indexOf("?");
        if (queryIdx >= 0) {
            String[] str = this.url.split("\\?");
            this.url = str[0];
            this.queryStr = str[1];
        }
        if (method.equals("POST")) {
            String qStr = this.requestInfo.substring(this.requestInfo.lastIndexOf("\r\n")).trim();
            this.queryStr = queryStr == null ? qStr : queryStr + "&" + qStr;
        }
        if (queryStr == null) {
            queryStr = "";
        }
        convertMap();
    }

    /**
     * 讲请求处理参数转化为Map
     */
    private void convertMap() {
        String[] keyValues = this.queryStr.split("&");
        for (String s : keyValues) {
            String[] kv = s.split("=");
            kv = Arrays.copyOf(kv, 2);
            String key = kv[0];
            String value = kv[1]==null?null:decode(kv[1], "UTF-8");
            if (!parameterMap.containsKey(key)) {
                parameterMap.put(key, new ArrayList<>());
            }
            parameterMap.get(key).add(value);
        }
    }

    /**
     * 处理中文
     * @return
     */
    private String decode(String value, String encode) {
        try {
            return URLDecoder.decode(value, encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getParameterValues(String key) {
        List<String> list = this.parameterMap.get(key);
        if (null == list || list.size() < 1) {
            return null;
        }
        return list.toArray(new String[0]);
    }

    public String getParameterValue(String key) {
        String[] values = getParameterValues(key);
        return values == null ? null : values[0];
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getQueryStr() {
        return queryStr;
    }

    @Override
    public String toString() {
        return "Request [method=" + method + ", parameterMap=" + parameterMap + ", queryStr=" + queryStr + ", url="
                + url + "]";
    }

}