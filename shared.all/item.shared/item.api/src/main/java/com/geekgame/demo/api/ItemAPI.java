package com.geekgame.demo.api;

import com.geekgame.demo.model.Item;
import com.geekgame.demo.model.Paging;
import com.geekgame.demo.model.Result;
import com.geekgame.demo.model.UserLoginInfo;
import com.geekgame.demo.param.BasePageParam;
import com.geekgame.demo.param.ItemReceiveParam;
import com.geekgame.demo.service.ItemService;
import com.geekgame.demo.util.COSManager;
import com.geekgame.demo.util.SnowflakeIdGenerator;
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
    @Autowired
    private SnowflakeIdGenerator generator;
    @Autowired
    private COSManager cosManager;

    @PostMapping("/upload")
    public Result<Item> upload(ItemReceiveParam param, HttpServletRequest request) throws IOException {
        Result<Item> result = new Result<>();

        Item item = new Item();
        item.setId(String.valueOf(generator.nextId()));
        item.setName(param.getName());
        item.setValue(param.getValue());

        if (param.getIntro() != null) {
            item.setIntro(param.getIntro());
        }

        item.setGmtCreated(LocalDateTime.now());
        item.setGmtModified(LocalDateTime.now());

        UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("loginInfo");
        item.setOwnerId(loginInfo.getId());
        item.setOwnerName(loginInfo.getUserName());

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String now = LocalDateTime.now().format(dateTimeFormatter);
        if (param.getImgs() != null && param.getImgs().length > 0) {
            String imgs = cosManager.upload(param.getImgs(), "image-"+now);
            item.setImgs(imgs);
        }

        Item add = itemService.add(item);
        if (add == null) {
            result.setMessage("上传失败");
            return result;
        }

        result.setSuccess(true);
        result.setData(item);
        return result;
    }

    @PostMapping("/show")
    public Result<Paging<Item>> show(@RequestBody BasePageParam param){
        Result<Paging<Item>> result = new Result<>();
        Paging<Item> itemPaging = itemService.pageQuery(param);
        if (itemPaging == null) {
            result.setMessage("结果为空");
            return result;
        }
        result.setSuccess(true);
        result.setData(itemPaging);
        return result;
    }

    @GetMapping("/generate")
    public Result<List<Item>> generate(@RequestParam Double value, HttpServletRequest request){
        Result<List<Item>> result = new Result<>();
        UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("loginInfo");
        List<Item> itemList = itemService.findByUserAndValue(loginInfo.getId(), value);
        if (itemList == null) {
            result.setMessage("结果为空");
            return result;
        }
        result.setSuccess(true);
        result.setData(itemList);
        return result;
    }

    @GetMapping("/myitems")
    public Result<List<Item>> myItems(HttpServletRequest request){
        Result<List<Item>> result = new Result<>();
        UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("loginInfo");
        List<Item> itemList = itemService.findByUserAndValue(loginInfo.getId(), null);
        if (itemList == null) {
            result.setMessage("结果为空");
            return result;
        }
        result.setSuccess(true);
        result.setData(itemList);
        return result;
    }

    @GetMapping("/delete")
    public Result delete(@RequestParam String itemId){
        Result result = new Result<>();
        boolean delete = itemService.delete(itemId);
        if (delete) {
            result.setSuccess(true);
        }
        return result;
    }

    @PostMapping("/update")
    public Result<Item> update(ItemReceiveParam param) throws IOException {
        Result<Item> result = new Result<>();
        Item item = new Item();
        item.setId(param.getId());
        if (!StringUtils.isEmpty(param.getName())) {
            item.setName(param.getName());
        }
        if (param.getValue() != null) {
            item.setValue(param.getValue());
        }
        if (!StringUtils.isEmpty(param.getIntro())) {
            item.setIntro(param.getIntro());
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String now = LocalDateTime.now().format(dateTimeFormatter);
        if (param.getImgs() != null && param.getImgs().length > 0) {
            String imgs = cosManager.upload(param.getImgs(), "image-"+now);
            item.setImgs(imgs);
        }

        Item update = itemService.update(item);
        if (update != null) {
            result.setSuccess(true);
            result.setData(update);
        }
        return result;
    }
}
