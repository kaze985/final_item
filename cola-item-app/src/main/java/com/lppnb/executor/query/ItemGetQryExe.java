package com.lppnb.executor.query;

import com.alibaba.cola.dto.SingleResponse;
import com.lppnb.domain.gateway.ItemGateway;
import com.lppnb.domain.model.Item;
import com.lppnb.dto.ItemGetQry;
import com.lppnb.dto.data.ItemDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemGetQryExe {
    @Autowired
    private ItemGateway itemGateway;

    public SingleResponse<ItemDTO> execute(ItemGetQry qry) {
        Item item = itemGateway.findById(qry.getId());
        ItemDTO itemDTO = new ItemDTO();
        BeanUtils.copyProperties(item, itemDTO);
        return SingleResponse.of(itemDTO);
    }
}
