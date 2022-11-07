package com.lppnb.executor;

import com.alibaba.cola.dto.Response;
import com.lppnb.domain.gateway.ItemGateway;
import com.lppnb.dto.ItemDeleteCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemDeleteCmdExe {
    @Autowired
    private ItemGateway itemGateway;

    public Response execute(ItemDeleteCmd cmd) {
        itemGateway.delete(cmd.getId());
        return Response.buildSuccess();
    }
}
