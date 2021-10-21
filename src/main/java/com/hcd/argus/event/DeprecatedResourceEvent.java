package com.hcd.argus.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;

@Getter
@RequiredArgsConstructor
public class DeprecatedResourceEvent  {

    private final HttpServletResponse response;
    private final String since;
    private final String alternate;
    private final String policy;
    private final String sunset;

}
