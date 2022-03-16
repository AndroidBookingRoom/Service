package com.example.serviceroom.hotel.room.service;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class RoomService {
    private static final Logger log = LogManager.getLogger(RoomService.class);
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private EntityManager entityManager;

    public List<RoomBean> getListRoom(RoomForm form) {

        StringBuilder strQuery = new StringBuilder("SELECT * ");
        strQuery.append("   FROM room r");
        strQuery.append("   WHERE 1=1 ");

        if (Objects.nonNull(form.getGuid())) {
            strQuery.append("   AND r.guid =  ").append(form.getGuid());
        }
        strQuery.append("   ORDER BY r.created_date ");
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery(strQuery.toString())
                .setResultTransformer(Transformers.aliasToBean(RoomBean.class));

        List lst = query.getResultList();
        return lst != null ? lst : new ArrayList<>();
    }

    public boolean createRoom(RoomForm roomForm) {
        try {
            ModelMapper modelMap = new ModelMapper();
            RoomBO roomBO = modelMap.map(roomForm, RoomBO.class);
            roomBO.setGuid(UUID.randomUUID().toString());
            roomRepository.save(roomBO);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public boolean deleteRoom(String guid) {
        try {
            RoomBO roomBO = roomRepository.findByGuid(guid).
                    orElseThrow(() -> new Exception("Room not found - " + guid));
            roomRepository.delete(roomBO);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }


}
