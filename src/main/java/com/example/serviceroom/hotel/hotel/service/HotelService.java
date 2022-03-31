package com.example.serviceroom.hotel.hotel.service;

import com.example.serviceroom.common.CommonUtil;
import com.example.serviceroom.common.Constants;
import com.example.serviceroom.common.Response;
import com.example.serviceroom.hotel.hotel.HotelBO;
import com.example.serviceroom.hotel.hotel.bean.HotelBean;
import com.example.serviceroom.hotel.hotel.hotelForm.HotelForm;
import com.example.serviceroom.hotel.hotel.repository.HotelRepository;
import com.example.serviceroom.hotel.image.ImageBO;
import com.example.serviceroom.hotel.image.repository.ImageRepository;
import com.example.serviceroom.hotel.image.service.ImageService;
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
public class HotelService {
    private static final Logger log = LogManager.getLogger(HotelService.class);
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;

    public List<HotelBean> getListHotel(HotelForm form) {
        StringBuilder strQuery = new StringBuilder("SELECT h.id as id, " +
                "h.lat as lat," +
                "h.lon as lon," +
                "h.guid_area as guidArea" +
                ",h.name as name," +
                "h.guid as guid," +
                "h.address as address " +
                ", h.created_date as createdDate" +
                ", i.url_image as urlImg ");
        strQuery.append("   FROM hotel h");
        strQuery.append("   JOIN image i ON i.guid_hotel = h.guid");
        strQuery.append("   WHERE 1=1 ");
//        if (Objects.nonNull(form.getGuidArea())) {
//            strQuery.append("   AND h.guidArea =  ").append(form.getGuidArea());
//        }
        if (Objects.nonNull(form.getName())) {
            strQuery.append("   AND h.name =  " ).append(form.getName());
        }
        strQuery.append("   ORDER BY h.created_date ");
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createSQLQuery(strQuery.toString())
                .setResultTransformer(Transformers.aliasToBean(HotelBean.class));

        List lst = query.getResultList();
        return lst != null ? lst : new ArrayList<>();
    }
//    public List<HotelBean> getListHotel(HotelForm form) {
//        StringBuilder strQuery = new StringBuilder("SELECT h.name as name  ");
//        strQuery.append("   FROM hotel h");
//        strQuery.append("   JOIN area a ON a.guid = h.guid_area ");
//        strQuery.append("   WHERE 1=1 ");
//        if (Objects.nonNull(form.getGuidArea())) {
//            strQuery.append("   AND h.guid_area =  ").append(form.getGuidArea());
//        }
//        if (Objects.nonNull(form.getName())) {
//            strQuery.append("   AND h.name =  ").append(form.getName());
//        }
//        strQuery.append("   ORDER BY h.created_date ");
//        Session session = entityManager.unwrap(Session.class);
//        Query query = session.createQuery(strQuery.toString())
//                .setResultTransformer(Transformers.aliasToBean(HotelBean.class));
//
//        List lst = query.getResultList();
//        return lst != null ? lst : new ArrayList<>();
//    }
//    public List<HotelBO> getListHotel() {
//        return hotelRepository.findAll();
//    }

    public HashMap<Boolean, HotelBean> createdHotel(HotelForm hotelForm) {
        HashMap<Boolean, HotelBean> map = new HashMap<>();
        try {
            ModelMapper modelMap = new ModelMapper();
            HotelBO hotelBO = modelMap.map(hotelForm, HotelBO.class);
            hotelBO.setGuid(UUID.randomUUID().toString());
            hotelBO.setCreatedDate(new Date());
            if (!CommonUtil.isEmpty(hotelForm.getMultipartFile())) {
                for (int i = 0; i < hotelForm.getMultipartFile().length; i++) {
                    boolean uploadImage = imageService.uploadImageForHotel(hotelBO.getGuid(), hotelForm.getMultipartFile()[i]);
                    if (!uploadImage) {
                        map.put(false, new HotelBean());
                        return map;
                    }
                }
            }
            hotelRepository.save(hotelBO);
            ImageBO imageBO = imageRepository.findByGuidHotel(hotelBO.getGuid()).orElse(null);
            HotelBean hotelBean = modelMap.map(hotelBO, HotelBean.class);
            if (Objects.nonNull(imageBO)) {
                hotelBean.setUrlImg(imageBO.getUrlImage());
            }
            map.put(true, hotelBean);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        map.put(false, new HotelBean());
        return map;
    }

    public List<HotelBO> getAllHotel() {
        return hotelRepository.findAll();
    }


    public HashMap<Boolean, HotelBean> deleteHotel(String guid) {
        HashMap<Boolean, HotelBean> map = new HashMap<>();
        try {
            ModelMapper modelMap = new ModelMapper();
            HotelBO hotelBO = hotelRepository.findByGuid(guid).
                    orElseThrow(() -> new Exception("Hotel not found - " + guid));
            HotelBean hotelBean = modelMap.map(hotelBO, HotelBean.class);
            hotelRepository.delete(hotelBO);
            map.put(true, hotelBean);
            return map;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        map.put(false, new HotelBean());
        return map;
    }

}
