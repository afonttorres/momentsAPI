package com.factoria.moments.dtos.user.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateReqDto {
    private String username;
    private String avatarUrl;
    private String email;
    private String name;
    private String bannerUrl;
    private long followers;
    private long following;
    private String description;
}
