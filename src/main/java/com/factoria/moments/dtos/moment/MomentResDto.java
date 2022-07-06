package com.factoria.moments.dtos.moment;

import com.factoria.moments.dtos.user.response.UserResDtoMoment;
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
    private UserResDtoMoment creator;
    private int commentsCount;
    private Long id;
}
