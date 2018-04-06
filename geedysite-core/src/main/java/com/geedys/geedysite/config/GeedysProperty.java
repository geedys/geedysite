package com.geedys.geedysite.config;

import lombok.Data;
import org.springframework.boot.context.properties.*;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "geedys", ignoreInvalidFields = true)
@Component
public class GeedysProperty {
    @NestedConfigurationProperty
    private Mybatis mybatis;

    @Data
    public static class Mybatis {
        /**
         * 监控MyBatis执行情况的性能
         */
        private boolean performance;
        /**
         * 将MyBatis执行过程中的sql语句进行格式化输出，如果printSql配置项为true，此配置项才会生效
         */
        private boolean formatSql = false;
        /**
         * MyBayis 执行过程中的sql语句是否打印输出到日志中
         */
        private boolean printSql = false;
    }
}
