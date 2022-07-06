package com.factoria.moments.dtos;

import com.factoria.moments.models.User;
import lombok.Data;

@Data
public class MomentReqDto {
    private String imgUrl;
    private String description;
    private String location;
    private Long userId;
}
