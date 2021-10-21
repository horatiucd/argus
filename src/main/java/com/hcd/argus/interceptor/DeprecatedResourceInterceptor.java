package com.hcd.argus.interceptor;

import com.hcd.argus.event.DeprecatedResourceEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class DeprecatedResourceInterceptor extends AnnotatedHandlerMethodInterceptor<DeprecatedResource> {

    private final ApplicationEventPublisher eventPublisher;

    public DeprecatedResourceInterceptor(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    protected Class<DeprecatedResource> getAnnotationClass() {
        return DeprecatedResource.class;
    }

    @Override
    protected boolean doPreHandle(HttpServletRequest request,
                                  HttpServletResponse response, DeprecatedResource annotation) {
        log.info("Deprecated REST Operation detected.");
        eventPublisher.publishEvent(new DeprecatedResourceEvent(response,
                annotation.since(), annotation.alternate(),
                annotation.policy(), annotation.sunset()));
        return true;
    }
}
