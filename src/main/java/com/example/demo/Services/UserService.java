package com.example.demo.Services;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.ControlException.Curent;
import com.example.demo.ControlException.MailExist;
import com.example.demo.ControlException.SavePassword;
import com.example.demo.Email.EmailService;
import com.example.demo.Email.MailRequest ;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entity.ModelSavePassword;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserService implements IUserService, UserDetailsService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	EmailService emailService;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired 
	PasswordEncoder passwordEncoder;
	@Autowired
	Curent curent;
	@Autowired
	SavePassword m;
	


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user == null) {
         
            throw new UsernameNotFoundException("User not found in the database");
        } else {
          
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getRole()));
            });
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
            // userdetails.User(user.getName(), user.getPassword(), user.getActive() ,  true, true, true, authorities);
    		
        }
    }
	/*    #####################################POUR QUE L'ADMIN PEUT AJOUTER UN ROLE #####################################*/
	@Override
	public Role addRole(Role role) {
        log.info("Saving new role {} to the database", role.getRole());
		return roleRepository.save(role);
	}
	/*    #####################################POUR QUE AFFECTER  UN ROLE A USER  #####################################*/

	@Override
	public User addRoleToUser(String NameUser, String NameRole) {
		
		User user = userRepository.findByUserName(NameUser);
		Role role = roleRepository.findByRole(NameRole);
		user.getRoles().add(role);
		return user;
	}
	/*    #####################################POUR SELLECTER TOUS LES USERS #####################################*/
	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		 log.info("Fetching all users");
		return userRepository.findAll();
	}
	
	/*    #####################################POUR QUE AJOUTER UN USER #####################################*/
	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		
		log.info("Saving new user {} to the database", user.getUserName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	/*    #####################################POUR QUE TROUVER UN USER SELON LEUR NOM #####################################*/
	@Override
	public User getUserByName(String NameUser) {
		// TODO Auto-generated method stub
		return userRepository.findByUserName(NameUser);
	}
	/*    #####################################POUR QUE AFFECTER  UN ROLE A USER  #####################################*/
	public User getUserByEmail(String email) {

		return userRepository.findByEmail(email);

	}
	/*    #####################################POUR INSCRIRE ET RECEVOIRE UN MAIL DE CONFIRMATION  #####################################*/
	@Override
	public User register(User user) {
		if(user.getPassword().equals(user.getConfirmepassword()))
		//PASSWORD
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setConfirmepassword(bCryptPasswordEncoder.encode(user.getConfirmepassword()));
		//DATE DE CREATTION DE COMPTE	
		user.setCreatedAt(Curent.getCurrentDate());
		user.setUserName(user.getUserName());
		String activationToken = UUID.randomUUID().toString();
		
		MailRequest mailRequest = new MailRequest(user.getUserName().toUpperCase() + " " + user.getUserName(),
				user.getEmail(), "Verification de votre compte", "Veuillez verifier votre compte",
				"Activer votre compte", "http://localhost:4200/activateAccount/" + activationToken);
		//mailRequest.setTLS(true); 
		emailService.sendEmail(mailRequest);
		// Fin
		User userEmail;
		userEmail = this.getUserByEmail(user.getEmail()); //user.getEmail()
		
			this.userRepository.save(user);
			//enregistrer le mdp
			 ModelSavePassword passwordHistory = new ModelSavePassword(user, user.getPassword(), Curent.getCurrentDate());
			 m.addPasswordHistory(passwordHistory);
				return user;
	}
	
	/*    ##################################### ACTIVER COMPTE #####################################*/
	public String activecompte (String username){
		User user=userRepository.findByUserName(username);
		user.setActive(true);
		return "activated";
	}
	/*    ##################################### CHANGE PASSWORD  #####################################*/
	public void RestPassword(String email,String newpass,String cofim) {

		User user =userRepository.findByEmail(email);
		user.setPassword(bCryptPasswordEncoder.encode(newpass));
		user.setConfirmepassword(bCryptPasswordEncoder.encode(cofim));
		userRepository.save(user);
	}
	/*    ##################################### FORGET PASSWORD  #####################################*/
	public User ForgetPassword(String email) {
		String token="";
		User user = userRepository.findByEmail(email);
		if (email != null) {
			 token= UUID.randomUUID().toString();
			//String link = "http://localhost:8081/pi-spring/api/resetPassword/{newpass} ";
			 MailRequest mailRequest = new MailRequest(user.getLastName().toUpperCase() + " " + user.getUserName(),
						user.getEmail(), "Reset your password", "Click to resest your password", "Reset your password",
						"http://localhost:4200/login/forgetPasswordPasswordForm/" + token);
				this.emailService.sendEmail(mailRequest);
			
	
	}
		return user;
	

	

}}
