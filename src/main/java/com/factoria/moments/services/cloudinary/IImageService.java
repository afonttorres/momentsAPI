package com.factoria.moments.services.cloudinary;

import com.factoria.moments.models.Image;

import java.io.IOException;
import java.util.List;

public interface IImageService {
    List<Image> getAllImages();
    boolean exists(Long id);
    Image findById(Long id);
    Image save(Image image);
    boolean delete(Long id);
    boolean deleteByUrl(String url) throws IOException;
}
