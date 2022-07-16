package com.factoria.moments.dtos.likes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeReqDto {
    Long momentId;
    Long likerId;
}
