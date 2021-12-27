package com.atguigu.demo0001.utils;

import com.sun.org.apache.xml.internal.security.algorithms.implementations.SignatureBaseRSA;
import io.jsonwebtoken.*;

import java.util.Date;

/**
 * 用于生成token口令
 * 验证口令
 * 获取口令中携带的信息
 */
public class JwtUtils {
    private static long expire=60*1000; //过期时间 10s
    private static String key="sf124*%&%";

    //生成口令
    public static String createToken(String id,String name){
        String token = Jwts.builder()
                .setSubject("主题")
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .claim("userId", id)
                .claim("userName", name)
                .signWith(SignatureAlgorithm.HS256, key)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    //验证口令
    public static boolean verify(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            System.out.println("验证口令的方法返回的对象shi::"+claimsJws);
            return true;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }
    //从jwt中取loader
    public static void getClam(String token){
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        String userId = (String) body.get("userId");
        System.out.println("从jwt中解析出来的数据："+userId);
        String userName = (String) body.get("userName");
        System.out.println("从jwt中解析出来的数据:"+userName);
    }
}
