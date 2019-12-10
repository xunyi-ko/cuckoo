package site.xunyi.cuckoo.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author xunyi
 */
public class Result<T> {
    public static final int SUCCESS_CODE = 0;
    public static final String SUCCESS_MSG = "success";
    
    public static final int ERROR_CODE = -1;
    public static final String ERROR_MSG = "未知错误";
    /**
     * 结果标识符，0代表成功，其他代表失败
     */
    private int code;
    
    /**
     * 结果信息，success代表成功，其他代表失败
     */
    private String msg = "success";
    
    /**
     * 返回结果
     */
    private T data;

    static Result<JSONObject> success;
    static Result<JSONObject> error;
    
    static{
        success = new Result<>();
        success.setCode(SUCCESS_CODE);
        success.setMsg(SUCCESS_MSG);
        
        error = new Result<>();
        error.setCode(ERROR_CODE);
        error.setMsg(ERROR_MSG);
    }
    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
