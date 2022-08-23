package com.factoria.moments.services.cloudinary;

import com.factoria.moments.exceptions.NotFoundException;
import com.factoria.moments.models.Image;
import com.factoria.moments.repositories.IImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ImageService implements IImageService{

    IImageRepository imageRepository;

    @Autowired
    public ImageService(IImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public List<Image> getAllImages(){
     return imageRepository.findByOrderById();
    }

    @Override
    public boolean exists(Long id) {
        return imageRepository.existsById(id);
    }


    @Override
    public Image findById(Long id) {
        var image = imageRepository.findById(id);
        if(image.isEmpty()) throw new NotFoundException("Image Not Found", "I-404");
        return image.get();
    }

    @Override
    public Image save(Image image){
        return imageRepository.save(image);
    }

    @Override
    public boolean delete(Long id){
        var image = imageRepository.findById(id);
        if(image.isEmpty()) throw new NotFoundException("Image Not Found", "I-404");
        imageRepository.delete(image.get());
        return  true;
    }
}
