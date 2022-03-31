package com.example.serviceroom.hotel.kindOfRoom.controller;

import com.example.serviceroom.common.Constants;
import com.example.serviceroom.common.Response;
import com.example.serviceroom.hotel.kindOfRoom.bean.KODBean;
import com.example.serviceroom.hotel.kindOfRoom.form.KindOfRoomForm;
import com.example.serviceroom.hotel.kindOfRoom.service.KindOfRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/kod")
public class KindOfRoomController {
    @Autowired
    private KindOfRoomService kindOfRoomService;

    @GetMapping("/getAllKOD")
    public Response getDataKOD(KindOfRoomForm form) {
        List<KODBean> lstHotel = kindOfRoomService.getListKOD(form);
        if (lstHotel.isEmpty()) {
            return new Response(Constants.RESPONSE_TYPE.SUCCESS, Constants.RESPONSE_CODE.SUCCESS, Constants.MESSAGE.ISEMPTY);
        }
        return new Response(Constants.RESPONSE_TYPE.SUCCESS, Constants.RESPONSE_CODE.SUCCESS).withData(lstHotel);
    }

    @PostMapping("/createKOD")
    public Response createKOD(KindOfRoomForm kindOfRoomForm) {
        HashMap<Boolean, KODBean> map = kindOfRoomService.createKindOfRoom(kindOfRoomForm);
        if (map.containsKey(true)) {
            return new Response(Constants.RESPONSE_TYPE.SUCCESS, Constants.RESPONSE_CODE.SUCCESS).withData(map.get(true));
        }
        return new Response(Constants.RESPONSE_TYPE.ERROR, Constants.RESPONSE_CODE.ERROR);
    }

    @DeleteMapping("/deleteKOD")
    public Response deleteKOD(@RequestParam String id) {
        HashMap<Boolean, KODBean> map = kindOfRoomService.deleteKOD(id);
        if (map.containsKey(true)) {
            return new Response(Constants.RESPONSE_TYPE.SUCCESS, Constants.RESPONSE_CODE.SUCCESS).withData(map.get(true));
        }
        return new Response(Constants.RESPONSE_TYPE.ERROR, Constants.RESPONSE_CODE.ERROR);
    }
}
