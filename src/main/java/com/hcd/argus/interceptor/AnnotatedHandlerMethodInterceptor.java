package com.hcd.argus.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

abstract class AnnotatedHandlerMethodInterceptor<A extends Annotation> implements HandlerInterceptor {

    protected abstract Class<A> getAnnotationClass();

    protected abstract boolean doPreHandle(HttpServletRequest request,
                                           HttpServletResponse response, A annotation);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            final A annotation = handlerMethod.getMethod().getAnnotation(getAnnotationClass());
            if (annotation == null) {
                return true;
            }
            return doPreHandle(request, response, annotation);
        }
        return true;
    }
}
