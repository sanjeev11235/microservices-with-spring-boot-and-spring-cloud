package com.rest.webservices.restfullwebservices.helloworld;

public class HelloWorldBean {

	private String message;

	public HelloWorldBean(String message) {
		this.message=  message;
	}

	public String getMessage() {//if we don't create a Getter, then the automatic conversion will not work.
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "HelloWorldBean [message=" + message + "]";
	}

	
}
