package com.lppnb.executor.query;

import com.alibaba.cola.dto.PageResponse;
import com.lppnb.domain.gateway.ItemGateway;
import com.lppnb.domain.model.Item;
import com.lppnb.dto.ItemListByPageQry;
import com.lppnb.dto.data.ItemDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemListByPageQryExe {
    @Autowired
    private ItemGateway itemGateway;

    public PageResponse<ItemDTO> execute(ItemListByPageQry qry) {
        List<Item> items = itemGateway.pageQuery(qry.getOffset(), qry.getPageSize());
        List<ItemDTO> collect = items.stream().map(item -> {
            ItemDTO itemDTO = new ItemDTO();
            BeanUtils.copyProperties(item, itemDTO);
            return itemDTO;
        }).collect(Collectors.toList());
        Collections.shuffle(collect);
        return PageResponse.of(collect, itemGateway.selectCounts(), qry.getPageSize(), qry.getPageIndex());
    }
}
