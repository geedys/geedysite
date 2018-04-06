package com.geedys.geedysite.mapper;

import com.geedys.geedysite.entity.PlatformUserTab;
import com.geedys.geedysite.mybatis.mapper.CommonMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.Map;

@Mapper
public interface PlatformUserTabMapper extends CommonMapper<PlatformUserTab, String> {

    @Select("{#{r.res,mode=OUT,jdbcType=VARCHAR} = call platform_user_api.login_check(#{u,mode=IN,jdbcType=VARCHAR},#{p,mode=IN,jdbcType=VARCHAR})}")
    @Options(statementType = StatementType.CALLABLE)
    void login(@Param("u") String u, @Param("p") String p, @Param("r") Map<String,Object> string);
}