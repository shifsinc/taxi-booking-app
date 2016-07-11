package com.shriram.microservices.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.shriram.microservices.service.AuthenticationService;
import com.shriram.microservices.util.ResponseUtil;

/**
 * Request intercepter class to store remote IP address in thread local for every request and clears it once request is served.
 *
 * <p>
 * This class retrieves remote IP address from request header for X_FORWARDED_FOR. It stores IP address in thread local for each request in
 * pre-handler, such that it is available to thread while request processing. It clears this information form thread local on post handler
 * </p>
 *
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    @Qualifier("authenticationService")
    private AuthenticationService authenticationService;

    private Logger logger = Logger.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (StringUtils.isEmpty(request.getHeader("Authorization"))) {
            response = ResponseUtil.unauthorized(response, "missing token");
            return false;
        }
        logger.info("Authorization Header :" + request.getHeader("Authorization"));
        String username = authenticationService.getUsername(request.getHeader("Authorization"));
        if (username == null) {
            response = ResponseUtil.unauthorized(response, null);
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws java.lang.Exception

    {

    }

}
