package com.example.serviceroom.hotel.room.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class RoomForm {
    private int id;
    private String guid;
    private String guidKindOfRoom;
    private String guidHotel;
    private Double price;
    private Date created_date;
    private MultipartFile[] multipartFile;
    private String urlImg;
}
