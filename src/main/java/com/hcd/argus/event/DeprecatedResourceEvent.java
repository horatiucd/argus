package com.hcd.argus.event;

import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

@Getter
public class DeprecatedResourceEvent extends AbstractResponseEvent {

    private final String since;
    private final String alternate;
    private final String policy;
    private final String sunset;

    public DeprecatedResourceEvent(HttpServletResponse response,
                                   String since, String alternate, String policy, String sunset) {
        super(response);
        this.since = since;
        this.alternate = alternate;
        this.policy = policy;
        this.sunset = sunset;
    }
}
