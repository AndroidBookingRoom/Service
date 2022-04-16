package com.example.serviceroom.hotel.hotel;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "hotel")
@Getter
@Setter
public class HotelBO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "guid")
    private String guid;
    @Column(name = "lat")
    private String lat;
    @Column(name = "lon")
    private String lon;
    @Column(name = "guid_area")
    private String guidArea;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "created_date")
    private Date createdDate;

}
