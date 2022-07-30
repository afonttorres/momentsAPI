package com.factoria.moments.dtos.saves;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SaveResDto {
    Long id;
    Long momentId;
    Long savedId;
}
