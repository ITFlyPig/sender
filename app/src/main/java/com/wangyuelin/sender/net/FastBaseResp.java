package com.wangyuelin.sender.net;


/**
 * fastjson解析返回结果基类
 */

public class FastBaseResp<T> {
    public int code;
    public T res; //这里支持简单的String Map和List还有自定义的Model
    public String message;

    /**
     * @return 请求是否成功
     */
    public boolean isSuccessful() {
        return code == 1000;
    }

}
