package spring.weblux.jwt.models;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ExceptionModel extends RuntimeException {
    private final String msg;
    private final int statusCode;

    @Override
    public String getMessage() {
        return this.msg;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
