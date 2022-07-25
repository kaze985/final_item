package com.geekgame.demo.api;

import com.geekgame.demo.model.Item;
import com.geekgame.demo.model.Result;
import com.geekgame.demo.model.UserLoginInfo;
import com.geekgame.demo.param.ItemReceiveParam;
import com.geekgame.demo.service.ItemService;
import com.geekgame.demo.util.COSManager;
import com.geekgame.demo.util.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        item.setIntro(param.getIntro());
        item.setGmtCreated(LocalDateTime.now());
        item.setGmtModified(LocalDateTime.now());
        UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("loginInfo");
        item.setOwnerId(loginInfo.getId());
        item.setOwnerName(loginInfo.getUserName());

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String now = LocalDateTime.now().format(dateTimeFormatter);
        String imgs = cosManager.upload(param.getImgs(), "image-"+now);
        item.setImgs(imgs);

        Item add = itemService.add(item);
        if (add == null) {
            return result;
        }

        result.setSuccess(true);
        result.setData(item);
        return result;
    }
}
