package spring.server.result;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultCode {

    private final int status;
    private final String code;
    private final String message;
}
