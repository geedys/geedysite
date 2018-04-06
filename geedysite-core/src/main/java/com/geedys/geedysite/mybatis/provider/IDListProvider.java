package com.geedys.geedysite.mybatis.provider;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.*;
import tk.mybatis.mapper.provider.IdsProvider;

import java.util.Set;

/**
 * @author resigshy Created on 2016-12-16 13:09
 */
public class IDListProvider extends IdsProvider {
    public IDListProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String deleteByIdList(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        Set columnList = EntityHelper.getPKColumns(entityClass);
        if(columnList.size() == 1) {
            EntityColumn column = (EntityColumn)columnList.iterator().next();
            StringBuilder sql = new StringBuilder();
            sql.append(SqlHelper.deleteFromTable(entityClass, this.tableName(entityClass)));
            whereIn(sql, column);
            return sql.toString();
        } else {
            throw new RuntimeException("继承 deleteByIds 方法的实体类[" + entityClass.getCanonicalName() + "]中必须只有一个带有 @Id 注解的字段");
        }
    }

    public String selectByIdList(MappedStatement ms) {
        String string = this.select(ms);
        if("".equals(string)){
            throw new RuntimeException("继承 selectByIdList 方法的实体类[" + getEntityClass(ms).getCanonicalName() + "]中必须只有一个带有 @Id 注解的字段");
        }
        return string;
    }

    public String selectByIdListRowBounds(MappedStatement ms){
        String string = this.select(ms);
        if("".equals(string)){
            throw new RuntimeException("继承 selectByIdListRowBounds 方法的实体类[" + getEntityClass(ms).getCanonicalName() + "]中必须只有一个带有 @Id 注解的字段");
        }
        return string;
    }

    private String select(MappedStatement ms){
        Class entityClass = this.getEntityClass(ms);
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        if (columnList.size() == 1) {
            this.setResultType(ms, entityClass);
            StringBuilder sql = new StringBuilder();
            sql.append(SqlHelper.selectAllColumns(entityClass));
            sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
            EntityColumn column = columnList.iterator().next();
            whereIn(sql, column);
            sql.append(SqlHelper.orderByDefault(entityClass));
            return sql.toString();
        } else {
            return "";
        }
    }

    private void whereIn(StringBuilder sql, EntityColumn column) {
        sql.append("where 1=2");
        sql.append("<if test=\"collection != null and !collection.isEmpty()\">");
        sql.append(" or ");
        sql.append(column.getColumn());
        sql.append(" in <foreach collection=\"collection\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">");
        sql.append("#{item}");
        sql.append("</foreach>");
        sql.append("</if>");
    }
}
