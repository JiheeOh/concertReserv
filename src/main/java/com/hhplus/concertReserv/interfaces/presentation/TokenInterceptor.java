package com.hhplus.concertReserv.interfaces.presentation;

import com.hhplus.concertReserv.application.TokenFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
     TokenFacade tokenFacade;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("===== preHandle Start ==== ");

        if(request.getRequestURI().equals("/token/create")){
            return true;
        }
        tokenFacade.findActivateToken(Long.valueOf(request.getHeader("tokenId")));
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("===== postHandle Start ==== ");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("===== afterCompletion Start ==== ");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
