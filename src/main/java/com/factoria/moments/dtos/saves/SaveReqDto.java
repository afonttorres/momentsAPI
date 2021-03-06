package com.factoria.moments.dtos.saves;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SaveReqDto {
    Long momentId;
    Long saverId;
}
