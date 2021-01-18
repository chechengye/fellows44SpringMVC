package com.weichuang.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
public class MyHandlerInterceptorOne implements HandlerInterceptor {
    /**
     * false : 表示拦截 。 true ： 表示放行
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("前处理方法");
        //拦截器处理 ： token校验 （登录之后会生成一个token / 封装着此用户的权限的） 时效。（一周 / 2个小时）
        //token ： Basic token / Bearer token / token 。（放于授权头中）
        String token = httpServletResponse.getHeader("token");
        if(token != null){//校验token的过程
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("后处理方法");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("最终处理方法");
    }
}
