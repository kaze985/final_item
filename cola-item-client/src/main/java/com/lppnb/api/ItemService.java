package com.lppnb.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.lppnb.dto.*;
import com.lppnb.dto.data.ItemDTO;

/**
 * 服务接口
 */
public interface ItemService {
    Response addItem(ItemAddCmd cmd);

    Response deleteItem(ItemDeleteCmd cmd);

    Response updateItem(ItemUpdateCmd cmd);

    SingleResponse<ItemDTO> getItem(ItemGetQry qry);

    PageResponse<ItemDTO> listItemByPage(ItemListByPageQry qry);

    MultiResponse<ItemDTO> listItem(ItemListQry qry);

}
