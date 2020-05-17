package com.antRain.webserver.Impl;

import com.antRain.webserver.Utils.Request;
import com.antRain.webserver.Utils.Response;

public interface Servlet {
    void service(Request request,Response response);
}