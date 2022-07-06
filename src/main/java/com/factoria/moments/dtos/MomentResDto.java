package com.factoria.moments.dtos;

import lombok.Data;

@Data
public class MomentResDto {
    private String imgUrl;
    private String description;
    private String location;
    private boolean isLiked;
    private boolean isSaved;
    private int likes;
    private int saves;
    private UserResDto creator;
    private int commentsCount;
    private Long id;
}
