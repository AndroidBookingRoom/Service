package com.example.serviceroom.hotel.hotel.hotelForm;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class HotelForm {
    private Integer id;
    private String lat;
    private String lon;
    private String guid;
    private String guidArea;
    private String name;
    private String address;
    private Date createdDate;
    private MultipartFile[] multipartFile;
    private String urlImg;
}
