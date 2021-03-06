package com.example.serviceroom.hotel.kindOfRoom.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class KODBean {
    private int id;
    private String guid;
    private String name;
    private Date createdDate;
    private String urlImg;
}
