package com.zidol.fc.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
	LOGIN_FAILED(404, "LOGIN_FAILED"),
	DUPLICATED_ID(505, "DUPLICATED_ID"),
	DUPLICATED_ACHIEVEMENT(505, "DUPLICATED_ACHIEVEMENT"),
	INVALID_PARAM(400, "INVALID_PARAM"),
	UPDATE_BY_UNAUTH_USER(500, "UPDATE_BY_UNAUTH_USER"),
	DELETE_BY_UNAUTH_USER(500, "DELETE_BY_UNAUTH_USER"),
	NOT_AN_ADMIN(500,"NOT_AN_ADMIN");
	
	private int status;
	private String code;
	
	ErrorCode(int status, String code) {
		this.status = status;
		this.code = code;
	}
}
