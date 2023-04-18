package spring.server.result.error.exception;

import spring.server.result.error.BusinessException;
import spring.server.result.error.ErrorCode;

public class RoomNotExistException extends BusinessException {
    public RoomNotExistException() {
        super(ErrorCode.RoomNotExistException);
    }

}
