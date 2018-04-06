package com.geedys.geedysite.mybatis.mapper;

/**
 * @author resigshy Created on 2016-12-16 13:13
 */
public interface IDListMapper<T, ID> extends SelectByIDListMapper<T, ID>, SelectByIdListRowBoundsMapper<T, ID>, DeleteByIDListMapper<T, ID> {
}
