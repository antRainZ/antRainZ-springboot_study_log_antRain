package com.antRain.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.antRain.webserver.Utils.Dispatcher;

public class Server {
    private ServerSocket serverSocket;
    private boolean isRunning;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(8888);
            isRunning = true;
            receive();
        } catch (Exception e) {
            System.out.println("启动服务失败");
        }
    }

    public void stop() {
        isRunning = false;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            System.out.println("服务器关闭失败");
        }
    }

    public void receive() {
        while (isRunning) {
            try {
                Socket client = serverSocket.accept();
                System.out.println(client);
                // 多线程处理
                // 获取请求协议 这里需要的HTTP 使用HTTPS编码错误
                new Thread(new Dispatcher(client)).start();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("客户端错误");
                isRunning = false;
            }
        }
    }
}