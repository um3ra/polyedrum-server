package com.shop.polyedrum.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.shop.polyedrum.exception.ApiException;
import com.shop.polyedrum.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageDataService {
    private final ImageDataRepository imageDataRepository;
    @Value("${aws.cellar.a3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;

    public ImageData uploadImage(MultipartFile file) throws IOException{
        if (file.getOriginalFilename().endsWith("png") || file.getOriginalFilename().endsWith("jpg")){
            if (imageDataRepository.findByName(file.getOriginalFilename()).isPresent()){
                throw new ApiException("image with name " + file.getOriginalFilename() + " is already exists", HttpStatus.BAD_REQUEST);
            }
            if (!amazonS3.doesObjectExist(bucket, file.getOriginalFilename())){
                File fileObj = convertMultiPartFileToFile(file);
                amazonS3.putObject(new PutObjectRequest(bucket, file.getOriginalFilename(), fileObj));
                fileObj.delete();
            }
            return imageDataRepository.save(
                    ImageData.builder()
                            .name(file.getOriginalFilename())
                            .type(file.getContentType())
                            .build()
            );
        }else {
            throw new ApiException("incorrect format. Please choose jpg or png", HttpStatus.BAD_REQUEST);
        }
    }

    public byte[] downloadImage(String name) throws IOException {
        imageDataRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Image", "name", name));
        S3Object s3Object = amazonS3.getObject(bucket, name);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        byte[] content = IOUtils.toByteArray(inputStream);
        return content;
    }

    private File convertMultiPartFileToFile(MultipartFile file) throws IOException{
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(file.getBytes());
        }
        return convertedFile;
    }
}
