package com.factoria.moments.dtos.user.response;

import lombok.Data;

@Data
public class UserNoPassResDto {
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
