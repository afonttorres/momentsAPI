package com.factoria.moments.dtos.saves;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveReqDto {
    Long momentId;
    Long saverId;
}
