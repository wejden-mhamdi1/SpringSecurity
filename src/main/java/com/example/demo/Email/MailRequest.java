package com.example.demo.Email;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Data
@Getter
@Setter
@AllArgsConstructor
public class MailRequest {
	
	private String name;
	private String to;
	private String subject;
	private String body;
	private String buttonTitle;
	private String buttonHref;
	
	
	
	
	
}
