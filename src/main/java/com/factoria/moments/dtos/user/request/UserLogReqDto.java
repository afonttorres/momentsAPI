package com.factoria.moments.dtos.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLogReqDto {
    private String email;
    private String password;
}
