package com.factoria.moments.dtos.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPostReqDto {
    private String email;
    private String username;
    private String password;
    private String name;

}
