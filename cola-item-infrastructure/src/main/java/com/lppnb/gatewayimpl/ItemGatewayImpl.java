package com.lppnb.gatewayimpl;

import com.lppnb.convertor.ItemConvertor;
import com.lppnb.domain.gateway.ItemGateway;
import com.lppnb.domain.model.Item;
import com.lppnb.gatewayimpl.database.ItemDAO;
import com.lppnb.gatewayimpl.database.dataobject.ItemDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemGatewayImpl implements ItemGateway {
    @Autowired
    private ItemDAO itemDAO;


    @Override
    public void add(Item item) {
        itemDAO.add(ItemConvertor.toDataObject(item));
    }

    @Override
    public void delete(String id) {
        itemDAO.delete(id);
    }

    @Override
    public void update(Item item) {
        itemDAO.update(ItemConvertor.toDataObject(item));
    }

    @Override
    public int selectCounts() {
        return itemDAO.selectCounts();
    }

    @Override
    public Item findById(String id) {
        ItemDO itemDO = itemDAO.findById(id);
        if (itemDO == null) {
            return null;
        }
        Item item = new Item();
        BeanUtils.copyProperties(itemDO, item);
        return item;
    }

    @Override
    public List<Item> pageQuery(Integer currentIndex, Integer pageSize) {
        return itemDAO.pageQuery(currentIndex, pageSize).stream().map(itemDO -> {
            Item item = new Item();
            BeanUtils.copyProperties(itemDO, item);
            return item;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Item> findByUserAndValue(String userId, Double value) {
        return itemDAO.findByUserAndValue(userId, value).stream().map(itemDO -> {
            Item item = new Item();
            BeanUtils.copyProperties(itemDO, item);
            return item;
        }).collect(Collectors.toList());
    }
}
