package com.hcd.argus.event;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;
import java.time.Month;

class AbstractEventListenerTest {

    private CustomEventListener listener;

    @BeforeEach
    public void before() {
        listener = new CustomEventListener();
    }

    @Test
    void parse_error() {
        final String input = "test date";
        final LocalDateTime date = listener.parse(input);
        Assertions.assertNull(date);
    }

    @Test
    void parse() {
        final String input = "13 Sep 2021 14:00:00 GMT";
        final LocalDateTime expected = LocalDateTime.of(2021, Month.SEPTEMBER, 13, 14, 0, 0, 0);
        final LocalDateTime date = listener.parse(input);
        Assertions.assertEquals(expected, date);
    }

    @Test
    void format() {
        final LocalDateTime input = LocalDateTime.of(2021, Month.SEPTEMBER, 13, 14, 0, 0, 0);
        final String expected = "13 Sep 2021 14:00:00 GMT";
        final String formattedDate = listener.format(input);
        Assertions.assertEquals(expected, formattedDate);
    }

    @Test
    void formatLink() {
        final String uri = "customUri";
        final String rel = "relation";
        final String link = AbstractEventListener.formatLink(uri, rel);
        Assertions.assertEquals("<" + uri + ">; rel=\"" + rel + "\"", link);
    }

    private static class CustomEventListener extends AbstractEventListener<CustomEvent> {

        @Override
        public void onApplicationEvent(CustomEvent customEvent) {

        }
    }

    private static class CustomEvent extends ApplicationEvent {

        public CustomEvent(Object source) {
            super(source);
        }
    }
}
