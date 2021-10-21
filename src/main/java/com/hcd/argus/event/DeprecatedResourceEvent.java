package com.hcd.argus.event;

import javax.servlet.http.HttpServletResponse;

public record DeprecatedResourceEvent(HttpServletResponse response,
                                      String since, String alternate,
                                      String policy, String sunset) {
}
