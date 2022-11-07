package com.lppnb.domain.gateway;

import com.lppnb.domain.model.Item;

import java.util.List;

public interface ItemGateway {
    void add(Item item);

    void delete(String id);

    void update(Item item);

    int selectCounts();

    Item findById(String id);

    List<Item> pageQuery(Integer currentIndex, Integer pageSize);

    List<Item> findByUserAndValue(String userId, Double value);
}
