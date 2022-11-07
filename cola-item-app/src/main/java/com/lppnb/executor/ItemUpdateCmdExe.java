package com.lppnb.executor;

import com.alibaba.cola.dto.Response;
import com.lppnb.convertor.ItemConvertor;
import com.lppnb.domain.gateway.ItemGateway;
import com.lppnb.dto.ItemUpdateCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemUpdateCmdExe {
    @Autowired
    private ItemGateway itemGateway;

    public Response execute(ItemUpdateCmd cmd) {
        itemGateway.update(ItemConvertor.toEntity(cmd.getItemDTO()));
        return Response.buildSuccess();
    }
}
