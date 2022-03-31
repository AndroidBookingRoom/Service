package com.example.serviceroom.hotel.room.RoomBean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RoomBean {
    private int id;
    private String guid;
    private String guidKindOfRoom;
    private String guidHotel;
    private Double price;
    private Date created_date;
    private String urlImg;

}
