package com.hcd.argus.event;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

public record SunsetEvent(HttpServletResponse response, LocalDateTime sunsetDate) {
}
