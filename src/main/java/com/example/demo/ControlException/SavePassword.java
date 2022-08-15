package com.example.demo.ControlException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.ModelSavePassword;
import com.example.demo.Entity.User;
import com.example.demo.Repository.SavePasswordRepository;


@Service
public class SavePassword {
	@Autowired SavePasswordRepository s;
	@Autowired Curent curent;
	
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public Boolean checkPasswordHistory(User user, String password) {
		
		List<ModelSavePassword> passwordHistoryByUserList = s.findByUser(user);

		for (ModelSavePassword passwordHistoryByUser : passwordHistoryByUserList) {
			if (this.passwordEncoder.matches(password, passwordHistoryByUser.getPassword())) {

				return false;
			}

		}
		String encodedPassword = this.passwordEncoder.encode(password);

		ModelSavePassword passwordHistory = new ModelSavePassword(user, encodedPassword, Curent.getCurrentDate());

		this.addPasswordHistory(passwordHistory);

		return true;

	}

	public void addPasswordHistory(ModelSavePassword passwordHistory) {
		s.save(passwordHistory);
	}
	
	
	@Scheduled(cron = "0 0 0 * * *")
	public void deleteOldPasswordHistory(){
		
		List<ModelSavePassword> passwordHistoryByUserList = s.findAll();
		
		for (ModelSavePassword passwordHistory : passwordHistoryByUserList) {
			
			if(curent.compareDateByMonths(passwordHistory.getCreatedAt(), 2)){
				this.s.delete(passwordHistory);
			}
			}}
	
	
	
	
}



