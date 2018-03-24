package com.zhengjin.reflection.demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface FindBy {

	String name();

	int age();

	int level() default 0;
}
