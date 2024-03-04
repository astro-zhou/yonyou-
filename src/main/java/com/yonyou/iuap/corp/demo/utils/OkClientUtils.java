package com.yonyou.iuap.corp.demo.utils;



import com.yonyou.iuap.corp.demo.model.HttpGetWithEntity;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class OkClientUtils {

    /**
     * get请求
     * @param url
     * @return
     */
    public static String httpGet(String url) {
        String result = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String sendJsonByGetReq(String url, String param, String encoding) throws Exception {
        String body = "";
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGetWithEntity httpGetWithEntity = new HttpGetWithEntity(url);
        HttpEntity httpEntity = new StringEntity(param, ContentType.APPLICATION_JSON);
        httpGetWithEntity.setEntity(httpEntity);
        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpGetWithEntity);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        //释放链接
        response.close();
        return body;
    }

    /**
     * post请求
     * @param url
     * @param data  提交的参数为key=value&key1=value1的形式
     */
    public static String httpPost(String url, String data) {
        String result = null;
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS).build();

       RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), data);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = httpClient.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String httpPostCookie(String url, String data,String cookie) {
        String result = null;
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(60,TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS).build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), data);
        Request request = new Request.Builder().addHeader("Cookie", cookie).url(url).post(requestBody).build();
        try {
            Response response = httpClient.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
