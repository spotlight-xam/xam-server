package spring.server.result.error;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    UserDoesNotExistException(500, "U001", "내부 서버 오류입니다."),
    EmailAuthTokenNotFound(500, "E001", "토큰이 확인되지 않았습니다."),
    UserNotFound(500, "U002", "유저가 확인되지 않았습니다."),
    FileConvertException(500, "F001", "파일 변환에 실패하였습니다."),
    EmailIsAlreadyExisted(500, "E002", "이메일이 이미 있습니다"),
    FeedNotExist(500, "F002", "피드가 존재하지 않습니다"),
    TeamNotExistException(500, "T001", "팀이 존재하지 않습니다"),
    ;

    private final int status;
    private final String code;
    private final String message;
}
