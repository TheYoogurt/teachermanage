package com.yaocj.vo;

import java.io.Serializable;

public class Response <T>  implements Serializable {

    private static final long serialVersionUID = 1340563394201259857L;

    protected boolean success;

    protected String errorCode;

    protected String errorMsg;

    protected T result;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Response() {

    }

    public Response(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }



    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public static <T> Response<T> getSuccessResult(T v) {
        Response<T> result = new Response<T>();
        result.setSuccess(true);
        result.setResult(v);
        return result;
    }

    public static <T> Response<T> getFailureResult(String errorCode, String msg) {
        Response<T> result = new Response<T>();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMsg(msg);
        return result;
    }
}
