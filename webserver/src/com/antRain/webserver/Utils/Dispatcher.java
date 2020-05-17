package com.antRain.webserver.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.antRain.webserver.Impl.Servlet;
import com.antRain.webserver.config.WebConfig;

/**
 * 分发器
 */
public class Dispatcher implements Runnable {
    private Socket client;
    private Request request;
    private Response response;

    public Dispatcher(Socket client) {
        this.client = client;
        try {
            request = new Request(client);
            response = new Response(client);
            System.out.println(request);
        } catch (Exception e) {
            e.printStackTrace();
            relese();
        }
    }

    @Override
    public void run() {
        Servlet servlet = WebConfig.getServlet(request.getUrl());
        try {
            if (null != servlet) {
                servlet.service(request, response);
                response.push(200);
            } else {
                InputStream is = Thread.currentThread().getContextClassLoader().
                    getResourceAsStream("error.html");
                response.print(new String(is.readAllBytes()));
                // 返回错误页面
                response.push(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.push(500);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        relese();//短连接
    }

    private void relese() {
        try {
            client.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}