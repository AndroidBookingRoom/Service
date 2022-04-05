package com.example.serviceroom.hotel.optionHotel.controller;

import com.example.serviceroom.common.Constants;
import com.example.serviceroom.common.Response;
import com.example.serviceroom.hotel.kindOfRoom.bean.KODBean;
import com.example.serviceroom.hotel.kindOfRoom.form.KindOfRoomForm;
import com.example.serviceroom.hotel.optionHotel.OHBean.OHBean;
import com.example.serviceroom.hotel.optionHotel.form.OHForm;
import com.example.serviceroom.hotel.optionHotel.service.OHService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/option-hotel")
public class OHController {
    @Autowired
    private OHService ohService;
    @GetMapping("/getAllOptionHotel")
    public Response getDataOptionHotel(OHForm form) {
        List<OHBean> lstHotel = ohService.getListOptionHotel(form);
        if (lstHotel.isEmpty()) {
            return new Response(Constants.RESPONSE_TYPE.SUCCESS, Constants.RESPONSE_CODE.SUCCESS, Constants.MESSAGE.ISEMPTY);
        }
        return new Response(Constants.RESPONSE_TYPE.SUCCESS, Constants.RESPONSE_CODE.SUCCESS).withData(lstHotel);
    }
    @PostMapping("/createOptionHotel")
    public Response createKOD(@RequestBody OHForm form) {
        HashMap<Boolean, OHBean> map = ohService.createOptionHotel(form);
        if (map.containsKey(true)) {
            return new Response(Constants.RESPONSE_TYPE.SUCCESS, Constants.RESPONSE_CODE.SUCCESS).withData(map.get(true));
        }
        return new Response(Constants.RESPONSE_TYPE.ERROR, Constants.RESPONSE_CODE.ERROR);
    }
    @DeleteMapping("/deleteOptionHotel")
    public Response deleteKOD(@RequestParam String id) {
        HashMap<Boolean, OHBean> map = ohService.deleteOptionHotel(id);
        if (map.containsKey(true)) {
            return new Response(Constants.RESPONSE_TYPE.SUCCESS, Constants.RESPONSE_CODE.SUCCESS).withData(map.get(true));
        }
        return new Response(Constants.RESPONSE_TYPE.ERROR, Constants.RESPONSE_CODE.ERROR);
    }
}
