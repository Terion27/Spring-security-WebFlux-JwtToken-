package spring.weblux.jwt.models.auth.AuthReqResp;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AuthReqResp<T> implements IAuthReqResp<T> {

    private T data;
    private String msg;

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public T getData() {
        return this.data;
    }
}
