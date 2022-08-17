package com.example.demo.Email;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import com.example.demo.Email.MailRequest;
import com.example.demo.Entity.User;

import lombok.Data;
@Service
public class EmailService {
	@Autowired
	private JavaMailSender sender;

	@Autowired
	private Configuration config;
	
	
	public MailResponse sendEmail(MailRequest mailRequest) {
		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		User user = new User();
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			
			// add attachment
			String activationToken = UUID.randomUUID().toString();
			user.setActivationToken(activationToken);
			Template t = config.getTemplate("email-template.ftl");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("bonjourOrBonsoir", GetMessageBonsoirOrBonjour());
			model.put("name", "welcome in ATB");
			model.put("body", mailRequest.getBody());
			
			
			//model2.addAttribute("tokenactive",user.setActivationToken(activationToken) );
			
			model.put("buttonTitle", mailRequest.getButtonTitle());
			model.put("buttonHref", mailRequest.getButtonHref());

			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
			helper.setTo(mailRequest.getTo());
			helper.setText(html, true);
			helper.setSubject(mailRequest.getSubject());
			
			sender.send(message);

			response.setMessage("mail send to : "+mailRequest.getTo() );
			response.setStatus(Boolean.TRUE);

		} catch (MessagingException  e) {
			response.setMessage("Mail Sending failure : " + e.getMessage());
			response.setStatus(Boolean.FALSE);
		}
		catch (IOException  e) {
			response.setMessage("Mail Sending failure : " + e.getMessage());
			response.setStatus(Boolean.FALSE);
		}
		catch ( TemplateException e) {
			response.setMessage("Mail Sending failure : " + e.getMessage());
			response.setStatus(Boolean.FALSE);
		}

		return response;
	}




	           
    

	public static boolean between(int variable, int minValueInclusive, int maxValueInclusive) {
		return variable >= minValueInclusive && variable <= maxValueInclusive;
	}
/*   ######################################POUR ENVOYER BONJOUR OU BONSOIR SELON LE TEMPS ############*/
	public static String GetMessageBonsoirOrBonjour() {

		int currentHour = java.time.LocalTime.now().getHour();

		String message = "Salut";

		if (between(currentHour, 0, 5) || between(currentHour, 18, 23))

		{
			message = "Bonsoir";

		} else if (between(currentHour, 6, 17)) {
			message = "Bonjour";

		}
		return message;

	}
	
	


}
