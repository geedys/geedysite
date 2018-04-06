package com.geedys.geedysite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class GeedysiteApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        this.setRegisterErrorPageFilter(false); // 错误页面有容器来处理，而不是SpringBoot
      //  builder.properties(PropertiesUtils.getInstance().getProperties());
        return builder.sources(GeedysiteApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(GeedysiteApplication.class, args);
    }
}
