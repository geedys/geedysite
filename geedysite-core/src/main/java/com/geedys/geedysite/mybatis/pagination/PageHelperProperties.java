package com.geedys.geedysite.mybatis.pagination;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.lang.reflect.*;
import java.util.*;

/**
 * Configuration properties for PageHelper.
 *
 * @author geedsy
 */
@Component
@ConfigurationProperties(prefix = "geedys.mybatis.page")
@Data
public class PageHelperProperties {

    private String dialect;
    private String helperDialect = "oracle";
    private String params = "count=countSql";

    private boolean offsetAsPageNum = false;
    private boolean rowBoundsWithCount = true;
    private boolean pageSizeZero = true;
    private boolean reasonable = true;
    private boolean supportMethodsArguments = true;
    private boolean autoRuntimeDialect = true;
    private boolean returnPageInfo = true;
    private boolean autoDialect = true;
    private boolean closeConn = true;

    public Properties toProperties() {
        Properties properties = new Properties();
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                properties.put(field.getName(), Optional.ofNullable(field.get(this)).orElse("").toString());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return properties;
    }
}