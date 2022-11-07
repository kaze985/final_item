package com.lppnb.dto.data;

import cn.hutool.core.util.IdUtil;
import com.alibaba.cola.dto.DTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemDTO extends DTO {
    /**
     * 物品id
     */
    private String id;

    /**
     * 物品名
     */
    private String name;

    /**
     * 物品价值
     */
    private Double value;

    /**
     * 物品拥有者id
     */
    private String ownerId;

    /**
     * 物品拥有者用户名
     */
    private String ownerName;

    /**
     * 物品简介
     */
    private String intro;

    /**
     * 物品图片
     */
    private String imgs;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreated;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    public static ItemDTO valueOf(String name, Double value, String ownerId, String ownerName, String intro) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(IdUtil.getSnowflake(2, 1).nextIdStr());
        itemDTO.setName(name);
        itemDTO.setValue(value);
        itemDTO.setOwnerId(ownerId);
        itemDTO.setOwnerName(ownerName);
        itemDTO.setIntro(intro);
        itemDTO.setGmtCreated(LocalDateTime.now());
        itemDTO.setGmtModified(LocalDateTime.now());
        return itemDTO;
    }
}
