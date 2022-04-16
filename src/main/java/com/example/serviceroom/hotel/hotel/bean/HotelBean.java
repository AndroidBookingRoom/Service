package com.example.serviceroom.hotel.hotel.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HotelBean {
    private Integer id;
    private String lat;
    private String lon;
    private String guid;
    private String guidArea;
    private String nameArea;
    private String name;
    private String address;
    private Date createdDate;
    private String urlImg;

}
