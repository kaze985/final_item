package com.lppnb.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class ItemDeleteCmd {
    @NonNull
    private String id;
}
