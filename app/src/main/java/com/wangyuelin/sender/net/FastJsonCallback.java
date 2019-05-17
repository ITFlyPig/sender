package com.wangyuelin.sender.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * 这类的作用：覆盖父类的方法convertResponse，将Okhttp返回的Response解析为需要的数据格式
 * <p>
 * <p>
 * AbsCallback：空实现了接口Callback部分方法，避免继承自AbsCallback的类需要实现全部方法，现在只需要覆盖想实现的
 *
 * @param <T>
 */

public abstract class FastJsonCallback<T> extends ChCallBack<T> {

    /**
     * 解释需要，假设网络请求传入的泛型：FastJsonCallback<FastBaseResp<User>>
     *
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    public T convertResponse(Response response) throws IOException {

        Type genType = getClass().getGenericSuperclass(); //返回直接继承的父类
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();//获取泛型中的实际类型
        //得到第二层泛型的所有类型：FastBaseResp<User>
        Type type = params[0];
        if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
        //得到第二层数据的真实类型：FastBaseResp
        Type rawType = ((ParameterizedType) type).getRawType();
        //得到第二层数据的泛型的真实类型：User
        Type resType = ((ParameterizedType) type).getActualTypeArguments()[0];

        //为了兼容这种情况：FastJsonCallback<FastBaseResp<Map<String, String>>>
        //这种情况比       FastJsonCallback<FastBaseResp<User>> 多了一层泛型
//        if (resType instanceof ParameterizedType) {//表明获取到的还是参数化类型
//            resType = ((ParameterizedType) resType).getRawType();//获取声明泛型的类或者接口
//        }

        if (rawType == FastBaseResp.class) {
            ResponseBody body = response.body();
            if (body == null) {
                response.close();
                throw new IllegalStateException("response body is empty");
            }
            final String bodyStr = body.string();
            if (TextUtils.isEmpty(bodyStr)) {
                response.close();
                throw new IllegalStateException("response body is empty");
            }

            response.close();
            Gson gson = new Gson();


            FastBaseResp resp = new FastBaseResp();

            JsonObject jsonObject = new JsonParser().parse(bodyStr).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            //在这处理code，不同的code做不同的处理
            resp.code = code;
            resp.message = jsonObject.get("msg").getAsString();

            JsonElement dataElement = jsonObject.get("data");
            if (dataElement != null) {
                String data = dataElement.toString();
                if (!TextUtils.isEmpty(data)) {
                    resp.res = gson.fromJson(data, resType);
                }
            }

            return (T) resp;
        }

        return null;

    }


}
