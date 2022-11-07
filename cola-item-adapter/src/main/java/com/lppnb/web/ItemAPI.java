package com.lppnb.web;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.fastjson.JSON;
import com.lppnb.api.ItemService;
import com.lppnb.dto.*;
import com.lppnb.dto.data.ItemDTO;
import com.lppnb.dto.data.ItemReceiveParam;
import com.lppnb.dto.data.UserLoginInfo;
import com.lppnb.gatewayimpl.oss.OSSManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemAPI {
    @Autowired
    private ItemService itemService;

    /**
     * 上传物品
     */
    @PostMapping("/upload")
    public Response upload(ItemReceiveParam param, HttpServletRequest request) throws IOException {
        UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("loginInfo");
        ItemDTO itemDTO = ItemDTO.valueOf(param.getName(), param.getValue(), loginInfo.getId(), loginInfo.getUserName(), param.getIntro());
        //上传图片并返回图片url
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String now = LocalDateTime.now().format(dateTimeFormatter);
        if (param.getImgs() != null && param.getImgs().length > 0) {
            String imgs = OSSManager.upload(param.getImgs(), "image-"+now);
            itemDTO.setImgs(imgs);
        }
        return itemService.addItem(new ItemAddCmd(itemDTO));
    }

    /**
     * 生成物品展示面板
     */
    @PostMapping("/show")
    public PageResponse<ItemDTO> show(@RequestBody ItemListByPageQry qry){
        //调用分页查询服务
        return itemService.listItemByPage(qry);
    }

    /**
     * 生成可交换物品列表
     */
    @GetMapping("/generate")
    public MultiResponse<ItemDTO> generate(@RequestParam Double value, HttpServletRequest request){
        UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("loginInfo");
        //调用查询服务
        return itemService.listItem(new ItemListQry(loginInfo.getId(), value));
    }

    /**
     * 我的物品
     */
    @GetMapping("/myitems")
    public MultiResponse<ItemDTO> myItems(HttpServletRequest request){
        UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("loginInfo");
        //调用查询服务
        return itemService.listItem(new ItemListQry(loginInfo.getId(), 0.0));
    }

    /**
     * 删除物品
     */
    @GetMapping("/delete")
    public Response delete(@RequestParam String itemId){
        return itemService.deleteItem(new ItemDeleteCmd(itemId));
    }

    /**
     * 更新物品信息
     */
    @PostMapping("/update")
    public Response update(ItemReceiveParam param) throws IOException {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(param.getId());
        if (!StringUtils.isEmpty(param.getName())) {
            itemDTO.setName(param.getName());
        }
        if (param.getValue() != null) {
            itemDTO.setValue(param.getValue());
        }
        if (!StringUtils.isEmpty(param.getIntro())) {
            itemDTO.setIntro(param.getIntro());
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String now = LocalDateTime.now().format(dateTimeFormatter);
        if (param.getImgs() != null && param.getImgs().length > 0) {
            SingleResponse<ItemDTO> item = itemService.getItem(new ItemGetQry(param.getId()));
            List<String> list = JSON.parseObject(item.getData().getImgs(), List.class);
            for (String s : list) {
                OSSManager.delete(s);
            }
            String imgs = OSSManager.upload(param.getImgs(), "image-"+now);
            itemDTO.setImgs(imgs);
        }
        return itemService.updateItem(new ItemUpdateCmd(itemDTO));
    }
}
