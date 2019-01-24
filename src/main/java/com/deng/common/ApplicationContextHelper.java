package com.deng.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("applicationContextHelper")
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static  <T> T popBean(Class<T> clazz){
        if(context == null)
            return null;
        return context.getBean(clazz);
    }
    public static  <T> T popBean(String name,Class<T> clazz){
        if(context == null)
            return null;
        return context.getBean(name,clazz);
    }

}
