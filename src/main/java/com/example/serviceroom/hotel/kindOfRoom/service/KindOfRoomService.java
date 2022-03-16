package com.example.serviceroom.hotel.kindOfRoom.service;

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

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class KindOfRoomService {
    private static final Logger log = LogManager.getLogger(KindOfRoomService.class);

    @Autowired
    private KindOfRoomRepository kindOfRoomRepository;
    @Autowired
    private EntityManager entityManager;

    public List<KODBean> getListKOD(KindOfRoomForm form) {

        StringBuilder strQuery = new StringBuilder("SELECT * ");
        strQuery.append("   FROM kind_of_room k");
        strQuery.append("   WHERE 1=1 ");

        if (Objects.nonNull(form.getName())){
            strQuery.append("   AND k.name =  ").append(form.getName());
        }
        strQuery.append("   ORDER BY k.created_date ");
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery(strQuery.toString())
                .setResultTransformer(Transformers.aliasToBean(KODBean.class));

        List lst = query.getResultList();
        return lst != null ? lst : new ArrayList<>();
    }

    public boolean createKindOfRoom(KindOfRoomForm kindOfRoomForm) {
        try {
            ModelMapper modelMap = new ModelMapper();
            KindOfRoom kindOfRoom = modelMap.map(kindOfRoomForm, KindOfRoom.class);
            kindOfRoom.setGuid(UUID.randomUUID().toString());
            kindOfRoomRepository.save(kindOfRoom);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public boolean deleteKOD(String guid) {
        try {
            KindOfRoom kindOfRoom = kindOfRoomRepository.findByGuid(guid).
                    orElseThrow(() -> new Exception("Kind of room not found - " + guid));
            kindOfRoomRepository.delete(kindOfRoom);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
