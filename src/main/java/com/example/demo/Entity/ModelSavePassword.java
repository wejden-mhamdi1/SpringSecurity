package com.example.demo.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//ce classe est pour enregistrer le MDP et mettre en historique
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
public class ModelSavePassword {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "passwordHistoryId_generator")
	Integer id;

	@ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
	@NonNull
	User user;
	@Column
	@NonNull
	String password;
	@Column
	@NonNull
	String createdAt;

}
