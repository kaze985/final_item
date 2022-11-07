package com.lppnb.domain.model;

import com.alibaba.cola.domain.Entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 物品模型
 */
@Data
@Entity
public class Item {
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
}
