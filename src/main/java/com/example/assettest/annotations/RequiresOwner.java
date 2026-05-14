package com.example.assettest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom owner-check annotation. The referenced resource id must belong to the
 * current authenticated user.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresOwner {
    String resourceId();
    String ownerId() default "currentUser.id";
}
