package spring.server.result;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    DeleteFeedSuccess(200, "F001", "게시물 삭제 성공");

    private final int status;
    private final String code;
    private final String message;
}
