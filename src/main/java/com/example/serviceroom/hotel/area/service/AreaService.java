package com.example.serviceroom.hotel.area.service;

import com.example.serviceroom.hotel.area.bo.AreaBO;
import com.example.serviceroom.hotel.area.form.AreaForm;
import com.example.serviceroom.hotel.area.repository.AreaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AreaService {
    @Autowired
    private AreaRepository areaRepository;

    public List<AreaBO> getListArea() {
        return areaRepository.findAll();
    }

    public boolean saveOrUpdate(AreaForm form) {
        try {
            ModelMapper mapper = new ModelMapper();
            AreaBO areaBO = mapper.map(form, AreaBO.class);
            areaBO.setGuid(UUID.randomUUID().toString());
            areaBO.setCreatedDate(new Date());
            areaRepository.save(areaBO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
