package com.example.serviceroom.hotel.room.service;

import com.example.serviceroom.common.CommonUtil;
import com.example.serviceroom.hotel.hotel.bean.HotelBean;
import com.example.serviceroom.hotel.image.ImageBO;
import com.example.serviceroom.hotel.image.repository.ImageRepository;
import com.example.serviceroom.hotel.image.service.ImageService;
import com.example.serviceroom.hotel.room.RoomBO;
import com.example.serviceroom.hotel.room.RoomBean.RoomBean;
import com.example.serviceroom.hotel.room.form.RoomForm;
import com.example.serviceroom.hotel.room.repository.RoomRepository;
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
public class RoomService {
    private static final Logger log = LogManager.getLogger(RoomService.class);
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;

    public List<RoomBean> getListRoom(RoomForm form) {

        StringBuilder strQuery = new StringBuilder("SELECT r.id as id ," +
                " r.created_date as  createdDate," +
                " r.guid as guid ," +
                " r.guid_hotel as guidHotel," +
                " r.guid_kind_of_room as guidKindOfRoom ," +
                " r.price as price ," +
                " i.url_image as urlImg ," +
                "h.name as hotelName ," +
                "k.name as kindOfRoomName  ");
        strQuery.append("   FROM room r");
        strQuery.append("   JOIN hotel h ON r.guid_hotel = h.guid");
        strQuery.append("   JOIN kind_of_room k ON k.guid = r.guid_kind_of_room");
        strQuery.append("   JOIN image i ON r.guid = i.guid_room");
        strQuery.append("   WHERE 1=1 ");

        if (Objects.nonNull(form.getGuid())) {
            strQuery.append("   AND r.guid =  ").append(form.getGuid());
        }
        strQuery.append("   ORDER BY r.created_date ");
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createSQLQuery(strQuery.toString())
                .setResultTransformer(Transformers.aliasToBean(RoomBean.class));
        List lst = query.getResultList();
        return lst != null ? lst : new ArrayList<>();
    }

    public HashMap<Boolean, RoomBean> createRoom(RoomForm roomForm) {
        HashMap<Boolean, RoomBean> map = new HashMap<>();
        try {
            ModelMapper modelMap = new ModelMapper();
            RoomBO roomBO = modelMap.map(roomForm, RoomBO.class);
            roomBO.setCreatedDate(new Date());
            roomBO.setGuid(UUID.randomUUID().toString());
            if (!CommonUtil.isEmpty(roomForm.getMultipartFile())) {
                for (int i = 0; i < roomForm.getMultipartFile().length; i++) {
                    boolean uploadImage = imageService.uploadImageForRoom(roomBO.getGuid(), roomForm.getMultipartFile()[i]);
                    if (!uploadImage) {
                        map.put(false, new RoomBean());
                        return map;
                    }
                }
            }
            roomRepository.save(roomBO);
            ImageBO imageBO = imageRepository.findByGuidRoom(roomBO.getGuid()).orElse(null);
            RoomBean roomBean = modelMap.map(roomBO, RoomBean.class);
            if (Objects.nonNull(imageBO)) {
                roomBean.setUrlImg(imageBO.getUrlImage());
            }
            map.put(true, roomBean);
            return map;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        map.put(false, new RoomBean());
        return map;
    }

    public HashMap<Boolean, RoomBean> deleteRoom(String guid) {
        HashMap<Boolean, RoomBean> map = new HashMap<>();
        try {
            ModelMapper modelMap = new ModelMapper();
            RoomBO roomBO = roomRepository.findByGuid(guid).
                    orElseThrow(() -> new Exception("Room not found - " + guid));
            RoomBean roomBean = modelMap.map(roomBO, RoomBean.class);
            roomRepository.delete(roomBO);
            map.put(true, roomBean);
            return map;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        map.put(false, new RoomBean());
        return map;
    }


}
