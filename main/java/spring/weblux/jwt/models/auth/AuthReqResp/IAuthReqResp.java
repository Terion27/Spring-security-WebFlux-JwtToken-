package spring.weblux.jwt.models.auth.AuthReqResp;

public interface IAuthReqResp<T> {
    String getMsg();

    T getData();
}
