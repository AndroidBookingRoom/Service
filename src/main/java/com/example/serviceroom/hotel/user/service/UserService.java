package com.example.serviceroom.hotel.user.service;

import com.example.serviceroom.common.Constants;
import com.example.serviceroom.hotel.user.bean.UserBean;
import com.example.serviceroom.hotel.user.bo.UserBO;
import com.example.serviceroom.hotel.user.form.UserForm;
import com.example.serviceroom.hotel.user.reporisoty.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {
    private static final Logger log = LogManager.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createdUser(UserForm userForm) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(Constants.COMMON.DATE_TIME_FORMAT);
            ModelMapper modelMap = new ModelMapper();
            UserBO userBO = modelMap.map(userForm, UserBO.class);
            if (Objects.nonNull(userBO.getGuid())) {
                userBO.setUpdatedDate(new Date());
            } else {
                userBO.setGuid(UUID.randomUUID().toString());
                userBO.setCreatedDate(new Date());
            }
//            userBO.setDob(format.parse(userForm.getTxtDateOfBirth()));
            userBO.setPassword(passwordEncoder.encode(userBO.getPassword()));
            userRepository.save(userBO);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public UserBean findUserByUsername(String username) {
        UserBO userBo = userRepository.findUserBOByUsername(username);
        ModelMapper mapper = new ModelMapper();
        UserBean bean = mapper.map(userBo, UserBean.class);
        return bean;
    }
}
