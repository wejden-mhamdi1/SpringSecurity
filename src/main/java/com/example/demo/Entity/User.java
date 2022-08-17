package com.example.demo.Entity;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
	 @Id @GeneratedValue(strategy = AUTO)
private Long id;
private String userName;
private String lastName;
private String email;
private String password;
private String confirmepassword;
private Boolean isblocked;

String createdAt;
@Column(columnDefinition = " varchar(255) ",nullable=true)
String activationToken;
private Boolean active=false;

@ManyToMany(fetch = EAGER)
private Collection<Role> roles = new ArrayList<>();
}
