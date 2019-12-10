package site.xunyi.cuckoo.utils;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;

/**
 * @author xunyi
 */
public class ResultUtil {
    public static Result<JSONObject> success() {
        return Result.success;
    }
    
    public static <T> Result<JSONObject> success(Page<T> page){
        JSONObject data = new JSONObject();
        data.put("total", page.getTotalElements());
        data.put("pages", page.getTotalPages());
        data.put("list", page.getContent());
        
        return success(data);
    }
    
    public static Result<JSONObject> success(JSONObject data){
        Result<JSONObject> res = new Result<>();
        res.setCode(Result.SUCCESS_CODE);
        res.setMsg(Result.SUCCESS_MSG);
        res.setData(data);
        
        return res;
    }
    
    public static Result<JSONObject> error(){
        return Result.error;
    }
    
    public static Result<Object> error(String msg){
        return error(-1, msg);
    }
    
    public static Result<Object> error(int code, String msg){
        Result<Object> res = new Result<>();
        res.setCode(code);
        res.setMsg(msg);
        
        return res;
    }
}
