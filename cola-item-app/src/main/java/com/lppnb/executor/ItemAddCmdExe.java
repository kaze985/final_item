package com.lppnb.executor;

import com.alibaba.cola.dto.Response;
import com.lppnb.convertor.ItemConvertor;
import com.lppnb.domain.gateway.ItemGateway;
import com.lppnb.domain.model.Item;
import com.lppnb.dto.ItemAddCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemAddCmdExe {
    @Autowired
    private ItemGateway itemGateway;

    public Response execute(ItemAddCmd cmd) {
        Item item = ItemConvertor.toEntity(cmd.getItemDTO());
        itemGateway.add(item);
        return Response.buildSuccess();
    }
}
