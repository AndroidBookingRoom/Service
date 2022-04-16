package com.example.serviceroom.hotel.area.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AreaBean {
    private Integer id;
    private String guid;
    private String name;
    private Date createdDate;
}
