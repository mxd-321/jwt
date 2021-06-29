package com.hikvision.jwt.controller;

import com.hikvision.jwt.config.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author mengxiangding
 * @Copyright(C) 2021 杭州海康威视技术有限公司
 * @Description:
 * @create: 2021年06月25日 11:07:50
 */
@RestController
public class JwtController {
    @Autowired
    private JwtConfig jwtConfig;

    @GetMapping("/login")
    public Object login(@RequestParam("name") String name,
                        @RequestParam("password") String password) {
        System.out.println("name : " + name);
        System.out.println("password : " + password);
        HashMap<String, Object> map = new HashMap<>();

        // 这里验证name和password，如果失败直接返回即可。

        // 通过数据库拿到 userId
        String userId = "5";
        String token = jwtConfig.createToken(userId);
        if (!StringUtils.isEmpty(token)){
            map.put("token",token);
        }

        return map;
    }

    /**
     * 获取用户信息，需要token验证
     */
    @GetMapping("/info")
    public Object getUserInfo(HttpServletRequest request) {
        String token = jwtConfig.getUsernameFromToken(request.getHeader("token"));
        System.out.println(jwtConfig.getExpire());
        return token;
    }

}
