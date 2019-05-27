package auto;

import enable.HelloWorldConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration} 引导类
 *
 * @author 小马哥
 * @since 2018/5/15
 */
@EnableAutoConfiguration(exclude = {HelloWorldAutoConfiguration.class})
public class EnableAutoConfigurationBootstrap {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(EnableAutoConfigurationBootstrap.class)
                .web(WebApplicationType.NONE)
                .run(args);

        // helloWorld Bean 是否存在
        String helloWorld =
                context.getBean("helloWorld", String.class);

        System.out.println("helloWorld Bean : " + helloWorld);

        // 关闭上下文
        context.close();



        // new SpringApplicationBuilder(EnableAutoConfigurationBootstrap.class)
        // //         .bannerMode(Banner.Mode.CONSOLE)
        // //         .web(WebApplicationType.NONE)
        //         .run(args);

    }
}
