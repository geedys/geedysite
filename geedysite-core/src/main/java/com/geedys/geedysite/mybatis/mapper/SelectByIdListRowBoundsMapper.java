package com.geedys.geedysite.mybatis.mapper;

import com.geedys.geedysite.mybatis.provider.IDListProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author resigshy Created on 2016-12-16 14:53
 */
public interface SelectByIdListRowBoundsMapper <T,ID> {
    @SelectProvider(
            type = IDListProvider.class,
            method = "dynamicSQL"
    )
    List<T> selectByIdListRowBounds(List<ID> var, RowBounds rowBounds);
}
