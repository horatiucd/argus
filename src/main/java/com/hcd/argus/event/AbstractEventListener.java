package com.hcd.argus.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

abstract class AbstractEventListener<T extends ApplicationEvent> implements ApplicationListener<T> {

	protected final DateTimeFormatter formatter;
	
	protected AbstractEventListener() {
		formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss z");
	}
	
	protected LocalDateTime parse(String date) {
		LocalDateTime value;
		try {
			value = LocalDateTime.parse(date, formatter);
		} catch (DateTimeParseException e) {
			value = null;
		}
		return value;
	}
	
	protected String format(LocalDateTime date) {
		return formatter.format(date.atZone(ZoneId.of("GMT")));
	}

	static String contextPath(HttpServletRequest request) {
		return String.format("%s://%s:%d%s",
				request.getScheme(), request.getServerName(),
				request.getServerPort(), request.getContextPath());
	}

	static String formatLink(String uri, String rel) {
		return String.format("<%s>; rel=\"%s\"", uri, rel);
	}
}
