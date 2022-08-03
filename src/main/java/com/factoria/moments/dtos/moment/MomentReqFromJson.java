package com.factoria.moments.dtos.moment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MomentReqFromJson {
    private String description;
    private String location;
    private String imgUrl;
    private String username;

    public MomentReqFromJson(){

    }
}
