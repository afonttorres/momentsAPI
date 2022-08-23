package com.factoria.moments.controllers;

import com.factoria.moments.dtos.cloudinary.CloudinaryMessage;
import com.factoria.moments.dtos.cloudinary.ImageResponseDto;
import com.factoria.moments.exceptions.BadRequestException;
import com.factoria.moments.models.Image;
import com.factoria.moments.services.cloudinary.ICloudinaryService;
import com.factoria.moments.services.cloudinary.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cloudinary")
@CrossOrigin
public class CloudinaryController {

    ICloudinaryService cloudinaryService;
    IImageService imageService;

    @Autowired
    public CloudinaryController(ICloudinaryService cloudinaryService, IImageService imageService){
        this.cloudinaryService = cloudinaryService;
        this.imageService = imageService;
    }

    @GetMapping("/images")
    public ResponseEntity<List<Image>> getAll(){
        List<Image> images = imageService.getAllImages();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<Image> getById(@PathVariable Long id){
        Image image = imageService.findById(id);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        if(bufferedImage == null) throw new BadRequestException("Invalid Image", "C-001");
        Map result = cloudinaryService.upload(multipartFile);
        Image image = new Image(result.get("original_filename").toString(), result.get("url").toString(), result.get("public_id").toString());
        imageService.save(image);
        return new ResponseEntity<>(new ImageResponseDto("Image uploaded!", image.getImgUrl(), image.getId()), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws IOException {
        Image image = imageService.findById(id);
        Map result = cloudinaryService.delete(image.getImgId());
        imageService.delete(id);
        return new ResponseEntity<>(new CloudinaryMessage("Image deleted!"), HttpStatus.OK);
    }

}
