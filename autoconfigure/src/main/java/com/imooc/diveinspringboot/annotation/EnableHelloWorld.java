package com.imooc.diveinspringboot.annotation;

import com.imooc.diveinspringboot.configuration.HelloWorldConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *  激活 HelloWorld 模块
 *
 * @author 小马哥
 * @since 2018/5/14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
// servlet 3.0
// @Import(HelloWorldConfiguration.class)
// servlet 3.1
@Import(HelloWorldImportSelector.class)
public @interface EnableHelloWorld {
}
