package com.imooc.diveinspringboot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link SpringApplication} 引导类
 *
 * @author 小马哥
 * @since 2018/5/16
 */
// @SpringBootApplication
// @SpringBootConfiguration
// @Configuration

@EnableAutoConfiguration
public class SpringApplicationBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationBootstrap.class,args);

        // Set<String> sources = new HashSet();
        // // 配置Class 名称
        // sources.add(ApplicationConfiguration.class.getName());
        // SpringApplication springApplication = new SpringApplication();
        // springApplication.setSources(sources);
        // springApplication.run(args);

        // new SpringApplicationBuilder(SpringApplicationBootstrap.class)
        //         // .bannerMode(Banner.Mode.CONSOLE)
        //         // .web(WebApplicationType.NONE)
        //         .run(args);
    }

    // @SpringBootApplication
    // @SpringBootConfiguration
    // @Configuration

    // @EnableAutoConfiguration
    public static class ApplicationConfiguration {

    }

}
