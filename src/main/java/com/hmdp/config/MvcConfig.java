package com.hmdp.config;

import com.hmdp.utils.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //定义排除swagger访问的路径配置
        String[] swaggerExcludes=new String[]{"/swagger-ui.html","/swagger-resources/**","/webjars/**"};

        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        "/shop/**",
                        "voucher/**",
                        "/shop-type/**",
                        "/upload/**",
                        "/blog/hot",
                        "/user/code",
                        "/user/login",
                        "/login.html"
                )
                //下面是固定格式,如果不配置swagger页面将会访问不了
                .excludePathPatterns(swaggerExcludes);
    }

}















