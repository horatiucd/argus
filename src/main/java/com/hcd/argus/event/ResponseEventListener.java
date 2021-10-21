package com.hcd.argus.event;

import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class ResponseEventListener extends AbstractEventListener {

    @EventListener
    public void onSunsetEvent(SunsetEvent event) {
        event.response()
                .addHeader("Sunset", format(event.sunsetDate()));
    }

    @EventListener
    public SunsetEvent onDeprecatedEvent(DeprecatedResourceEvent event) {
        event.response()
                .addHeader("Deprecation", event.since());
        event.response()
                .addHeader(HttpHeaders.LINK, formatLink(event.alternate(), "alternate") + "," +
                        formatLink(event.policy(), "deprecation"));

        return new SunsetEvent(event.response(), parse(event.sunset()));
    }
}
