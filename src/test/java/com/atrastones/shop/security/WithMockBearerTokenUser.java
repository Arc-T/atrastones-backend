package com.atrastones.shop.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = WithMockOidcUserSecurityContextFactory.class)
public @interface WithMockBearerTokenUser {

    String username() default "09361629708";

    String[] authorities() default {};

    String firstName() default "Taha";

    String lastName() default "Hajivand";

    String birthdate() default "1990-10-12";

    String fatherName() default "fatherName";

    String gender() default "MALE";

}