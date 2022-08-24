package com.factoria.moments.services.cloudinary;

import com.factoria.moments.exceptions.NotFoundException;
import com.factoria.moments.models.Image;
import com.factoria.moments.repositories.IImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class ImageService implements IImageService{

    IImageRepository imageRepository;
    ICloudinaryService cloudinaryService;

    @Autowired
    public ImageService(IImageRepository imageRepository, ICloudinaryService cloudinaryService) {
        this.imageRepository = imageRepository;
        this.cloudinaryService = cloudinaryService;
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

    @Override
    public boolean deleteByUrl(String url) throws IOException {
        var image = imageRepository.findByImgUrl(url).stream().findFirst();
        if(image.isEmpty()) throw new NotFoundException("Image Not Found","I-404");
        cloudinaryService.delete(image.get().getImgId());
        imageRepository.delete(image.get());
        return true;
    }
}
