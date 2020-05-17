package com.antRain.webserver.service;

import com.antRain.webserver.Impl.Servlet;
import com.antRain.webserver.Utils.Request;
import com.antRain.webserver.Utils.Response;

public class LoginServlet implements Servlet{

	@Override
	public void service(Request request,Response response) {
		response.print("<html>");
		response.print("<head>");
		response.print("<title>");
		response.print("回应");
		response.print("</title>");
		response.print("<body>");
		response.print("hello world"+request.getParameterValue("name"));
		response.print("</body>");
		response.print("</head>");
		response.print("</html>");
	}
    
}