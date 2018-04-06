package com.geedys.geedysite.config;

import com.geedys.geedysite.mybatis.interceptor.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;

/**
 * MyBatis相关配置类
 *
 * @author geedys
 */
@Configuration
public class MybatisConfig {
    /**
     * 根据属性配置是否启动性能监控拦截器
     * <p>
     * ConditionalOnProperty 当配置文件中存在属性值performance，判断havingValue=true时创建该bean，如果不存在performance，则根据
     * matchIfMissing的值判断是否生成bean，true生成
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "geedys.mybatis.performance", havingValue = "true", matchIfMissing = false)
    public MybatisLogInterceptor logInterceptor(){
        return new MybatisLogInterceptor();
    }
}
