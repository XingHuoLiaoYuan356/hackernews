package com.cskaoyan.hackernews.configuration;

  import com.cskaoyan.hackernews.interceptor.LoginRequiredInterceptor;
  import com.cskaoyan.hackernews.interceptor.PassportInterceptor;
  import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

  import java.util.Arrays;

@Component
public class ToutiaoWebConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequiredInterceptor).
               addPathPatterns("/msg/*")
               .addPathPatterns("/like")
               .addPathPatterns("/dislike")
               .addPathPatterns("/user/**")
              ;
        super.addInterceptors(registry);
    }
}


/* .excludePathPatterns(
                       Arrays.asList(
                               "/fonts/**",
                               "/images/**",
                               "/scripts/**",
                               "/styles/**")
                       )*/