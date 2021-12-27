package com.atguigu.demo0001.httpUtils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class HttpClientUtil {
    //封闭构造方法
    private HttpClientUtil(){}

    //定制 请求配置信息 对象
    private RequestConfig requestConfig=RequestConfig.custom()
            .setConnectTimeout(15000)
            .setSocketTimeout(150000)
            .setConnectionRequestTimeout(15000)
            .build();

    //单例模式
    private static HttpClientUtil httpClientUtil=null;

    public static HttpClientUtil getInstance(){
        if(httpClientUtil==null){
            httpClientUtil=new HttpClientUtil();
        }
        return httpClientUtil;
    }

    //发送短信验证方法
    public int sendMsgUtf8(String Uid,String Key,String smsMob,String smsText){
        System.out.println("发送的手机号码："+smsMob);
        HashMap<String, String> map = new HashMap<>();
        /**
         *  特别注意 ：
         *          Uid Key smsMob smsText   -->这四个字符在 http 的Entity中是固定写法 不能修改的
         */

        map.put("Uid",Uid);
        map.put("Key",Key);
        map.put("smsMob",smsMob);
        map.put("smsText",smsText);

        String result=sendHttpPost("http://utf8.api.smschinese.cn/",map,"UTF-8");
        return Integer.parseInt(result);
    }

    //构建post请求对象
    private String sendHttpPost(String url, HashMap<String, String> map, String encode) {
        System.out.println("构建post请求对象");
        HttpPost httpPost = new HttpPost(url);
        ArrayList<NameValuePair> list = new ArrayList<>();
        for (String key : map.keySet()) {
            list.add(new BasicNameValuePair(key,map.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list,encode));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost,encode);
    }
    //发送post请求的重载方法1
    private String sendHttpPost(HttpPost httpPost, String encode) {
        CloseableHttpClient httpClient=null;
        CloseableHttpResponse response=null;
        HttpEntity entity=null;
        String responseContent=null;
        try{
            //获得httpClient对象
            httpClient = HttpClients.createDefault();
            //设置请求的配置对象
            httpPost.setConfig(requestConfig);
            //调用客户端对象 执行 请求
            response=httpClient.execute(httpPost);
            entity = response.getEntity();
            //将发送http请求之后返回的对象解析为字符串
            responseContent = EntityUtils.toString(entity, encode);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                if(httpClient!=null){
                    httpClient.close();
                }
                if(response!=null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }


    public String getError(int i) {
        switch(i){
            case -1:
                return "没有该用户账户";
            case -2:
                return "接口密钥不正确 [查看密钥]\n" +
                        "不是账户登陆密码";

            case -21:
                return "MD5接口密钥加密不正确";

            case -3:
                return "短信数量不足";

            case -11:
                return "该用户被禁用";

            case -14:
                return "短信内容出现非法字符";

            case -4:
                return "手机号格式不正确";

            case -41:
                return "手机号码为空";

            case -42:
                return "短信内容为空";

            case -51:
                return "短信签名格式不正确\n" +
                        "接口签名格式为：【签名内容】";

            case -52:
                return "短信签名太长\n" +
                        "建议签名10个字符以内";

            case -6:
                return "IP限制";

            default:
                return "错误超过预期";
        }
    }
}
