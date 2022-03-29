package com.example.serviceroom.hotel.hotel.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HotelBean {
    private Integer id;
    private Float lat;
    private Float lon;
    private String guid;
    private String guidArea;
    private String name;
    private String address;
    private Date createdDate;
    private String urlImg;

}
