package com.factoria.moments.dtos.user.request;

import lombok.Data;

@Data
public class UserPostReqDto {
    private String email;
    private String username;
    private String password;
    private String name;

}
