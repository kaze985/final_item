package com.lppnb.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.lppnb.dto.*;
import com.lppnb.dto.data.ItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "lppnb-item-app")
public interface ItemFeign {

    @GetMapping("/item/addItem")
    Response addItem(@RequestParam ItemAddCmd cmd);

    @GetMapping("/item/deleteItem")
    Response deleteItem(@RequestParam ItemDeleteCmd cmd);

    @GetMapping("/item/updateItem")
    Response updateItem(@RequestParam ItemUpdateCmd cmd);

    @GetMapping("/item/getItem")
    SingleResponse<ItemDTO> getItem(@RequestParam ItemGetQry qry);

    @GetMapping("/item/listItemByPage")
    PageResponse<ItemDTO> listItemByPage(@RequestParam ItemListByPageQry qry);

    @GetMapping("/item/listItem")
    MultiResponse<ItemDTO> listItem(@RequestParam ItemListQry qry);
}
