package com.deng.common;

import com.deng.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv;
        String defaultMsg = "System error";
        String url = httpServletRequest.getRequestURL().toString();


        if (url.endsWith(".json")) {
            if (e instanceof PermissionException) {
                JsonData jsonData = JsonData.fail(e.getMessage());
                mv = new ModelAndView("jsonView", jsonData.toMap());
            } else {
                log.error("unknwon json exception,url" + url, e);
                JsonData jsonData = JsonData.fail(defaultMsg);
                mv = new ModelAndView("jsonView", jsonData.toMap());
            }
        } else if (url.endsWith(".page")) {
            JsonData jsonData = JsonData.fail(defaultMsg);
            log.error("unknown page exception,url" + url, e);
            //找到exception.jsp返回
            mv = new ModelAndView("exception", jsonData.toMap());
        } else {
            JsonData jsonData = JsonData.fail(defaultMsg);
            log.error("unknown exception,url" + url, e);
            mv = new ModelAndView("jsonView", jsonData.toMap());
        }

        return mv;
    }
}
