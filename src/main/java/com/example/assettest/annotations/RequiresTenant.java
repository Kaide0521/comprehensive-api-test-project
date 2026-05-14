package com.example.assettest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom tenant isolation annotation. The tenant id is expected in a request
 * header or authenticated principal claim.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresTenant {
    String value() default "X-Tenant-Id";
}
