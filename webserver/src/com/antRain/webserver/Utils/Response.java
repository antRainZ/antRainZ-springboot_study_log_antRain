package com.antRain.webserver.Utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Response {
    private BufferedWriter bw;
    private StringBuilder content;//正文
    private StringBuilder headInfo;//响应头信息
    private int len;//正文的字节数
    private final String BLANK =" ";
    private final String CRLF = "\r\n";
   
    private Response() {
        content = new StringBuilder();
        headInfo = new StringBuilder();
        len = 0;
    }

    public Response(Socket client) {
        this();
        try {
            bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
            
    }

    public Response print(String info){
        content.append(info).append(CRLF);
        len += info.getBytes().length+2;
        return this;
    }

    public Response println(String info){
        content.append(info).append(CRLF);
        len += info.getBytes().length;
        return this;
    }

    private void createHeadInfo(int code){
        headInfo.append("HTTP/1.1").append(BLANK);
        headInfo.append(code).append(BLANK);
        switch(code){
            case 200:
                headInfo.append("OK").append(CRLF);
                break;
            case 404:
                headInfo.append("NOT FOUND").append(CRLF);
                break;
            case 505:
                headInfo.append("SERVER ERROR").append(CRLF);
                break;
        }
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Server:antRain Server;charset=UTF-8\r\n");
        headInfo.append("Content-type:text/html\r\n");
        headInfo.append("Content-length:").append(len).append(CRLF);
        headInfo.append(CRLF);
    }

    /**
     * @throws IOException
     * @apiNote 推送信息
     */
    public void push(int code) throws IOException {
        createHeadInfo(code);
        bw.append(headInfo);
        bw.append(content);
        bw.flush();
    }
}