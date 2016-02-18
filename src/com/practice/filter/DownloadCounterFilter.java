package com.practice.filter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(filterName = "DownloadCounterFilter",urlPatterns = {"/*"})
public class DownloadCounterFilter implements Filter {

	ExecutorService es = Executors.newSingleThreadExecutor();
	Properties backupLog;
	File file;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		System.out.println("***** init*******");
		String appPath = filterConfig.getServletContext().getRealPath("/");
		file = new File(appPath,"downloadLog.txt");
		
		try
		{
			if(!file.exists())
			{
				file.createNewFile();
			}
			
			backupLog = new Properties();
			backupLog.load(new FileReader(file));
		}catch (Exception e) {
			
		}

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		final String uri = httpServletRequest.getRequestURI();
		es.execute(new Runnable() {
			
			
			@Override
			public void run() {
			String property = backupLog.getProperty(uri);
			if(property == null)
			{
				backupLog.setProperty(uri, "1");
			}
			else
			{
				int count = 0;
				try
				{
				count = Integer.parseInt(property);
				
				}catch (Exception e) {

				}
				count+=count+1;
				backupLog.setProperty(uri, Integer.toString(count));
			}
				
			}
		});
		chain.doFilter(request
				, response);
		

	}

	@Override
	public void destroy() {
		es.shutdown();

	}

}
