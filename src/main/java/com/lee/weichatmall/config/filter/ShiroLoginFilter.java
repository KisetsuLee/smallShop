package com.lee.weichatmall.config.filter;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * Description:
 * User: Lzj
 * Date: 2020-04-01
 * Time: 15:54
 */
public class ShiroLoginFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);
        return false;
    }
}

