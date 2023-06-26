package spring.server.result.error.exception;

import spring.server.result.error.BusinessException;
import spring.server.result.error.ErrorCode;

public class UserAlReadyExisted extends BusinessException {
    public UserAlReadyExisted() {
        super(ErrorCode.UserAlReadyExisted);
    }
}
