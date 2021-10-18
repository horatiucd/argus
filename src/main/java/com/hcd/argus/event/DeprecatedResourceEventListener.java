package com.hcd.argus.event;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class DeprecatedResourceEventListener extends AbstractEventListener<DeprecatedResourceEvent> {

    private final ApplicationEventPublisher eventPublisher;

    DeprecatedResourceEventListener(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onApplicationEvent(DeprecatedResourceEvent event) {

    }
}
