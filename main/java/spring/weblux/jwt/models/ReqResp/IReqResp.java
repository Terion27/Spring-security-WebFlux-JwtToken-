package spring.weblux.jwt.models.ReqResp;

public interface IReqResp<T> {
    String getMsg();

    T getData();
}
