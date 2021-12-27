package com.atguigu.demo0001.service.impl;

import com.atguigu.demo0001.httpUtils.HttpClientUtil;
import com.atguigu.demo0001.service.SendMsg;
import org.springframework.stereotype.Service;

@Service
public class sendMsgImpl implements SendMsg {

    @Override
    public boolean sendSms() {
        /**
         *  特别注意 ：
         *          Uid Key smsMob smsText   -->这四个字符在 http 的Entity中是固定写法 不能修改的
         */

        //用户名
        String Uid = "wkppopo";

        //接口安全秘钥
        String Key = "d41d8cd98f00b204e980";

        //手机号码，多个号码如13800000000,13800000001,13800000002
        String smsMob = "17375501961";

        //短信内容
        String smsText = "验证码：11111";

        HttpClientUtil http = HttpClientUtil.getInstance();
        System.out.println("............."+http);

        int i = http.sendMsgUtf8(Uid, Key, smsMob, smsText);

        if(i>0){
            System.out.println("成功发送短信条数:"+i);
            return true;
        }else{
            System.out.println("发送失败:"+i+":"+http.getError(i));
        }
        return false;
    }
}
