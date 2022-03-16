package com.example.serviceroom.hotel.kindOfRoom.controller;

import com.example.serviceroom.common.Constants;
import com.example.serviceroom.common.Response;
import com.example.serviceroom.hotel.kindOfRoom.bean.KODBean;
import com.example.serviceroom.hotel.kindOfRoom.form.KindOfRoomForm;
import com.example.serviceroom.hotel.kindOfRoom.service.KindOfRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/kod")
public class KindOfRoomController {
    @Autowired
    private KindOfRoomService kindOfRoomService;
    @GetMapping("/getAllKOD")
    public Response getDataKOD(KindOfRoomForm form) {
        List<KODBean> lstHotel = kindOfRoomService.getListKOD(form);
        if (lstHotel.isEmpty()){
            return new Response(Constants.RESPONSE_TYPE.SUCCESS,Constants.RESPONSE_CODE.SUCCESS,Constants.MESSAGE.ISEMPTY);
        }
        return new Response(Constants.RESPONSE_TYPE.SUCCESS,Constants.RESPONSE_CODE.SUCCESS).withData(lstHotel);
    }
    @PostMapping("/createKOD")
    public Response createKOD(@RequestBody KindOfRoomForm kindOfRoomForm) {
        boolean flag = kindOfRoomService.createKindOfRoom(kindOfRoomForm);
        if (flag) {
            return new Response(Constants.RESPONSE_TYPE.SUCCESS, Constants.RESPONSE_CODE.SUCCESS);
        }
        return new Response(Constants.RESPONSE_TYPE.ERROR, Constants.RESPONSE_CODE.ERROR);
    }
    @DeleteMapping("/deleteKOD")
    public Response deleteKOD(@RequestBody KindOfRoomForm kindOfRoomForm){
        boolean flag = kindOfRoomService.deleteKOD(kindOfRoomForm.getGuid());
        if (flag) {
            return new Response(Constants.RESPONSE_TYPE.SUCCESS, Constants.RESPONSE_CODE.SUCCESS);
        }
        return new Response(Constants.RESPONSE_TYPE.ERROR, Constants.RESPONSE_CODE.ERROR);
    }
}
