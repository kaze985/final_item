package com.lppnb.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class ItemListQry {
    @NonNull
    private String userId;
    @NonNull
    private Double value;
}
