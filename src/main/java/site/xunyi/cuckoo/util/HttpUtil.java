package site.xunyi.cuckoo.util;

import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;

/**
 * @author xunyi
 */
public class HttpUtil {
    /**
     * post请求，访问url
     * @param url
     * @param params
     * @return
     */
    public static Result<JSONObject> post(String url, JSONObject params) {
        return executeHttpResponse(HttpRequest.post(url).body(params.toString()).execute());
    }
    
    /**
     * get请求，访问url
     * @param url
     * @param params
     * @return
     */
    public static Result<JSONObject> get(String url, Map<String, Object> params){
        if(params != null) {
            StringBuilder sb = new StringBuilder(url);
            for(Entry<String, Object> e : params.entrySet()) {
                sb.append("&").append(e.getKey()).append("=").append(e.getValue());
            }
            url = sb.toString();
        }
        return executeHttpResponse(HttpRequest.get(url).execute());
    }
    
    /**
     * 处理http请求后的返回结果
     * @param execute
     * @return
     */
    private static Result<JSONObject> executeHttpResponse(HttpResponse execute){
        Result<JSONObject> res;
        // 成功返回数据
        if(execute.getStatus() == HttpStatus.HTTP_OK) {
            res = ResultUtil.success(JSONObject.parseObject(execute.body()));
        }else if(execute.getStatus() == HttpStatus.HTTP_NO_CONTENT) {
            res = ResultUtil.success();
        }else {
            res = ResultUtil.error();
        }
        return res;
    }
}
