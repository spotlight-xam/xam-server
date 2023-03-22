package spring.server.result.error.exception;

import spring.server.result.error.BusinessException;
import spring.server.result.error.ErrorCode;

public class EmailIsAlreadyExisted extends BusinessException {
    public EmailIsAlreadyExisted() {
        super(ErrorCode.EmailIsAlreadyExisted);
    }
}
