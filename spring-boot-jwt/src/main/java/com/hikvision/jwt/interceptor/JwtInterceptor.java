package com.hikvision.jwt.interceptor;

import com.hikvision.jwt.config.JwtConfig;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mengxiangding
 * @Copyright(C) 2021 杭州海康威视技术有限公司
 * @Description:
 * @create: 2021年06月25日 10:55:36
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtConfig jwtConfig;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        // 开始token验证
        String token = request.getHeader(jwtConfig.getHeader());
        if (StringUtils.isEmpty(token)){
            token = request.getParameter(jwtConfig.getHeader());
        }
        if(StringUtils.isEmpty(token)){
            throw new RuntimeException(jwtConfig.getHeader()+ "不能为空");
        }

        Claims claims = null;
        try {
            claims = jwtConfig.getTokenClaim(token);
            System.out.println("claims: " + claims);
            if (claims == null || jwtConfig.isTokenExpired(claims.getExpiration())){
                throw new RuntimeException(jwtConfig.getHeader() + "失效，请重新登陆");
            }
        }catch (Exception e){
            throw new RuntimeException(jwtConfig.getHeader() + "失效，请重新登陆");
        }

        request.setAttribute("identityId", claims.getSubject());
        return true;
    }
}
