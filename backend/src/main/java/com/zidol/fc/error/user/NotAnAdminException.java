package com.zidol.fc.error.user;

import java.io.Serial;

import com.zidol.fc.error.CustomException;
import com.zidol.fc.error.ErrorCode;
import com.zidol.fc.error.ErrorResponse;

public class NotAnAdminException extends CustomException {

	@Serial
	private static final long serialVersionUID = 2578916666257690334L;
	
	public NotAnAdminException(ErrorResponse.CustomFieldError customFieldError) {
		super(ErrorCode.NOT_AN_ADMIN, customFieldError);
	}

}
