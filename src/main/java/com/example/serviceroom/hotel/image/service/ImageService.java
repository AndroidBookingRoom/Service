package com.example.serviceroom.hotel.image.service;

import com.example.serviceroom.hotel.hotel.service.HotelService;
import com.example.serviceroom.hotel.image.ImageBO;
import com.example.serviceroom.hotel.image.repository.ImageRepository;
import com.example.serviceroom.service.CloudinaryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class ImageService {
    private static final Logger log = LogManager.getLogger(ImageService.class);

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
//    @Transactional
    public boolean uploadImageForHotel(String guid, MultipartFile multipartFile) {
        ImageBO image;
        try {
            BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
            if (Objects.isNull(bi)) {
                return false;
            }
            Map result = cloudinaryService.upload(multipartFile);
            image = imageRepository.findByGuidHotel(guid).orElse(null);
            if (Objects.isNull(image)) {
                image = new ImageBO();
                image.setGuidHotel(guid);
            } else {
                delete(image.getGuid());
            }
            image.setGuid(UUID.randomUUID().toString());
            image.setUrlImage(String.valueOf(result.get("url")));
            imageRepository.save(image);
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
    public boolean uploadImageForKOD(String guid, MultipartFile multipartFile) {
        ImageBO image;
        try {
            BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
            if (Objects.isNull(bi)) {
                return false;
            }
            Map result = cloudinaryService.upload(multipartFile);
            image = imageRepository.findByGuidHotel(guid).orElse(null);
            if (Objects.isNull(image)) {
                image = new ImageBO();
                image.setGuidKindOfRoom(guid);
            } else {
                delete(image.getGuid());
            }
            image.setGuid(UUID.randomUUID().toString());
            image.setUrlImage(String.valueOf(result.get("url")));
            imageRepository.save(image);
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
    public boolean uploadImageForRoom(String guid, MultipartFile multipartFile) {
        ImageBO image;
        try {
            BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
            if (Objects.isNull(bi)) {
                return false;
            }
            Map result = cloudinaryService.upload(multipartFile);
            image = imageRepository.findByGuidRoom(guid).orElse(null);
            if (Objects.isNull(image)) {
                image = new ImageBO();
                image.setGuidRoom(guid);
            } else {
                delete(image.getGuid());
            }
            image.setGuid(UUID.randomUUID().toString());
            image.setUrlImage(String.valueOf(result.get("url")));
            imageRepository.save(image);
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
    public void delete(String imageGuid) {
        cloudinaryService.delete(imageGuid);
        imageRepository.deleteByGuid(imageGuid);
    }
}

