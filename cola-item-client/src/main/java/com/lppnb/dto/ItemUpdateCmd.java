package com.lppnb.dto;

import com.lppnb.dto.data.ItemDTO;
import lombok.Data;
import lombok.NonNull;

@Data
public class ItemUpdateCmd {
    @NonNull
    private ItemDTO itemDTO;
}
