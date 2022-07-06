package com.factoria.moments.dtos.comment;

import lombok.Data;

@Data
public class CommentRequestDto {
    private String comment;
    private Long momentId;
    private Long userId;
}
