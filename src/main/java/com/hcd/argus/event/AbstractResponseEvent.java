package com.hcd.argus.event;

import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

@Getter
abstract class AbstractResponseEvent {

    private final HttpServletResponse response;

    protected AbstractResponseEvent(HttpServletResponse response) {
        this.response = response;
    }
}
