package com.geedys.geedysite.mybatis.mapper;

import com.geedys.geedysite.mybatis.provider.IDListProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author resigshy Created on 2016-12-16 14:37
 */
public interface SelectByIDListMapper<T,ID> {
    @SelectProvider(
            type = IDListProvider.class,
            method = "dynamicSQL"
    )
    List<T> selectByIdList(List<ID> var);
}
