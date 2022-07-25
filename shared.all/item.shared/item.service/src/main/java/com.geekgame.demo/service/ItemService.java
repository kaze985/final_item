package com.geekgame.demo.service;

import com.geekgame.demo.model.Item;
import com.geekgame.demo.model.Paging;
import com.geekgame.demo.param.BasePageParam;

import java.util.List;

public interface ItemService {
    Item add(Item item);

    boolean delete(String id);

    Item update(Item item);

    Item findById(String id);

    Paging<Item> pageQuery(BasePageParam param);

    List<Item> findByUserAndValue(String userId, Double value);

}
