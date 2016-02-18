package com.practice.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


@WebServlet(name="MyFirstServlet",urlPatterns={"/myServlet"})
public class MyServlet implements Servlet{

	private transient ServletConfig config;
	
	@Override
	public void destroy() {
		
		System.out.println("*** destroy***");
	}

	@Override
	public ServletConfig getServletConfig() {
		
		return config;
	}

	@Override
	public String getServletInfo() {
		
		return "My First Servlet";
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		this.config = arg0;
		
	}

	@Override
	public void service(ServletRequest req, ServletResponse resp)
			throws ServletException, IOException {
		String name = config.getServletName();
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
        pw.print("<html><head></head>"
                + "<body>Hello from " + name 
                + "</body></html>");

		
	}

	
}
