package com.geedys.geedysite.mybatis.mapper;

import com.geedys.geedysite.mybatis.provider.IDListProvider;
import org.apache.ibatis.annotations.DeleteProvider;

import java.util.List;

/**
 * @author resigshy Created on 2016-12-16 14:38
 */
public interface DeleteByIDListMapper<T, ID> {
    @DeleteProvider(
            type = IDListProvider.class,
            method = "dynamicSQL"
    )
    int deleteByIdList(List<ID> var);
}
