package com.hcd.argus.interceptor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DeprecatedResource {

    String since() default "";

    String alternate() default "";

    String policy() default "";
}
