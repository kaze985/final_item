package com.lppnb.convertor;

import com.lppnb.domain.model.Item;
import com.lppnb.dto.data.ItemDTO;
import com.lppnb.gatewayimpl.database.dataobject.ItemDO;
import org.springframework.beans.BeanUtils;

public class ItemConvertor {
    public static Item toEntity(ItemDTO itemDTO){
        Item item = new Item();
        BeanUtils.copyProperties(itemDTO, item);
        return item;
    }

    public static ItemDO toDataObject(Item item){
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(item, itemDO);
        return itemDO;
    }
}
