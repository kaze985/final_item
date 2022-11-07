package com.lppnb.executor.query;

import com.alibaba.cola.dto.MultiResponse;
import com.lppnb.domain.gateway.ItemGateway;
import com.lppnb.domain.model.Item;
import com.lppnb.dto.ItemListQry;
import com.lppnb.dto.data.ItemDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemListQryExe {
    @Autowired
    private ItemGateway itemGateway;

    public MultiResponse<ItemDTO> execute(ItemListQry qry) {
        List<Item> list = itemGateway.findByUserAndValue(qry.getUserId(), qry.getValue());
        List<ItemDTO> collect = list.stream().map(item -> {
            ItemDTO itemDTO = new ItemDTO();
            BeanUtils.copyProperties(item, itemDTO);
            return itemDTO;
        }).collect(Collectors.toList());
        return MultiResponse.of(collect);
    }
}
