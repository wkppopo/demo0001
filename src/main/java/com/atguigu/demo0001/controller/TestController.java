package com.atguigu.demo0001.controller;

import com.alibaba.fastjson.JSON;
import com.atguigu.demo0001.entity.Login;
import com.atguigu.demo0001.service.SendMsg;
import com.atguigu.demo0001.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;


@RestController  //返回json对象
//@Controller
@Api(tags = "jwt登陆认证")
@CrossOrigin //跨域
public class TestController {
    @Autowired
    private SendMsg sendMsg; //引入业务类对象 短信验证

    private static String token;

    /***
     * 登陆注册短信验证
     */
    @PostMapping("/sms")
    @ApiOperation(value = "短信验证")
    public String sms(@RequestBody Login login){
        System.out.println(login.getToken());
        System.out.println(login.getUserName());
        System.out.println(login.getUserId());
        System.out.println("************");

        boolean b = sendMsg.sendSms();
        if(b){
            return "验证码发送成功";
        }
        return "验证码发送失败";
    }

    /**
     * 生成口令
     */
    @ApiOperation(value="登陆认证")
    @PostMapping("/test")
    public HashMap<String, Object> test(@RequestBody Login login) {
        HashMap<String, Object> map = new HashMap<>();

        if(login!=null){
            //如果参数不为空，验证口令
            if(login.getToken()==""||login.getToken()==null){
                String token = JwtUtils.createToken(login.getUserId(), login.getUserName());
                map.put("msg","参数信息为空");
                map.put("token",token);
            }else{
                System.out.println("**********************"+login.getToken());
                if(JwtUtils.verify(login.getToken())){
                    System.out.println("验证通过");
                    map.put("msg","验证通过");
                }else{
                    System.out.println("验证失败");
                    map.put("msg","验证失败");
                }
            }
        }else{
            String token = JwtUtils.createToken(login.getUserId(), login.getUserName());
            map.put("msg","参数信息为空");
            map.put("token",token);
        }

        return map;
    }

    /**
     * 验证request 接收url中的数据
     * @RequestParam("name")  //从rul中获取参数数据 与请求方法无关
     */
    @ApiOperation(value="从url中获取数据")
    @GetMapping("/param")
    public String param(Login login){
        System.out.println("控制类输出token ："+login.getToken());
        String token = login.getToken();
        JwtUtils.getClam(token); //通过验证之后获取jwt中的信息
        return "执行成功...验证通过";
    }

    /**
     * 直接配置参数名
     */
    @ApiOperation(value="从url中获取数据")
    @GetMapping("/param1")
    public String param1(String name,
                        String age){
        return name+age;
    }

    /**
     * 获取body中的json字符串参数
     * @RequestBody()
     */
    @ApiOperation(value="获取body中的json数据")
    @PostMapping("/body")
    public String body(@RequestBody Login login){
        String s = JSON.toJSONString(login);//将实体对象转成json字符串

        String token = login.getToken();

        System.out.println(token);
        return s;
    }

    /**
     *  直接使用对象接收数据
     *
     */
    @ApiOperation(value="获取body中的json数据")
    @PostMapping("/body1")
    public String body1(Login login){
        String s = JSON.toJSONString(login);//将实体对象转成json字符串

        String token = login.getToken();

        System.out.println(token);
        return s;
    }

    /**
     * httpServletRequest
     *      客户端通过http请求中的所有数据
     */
    @ApiOperation(value="测试request请求")
    @GetMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-type","test/html;charset=utf-8");
            PrintWriter out = response.getWriter();

            System.out.println(request.getRequestURL()); //返回客户端发出请求的完整url地址
            System.out.println(request.getRequestURI());//返回请求行中的uri资源标识部分
            System.out.println(request.getQueryString());//返回请求行的参数部分
            System.out.println(request.getPathInfo()); //返回请求路径之外的信息
            System.out.println("*****************************");
            System.out.println(request.getRemoteAddr()); //返回请求客户端的IP地址
            System.out.println(request.getRemoteHost()); //返回请求客户端的主机名
            System.out.println(request.getRemotePort()); //返回请求客户端的port
            System.out.println(request.getRemoteUser()); //
            System.out.println("*******************");
            System.out.println(request.getLocalAddr()); //返回web端的ip地址
            System.out.println(request.getLocalName()); //返回web端的主机名
            System.out.println("**********************");
            System.out.println(request.getMethod()); // 获取请求的方法
            System.out.println(request.getHeaderNames());


            out.write(String.valueOf(request.getRequestURL()));
            out.write("<br/>");
            out.write(request.getRequestURI());
            out.write("<br/>");
            out.write(request.getRemoteAddr());
            out.write("<br/>");
            out.write(request.getRemoteHost());
            out.write("<br/>");
            out.write(request.getRemotePort());
            out.write("<br/>");
            out.write(request.getLocalAddr());
            out.write("<br/>");
            out.write(request.getLocalName());
            out.write("<br/>");
            out.write(request.getMethod());
            out.write("<br/>");
            out.write("<br/>");

        } catch (IOException e) {
            e.printStackTrace();
        }

        //return "ok!!";
    }

}
