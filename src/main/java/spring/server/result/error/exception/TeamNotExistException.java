package spring.server.result.error.exception;

import spring.server.result.error.BusinessException;
import spring.server.result.error.ErrorCode;

public class TeamNotExistException extends BusinessException {
    public TeamNotExistException() {
        super(ErrorCode.TeamNotExistException);
    }
}
