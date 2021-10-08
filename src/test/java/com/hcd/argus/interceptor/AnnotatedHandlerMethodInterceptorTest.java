package com.hcd.argus.interceptor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.*;
import java.lang.reflect.Method;

class AnnotatedHandlerMethodInterceptorTest {

    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;

    private CustomMethodAnnotatedHandlerMethodInterceptor interceptor;

    @BeforeEach
    public void before() {
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        interceptor = new CustomMethodAnnotatedHandlerMethodInterceptor();
    }

    @Test
    void isAnnotatedHandlerMethod_notAHandlerMethod() {
        final Integer handler = 0;
        Assertions.assertTrue(interceptor.preHandle(mockRequest,
                mockResponse, handler));
    }

    @Test
    void isAnnotatedHandlerMethod_notAnnotated() throws NoSuchMethodException {
        final CustomBean bean = new CustomBean();
        final Method method = CustomBean.class.getMethod("notAnnotated");
        final HandlerMethod handler = new HandlerMethod(bean, method);

        Assertions.assertTrue(interceptor.preHandle(mockRequest,
                mockResponse, handler));
    }

    @Test
    void isAnnotatedHandlerMethod_annotated() throws NoSuchMethodException {
        final CustomBean bean = new CustomBean();
        final Method method = CustomBean.class.getMethod("annotated");
        final HandlerMethod handler = new HandlerMethod(bean, method);

        Assertions.assertFalse(interceptor.preHandle(mockRequest,
                mockResponse, handler));
    }

    private static class CustomBean {

        public void notAnnotated () {

        }

        @CustomMethod
        public void annotated() {

        }
    }

    private static class CustomMethodAnnotatedHandlerMethodInterceptor extends AnnotatedHandlerMethodInterceptor<CustomMethod> {

        @Override
        protected Class<CustomMethod> getAnnotationClass() {
            return CustomMethod.class;
        }

        @Override
        protected boolean doPreHandle(HttpServletRequest request,
                                      HttpServletResponse response, CustomMethod annotation) {
            return false;
        }
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    private @interface CustomMethod {

    }
}
