package com.lppnb.gatewayimpl.database;

import com.lppnb.gatewayimpl.database.dataobject.ItemDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemDAO {
    int add(ItemDO itemDO);

    int delete(String id);

    int update(ItemDO itemDO);

    int selectCounts();

    ItemDO findById(String id);

    List<ItemDO> pageQuery(@Param("currentIndex") Integer currentIndex, @Param("pageSize") Integer pageSize);

    List<ItemDO> findByUserAndValue(@Param("userId") String userId, @Param("value") Double value);
}
