package com.example.serviceroom.hotel.optionHotel.service;

import com.example.serviceroom.common.CommonUtil;
import com.example.serviceroom.hotel.image.ImageBO;
import com.example.serviceroom.hotel.kindOfRoom.KindOfRoom;
import com.example.serviceroom.hotel.kindOfRoom.bean.KODBean;
import com.example.serviceroom.hotel.kindOfRoom.form.KindOfRoomForm;
import com.example.serviceroom.hotel.optionHotel.OHBean.OHBean;
import com.example.serviceroom.hotel.optionHotel.OptionHotelBO;
import com.example.serviceroom.hotel.optionHotel.form.OHForm;
import com.example.serviceroom.hotel.optionHotel.repository.OHRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
public class OHService {
    private static final Logger log = LogManager.getLogger(OHService.class);

    @Autowired
    private OHRepository ohRepository;
    @Autowired
    EntityManager entityManager;


    public List<OptionHotelBO> getAll() {
        return ohRepository.findAll();
    }

    public List<OHBean> getListOptionHotel(OHForm form) {

        StringBuilder strQuery = new StringBuilder("SELECT oh.id as id ," +
                " oh.guid_hotel as guidHotel ," +
                " oh.option_name as optionName ," +
                "oh.guid as guid," +
                " h.name as hotelName");
        strQuery.append("   FROM option_hotel oh");
        strQuery.append("   JOIN hotel h on oh.guid_hotel = h.guid ");
        strQuery.append("   WHERE 1=1 ");

//        if (Objects.nonNull(form.getName())){
//            strQuery.append("   AND k.name =  ").append(form.getName());
//        }
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createSQLQuery(strQuery.toString())
                .setResultTransformer(Transformers.aliasToBean(OHBean.class));
        List lst = query.getResultList();
        return lst != null ? lst : new ArrayList<>();
    }

    public HashMap<Boolean, OHBean> createOptionHotel(OHForm form) {
        HashMap<Boolean, OHBean> map = new HashMap<>();
        try {
            ModelMapper modelMap = new ModelMapper();
            OptionHotelBO optionHotelBO = modelMap.map(form, OptionHotelBO.class);
            optionHotelBO.setGuid(UUID.randomUUID().toString());
            ohRepository.save(optionHotelBO);
            OHBean ohBean = modelMap.map(optionHotelBO, OHBean.class);
            map.put(true, ohBean);
            return map;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        map.put(false, new OHBean());
        return map;
    }

    public HashMap<Boolean, OHBean> deleteOptionHotel(String guid) {
        HashMap<Boolean, OHBean> map = new HashMap<>();
        try {
            ModelMapper modelMapper = new ModelMapper();
            OptionHotelBO optionHotelBO = ohRepository.findByGuid(guid).
                    orElseThrow(() -> new Exception("Option Hotel not found - " + guid));
            OHBean ohBean = modelMapper.map(optionHotelBO, OHBean.class);
            ohRepository.delete(optionHotelBO);
            map.put(true, ohBean);
            return map;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        map.put(false, new OHBean());
        return map;
    }
}
