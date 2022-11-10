package com.lppnb.service;

import com.alibaba.cola.catchlog.CatchAndLog;
import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.lppnb.api.ItemService;
import com.lppnb.dto.*;
import com.lppnb.dto.data.ItemDTO;
import com.lppnb.executor.ItemAddCmdExe;
import com.lppnb.executor.ItemDeleteCmdExe;
import com.lppnb.executor.ItemUpdateCmdExe;
import com.lppnb.executor.query.ItemGetQryExe;
import com.lppnb.executor.query.ItemListByPageQryExe;
import com.lppnb.executor.query.ItemListQryExe;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务实现类
 */
@Service
@DubboService
@CatchAndLog
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemAddCmdExe itemAddCmdExe;
    @Autowired
    private ItemDeleteCmdExe itemDeleteCmdExe;
    @Autowired
    private ItemUpdateCmdExe itemUpdateCmdExe;
    @Autowired
    private ItemGetQryExe itemGetQryExe;
    @Autowired
    private ItemListByPageQryExe itemListByPageQryExe;
    @Autowired
    private ItemListQryExe itemListQryExe;

    @Override
    public Response addItem(ItemAddCmd cmd) {
        return itemAddCmdExe.execute(cmd);
    }

    @Override
    public Response deleteItem(ItemDeleteCmd cmd) {
       return itemDeleteCmdExe.execute(cmd);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Response updateItem(ItemUpdateCmd cmd) {
        return itemUpdateCmdExe.execute(cmd);
    }

    @Override
    public SingleResponse<ItemDTO> getItem(ItemGetQry qry) {
        return itemGetQryExe.execute(qry);
    }

    @Override
    public PageResponse<ItemDTO> listItemByPage(ItemListByPageQry qry) {
        return itemListByPageQryExe.execute(qry);
    }

    @Override
    public MultiResponse<ItemDTO> listItem(ItemListQry qry) {
        return itemListQryExe.execute(qry);
    }
}
