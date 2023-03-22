package spring.server.result.error.exception;

import spring.server.result.error.BusinessException;
import spring.server.result.error.ErrorCode;

public class FileConvertException extends BusinessException {
    public FileConvertException() {
        super(ErrorCode.FileConvertException);
    }
}
