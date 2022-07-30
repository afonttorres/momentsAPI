package com.factoria.moments.dtos.likes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LikeResDto {
    Long id;
    Long momentId;
    Long likerId;
}
