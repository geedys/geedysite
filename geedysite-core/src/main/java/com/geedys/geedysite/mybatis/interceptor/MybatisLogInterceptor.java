package com.geedys.geedysite.mybatis.interceptor;

import com.geedys.geedysite.config.GeedysProperty;
import com.geedys.geedysite.util.SQLFormatter;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.*;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.*;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * 拦截MyBatis执行sql时使用的Connection，用于对该Connection设置语言和用户
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class MybatisLogInterceptor implements Interceptor {
    private final static Logger logger = LoggerFactory.getLogger(MybatisLogInterceptor.class);
    private static final SQLFormatter SQL_FORMATTER = new SQLFormatter();
    private final static ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private GeedysProperty geedysProperty;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        if (!(invocation.getTarget() instanceof RoutingStatementHandler)) {
            GeedysProperty.Mybatis myBatis = geedysProperty.getMybatis();
            if (myBatis != null && myBatis.isPrintSql()) {
                executorService.execute(() -> {
                    MappedStatement ms = (MappedStatement) args[0];
                    String statementId = ms.getId();
                    Object parameterObject = null;
                    if (args.length > 1) {
                        parameterObject = args[1];
                    }
                    BoundSql boundSql;
                    //由于逻辑关系，只会进入一次
                    if (args.length == 4) {
                        //4 个参数时
                        boundSql = ms.getBoundSql(parameterObject);
                    } else {
                        boundSql = (BoundSql) args[5];
                    }
                    Configuration configuration = ms.getConfiguration();
                    String sql = getSql(boundSql, parameterObject, configuration);
                    String s = myBatis.isFormatSql() ? SQL_FORMATTER.format(sql) : sql;
                    logger.debug("MapperID:" + statementId);
                    logger.debug("SQL:" + s);
                });
            }
        }
        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        long end = System.currentTimeMillis();
        long timing = end - start;
        logger.debug("耗时:" + timing + " ms");
        return result;
    }

    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    public void setProperties(Properties properties) {
    }

    private String getSql(BoundSql boundSql, Object parameterObject, Configuration configuration) {
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        if (parameterMappings != null) {
            for (ParameterMapping parameterMapping : parameterMappings) {
                //if (parameterMapping.getMode() != ParameterMode.OUT) {
                Object value;
                String propertyName = parameterMapping.getProperty();
                if (boundSql.hasAdditionalParameter(propertyName)) {
                    value = boundSql.getAdditionalParameter(propertyName);
                } else if (parameterObject == null) {
                    value = null;
                } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                    value = parameterObject;
                } else {
                    MetaObject metaObject = configuration.newMetaObject(parameterObject);
                    value = metaObject.getValue(propertyName);
                }
                sql = replacePlaceholder(sql, propertyName, value, parameterMapping.getMode());
                //}
            }
        }
        return sql;
    }

    private String replacePlaceholder(String sql, String propertyName, Object propertyValue, ParameterMode mode) {
        String result;
        if (mode == ParameterMode.OUT) {
            result = "#{OUT:" + propertyName + "}";
        } else {
            if (propertyValue != null) {
                if (propertyValue instanceof String) {
                    result = "'" + propertyValue + "'";
                } else if (propertyValue instanceof Date) {
                    result = "'" + DATE_FORMAT.format(propertyValue) + "'";
                } else {
                    result = propertyValue.toString();
                }
            } else {
                result = "null";
            }
        }
        return sql.replaceFirst("\\?", result);
    }
}