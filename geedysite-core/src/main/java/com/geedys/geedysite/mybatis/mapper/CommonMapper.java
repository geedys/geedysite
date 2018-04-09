package com.geedys.geedysite.mybatis.mapper;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.*;

/**
 * @author resigshy Created on 2016-12-16 14:45
 */
@RegisterMapper
public interface CommonMapper<T, ID> extends Mapper<T>, IDListMapper<T, ID>, IdsMapper<T> {
}
