package com.factoria.moments.dtos.moment;

import com.factoria.moments.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MomentReqDto {
    private String imgUrl;
    private String description;
    private String location;
    private Long userId;
}
