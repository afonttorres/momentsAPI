package com.factoria.moments.dtos.moment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MomentReqDto {
    private String description;
    private String location;
    private String imgUrl;
    private Long userId;

    public MomentReqDto(){

    }
}
