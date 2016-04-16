package momo.cn.edu.fjnu.videoclient.exception;

/**
 * 自定义的APP异常
 * Created by GaoFei on 2016/3/10.
 */
public class AppException extends Exception{
    /**错误代码*/
    private int code;
    /**错误消息*/
    private String errorMsg;
    public AppException(int code, String errorMsg){
        this.code = code;
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "errorCode:" + code + "\n"
                +"errorMsg:" + errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getCode() {
        return code;
    }

}
