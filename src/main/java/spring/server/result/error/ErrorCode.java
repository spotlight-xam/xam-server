package spring.server.result.error;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    UserDoesNotExistException(500, "U001", "내부 서버 오류입니다."),
    EmailAuthTokenNotFound(500, "002", "토큰이 확인되지 않았습니다."),
    UserNotFound(500, "U002", "유저가 확인되지 않았습니다.")
    ;

    private final int status;
    private final String code;
    private final String message;
}
