package com.example.serviceroom.hotel.user.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
public class UserBean {
    private int id;
    private String guid;
    private String username;
    private String gmail;
    private String password;
    private String address;
    @Temporal(TemporalType.DATE)
    private Date dob;
    private String gender;
    private Date createdDate;
    private Date updatedDate;
}
