package com.example.serviceroom.hotel.kindOfRoom.service;

import com.example.serviceroom.common.CommonUtil;
import com.example.serviceroom.hotel.hotel.bean.HotelBean;
import com.example.serviceroom.hotel.image.ImageBO;
import com.example.serviceroom.hotel.image.repository.ImageRepository;
import com.example.serviceroom.hotel.image.service.ImageService;
import com.example.serviceroom.hotel.kindOfRoom.KindOfRoom;
import com.example.serviceroom.hotel.kindOfRoom.bean.KODBean;
import com.example.serviceroom.hotel.kindOfRoom.form.KindOfRoomForm;
import com.example.serviceroom.hotel.kindOfRoom.repository.KindOfRoomRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
public class KindOfRoomService {
    private static final Logger log = LogManager.getLogger(KindOfRoomService.class);

    @Autowired
    private KindOfRoomRepository kindOfRoomRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;

    public List<KODBean> getListKOD(KindOfRoomForm form) {

        StringBuilder strQuery = new StringBuilder("SELECT k.id as id ," +
                " k.created_date as createdDate ," +
                " k.guid as guid ," +
                " k.name as name , " +
                "i.url_image as urlImg ");
        strQuery.append("   FROM kind_of_room k");
        strQuery.append("   JOIN image i ON  k.guid = i.guid_room ");
        strQuery.append("   WHERE 1=1 ");

//        if (Objects.nonNull(form.getName())){
//            strQuery.append("   AND k.name =  ").append(form.getName());
//        }
        strQuery.append("   ORDER BY k.created_date ");
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createSQLQuery(strQuery.toString())
                .setResultTransformer(Transformers.aliasToBean(KODBean.class));

        List lst = query.getResultList();
        return lst != null ? lst : new ArrayList<>();
    }

    public HashMap<Boolean, KODBean> createKindOfRoom(KindOfRoomForm kindOfRoomForm) {
        HashMap<Boolean, KODBean> map = new HashMap<>();
        try {
            ModelMapper modelMap = new ModelMapper();
            KindOfRoom kindOfRoom = modelMap.map(kindOfRoomForm, KindOfRoom.class);
            kindOfRoom.setGuid(UUID.randomUUID().toString());
            kindOfRoom.setCreatedDate(new Date());
            if (!CommonUtil.isEmpty(kindOfRoomForm.getMultipartFile())) {
                for (int i = 0; i < kindOfRoomForm.getMultipartFile().length; i++) {
                    boolean uploadImage = imageService.uploadImageForRoom(kindOfRoom.getGuid(), kindOfRoomForm.getMultipartFile()[i]);
                    if (!uploadImage) {
                        map.put(false, new KODBean());
                        return map;
                    }
                }
            }
            kindOfRoomRepository.save(kindOfRoom);
            ImageBO imageBO = imageRepository.findByGuidRoom(kindOfRoom.getGuid()).orElse(null);
            KODBean kodBean = modelMap.map(kindOfRoom, KODBean.class);
            if (Objects.nonNull(imageBO)) {
                kodBean.setUrlImg(imageBO.getUrlImage());
            }
            map.put(true, kodBean);
            return map;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        map.put(false, new KODBean());
        return map;
    }

    public HashMap<Boolean, KODBean> deleteKOD(String guid) {
        HashMap<Boolean, KODBean> map = new HashMap<>();
        try {
            ModelMapper modelMapper = new ModelMapper();
            KindOfRoom kindOfRoom = kindOfRoomRepository.findByGuid(guid).
                    orElseThrow(() -> new Exception("Kind of room not found - " + guid));
            KODBean kodBean = modelMapper.map(kindOfRoom, KODBean.class);
            kindOfRoomRepository.delete(kindOfRoom);
            map.put(true, kodBean);
            return map;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        map.put(false, new KODBean());
        return map;
    }
}
