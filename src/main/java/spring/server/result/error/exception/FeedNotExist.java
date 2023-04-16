package spring.server.result.error.exception;

import spring.server.result.error.BusinessException;
import spring.server.result.error.ErrorCode;

public class FeedNotExist extends BusinessException {
    public FeedNotExist() {
        super(ErrorCode.FeedNotExist);
    }
}
