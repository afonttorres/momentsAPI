package com.factoria.moments.dtos;

import lombok.Data;

@Data
public class CommentRequestDto {
    private String comment;
    private boolean isLiked;
    private Long momentId;
    private Long userId;
}
