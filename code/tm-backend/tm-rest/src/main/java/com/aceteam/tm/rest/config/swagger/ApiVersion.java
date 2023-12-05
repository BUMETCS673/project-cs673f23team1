package com.aceteam.tm.rest.config.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: some desc
 * @author: haoran
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiVersion {
    /**
     * Api Version Number(which is group in swagger)
     *
     * @return String[]
     */
    String[] group();
}
