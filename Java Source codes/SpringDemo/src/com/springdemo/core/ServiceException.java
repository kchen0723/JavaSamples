package com.springdemo.core;

/**
 * server µÄÒì³£
 * @author joe
 * @date  2011-10-24 ÏÂÎç03:33:11
 */
public class ServiceException extends Exception implements MessageAlertable,Logable{
	private static final long serialVersionUID = 1L;
   
	public ServiceException(String msg,Throwable e){
		super(msg, e);
	}
	
}
