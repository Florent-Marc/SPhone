package com.dev.sphone.api.loaders;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AppDetails {
    AppType type() default AppType.DEFAULT;
    boolean isAlwaysHidden() default false;
}
