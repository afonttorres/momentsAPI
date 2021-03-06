package com.factoria.moments.dtos.moment;

import com.factoria.moments.dtos.user.response.UserResDtoMoment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MomentResDto {
    private String imgUrl;
    private String description;
    private String location;
    private boolean isLiked;
    private boolean isSaved;
    private int likesCount;
    private int savesCount;
    private UserResDtoMoment creator;
    private int commentsCount;
    private Long id;
}
