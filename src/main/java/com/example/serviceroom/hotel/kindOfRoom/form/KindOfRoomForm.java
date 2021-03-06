package com.example.serviceroom.hotel.kindOfRoom.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class KindOfRoomForm {
    private int id;
    private String guid;
    private String name;
    private Date createdDate;
    private MultipartFile[] multipartFile;
    private String urlImg;

}
