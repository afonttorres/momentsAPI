package com.factoria.moments.dtos.user.request;

import lombok.Data;

@Data
public class UserUpdateReqDto {
    private Long id;
    private String username;
    private String avatarUrl;
    private String email;
    private String name;
    private String bannerUrl;
    private long followers;
    private long following;
    private String description;
}
