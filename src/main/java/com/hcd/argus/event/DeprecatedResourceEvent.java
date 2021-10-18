package com.hcd.argus.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

@Getter
public class DeprecatedResourceEvent extends ApplicationEvent {

    private final HttpServletResponse response;
    private final String since;
    private final String alternate;
    private final String policy;

    public DeprecatedResourceEvent(Object source, HttpServletResponse response,
                                   String since, String alternate, String policy) {
        super(source);
        this.response = response;
        this.since = since;
        this.alternate = alternate;
        this.policy = policy;
    }
}
