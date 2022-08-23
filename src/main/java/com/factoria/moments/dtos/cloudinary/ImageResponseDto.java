package com.factoria.moments.dtos.cloudinary;

import lombok.Data;

@Data
public class ImageResponseDto {

    private String message;
    private String url;

    private Long id;

    public ImageResponseDto(String message, String url, Long id){
        this.message = message;
        this.url = url;
        this.id = id;
    }
}
