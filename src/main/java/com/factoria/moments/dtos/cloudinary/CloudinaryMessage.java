package com.factoria.moments.dtos.cloudinary;

import lombok.Data;

@Data
public class CloudinaryMessage {
    private String message;

    public CloudinaryMessage(String message) {
        this.message = message;
    }




}
