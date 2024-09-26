package com.zidol.fc.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
	
	@OneToOne(mappedBy = "user")
	@JsonManagedReference(value = "user-admin")
	private Admin admin;
	
	@OneToMany(mappedBy = "user")
	@JsonManagedReference(value = "user-board")
	private List<Board> board;
	
	@OneToMany(mappedBy = "user")
	@JsonManagedReference(value = "user-reply")
	private List<Reply> reply;
	
	@OneToMany(mappedBy = "user")
	@JsonManagedReference(value = "user-cs")
	private List<Achievement> css;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userCode;
	
	@Column
	@NotNull
	private String userEmail;
	
	@Column
	@NotNull
	private String userPassword;
	
	@Column
	@NotNull
	private String userName;
	
	@Column
	@NotNull
	private String userNickname;

	@Builder
	public User(Admin admin, List<Board> board, List<Reply> reply, List<Achievement> css, long userCode,
			@NotNull String userEmail, @NotNull String userPassword, @NotNull String userName,
			@NotNull String userNickname) {
		super();
		this.admin = admin;
		this.board = board;
		this.reply = reply;
		this.css = css;
		this.userCode = userCode;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userName = userName;
		this.userNickname = userNickname;
	}

}