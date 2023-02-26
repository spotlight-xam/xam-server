package spring.server.result.error.exception;

import spring.server.result.error.BusinessException;
import spring.server.result.error.ErrorCode;

public class UserDoesNotExistException extends BusinessException {
    public UserDoesNotExistException() {
        super(ErrorCode.UserDoesNotExistException);
    }

}
