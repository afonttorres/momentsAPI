package com.factoria.moments.dtos.comment;

import com.factoria.moments.dtos.user.response.UserResDtoMoment;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class CommentResDto {
    private Long id;
    private String comment;
    private boolean isLiked;
    private Long momentId;
    private UserResDtoMoment creator;
}
