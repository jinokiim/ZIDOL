package com.zidol.fc.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Admin {
	
	@OneToOne
	@JoinColumn(name = "user_code")
	@JsonBackReference(value = "user-admin")
	private User user;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long adminCode;

	@Builder
	public Admin(User user, long adminCode) {
		super();
		this.user = user;
		this.adminCode = adminCode;
	}
	
}