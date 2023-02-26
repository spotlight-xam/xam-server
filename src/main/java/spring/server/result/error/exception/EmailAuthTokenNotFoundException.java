package spring.server.result.error.exception;

import spring.server.result.error.BusinessException;
import spring.server.result.error.ErrorCode;

public class EmailAuthTokenNotFoundException extends BusinessException {
    public EmailAuthTokenNotFoundException() {
        super(ErrorCode.EmailAuthTokenNotFound);
    }
}
