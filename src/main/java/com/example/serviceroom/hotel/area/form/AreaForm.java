package com.example.serviceroom.hotel.area.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AreaForm {
    private Integer id;
    private String guid;
    private String name;
    private Date createdDate;
}
