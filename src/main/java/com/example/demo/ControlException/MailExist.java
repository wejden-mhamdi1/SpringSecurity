package com.example.demo.ControlException;

public class MailExist extends RuntimeException{

	private static final long serialVersionUID=134877109171435607L;
	public MailExist(String message)
	{
		
		super(message);
		
	}}

