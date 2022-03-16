package com.example.serviceroom.hotel.room.controller;

import com.example.serviceroom.common.Constants;
import com.example.serviceroom.common.Response;
import com.example.serviceroom.hotel.room.RoomBean.RoomBean;
import com.example.serviceroom.hotel.room.form.RoomForm;
import com.example.serviceroom.hotel.room.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/get-all-room")
    public Response getDataRoom(RoomForm form) {
        List<RoomBean> lstRoom = roomService.getListRoom(form);
        if (lstRoom.isEmpty()){
            return new Response(Constants.RESPONSE_TYPE.SUCCESS,Constants.RESPONSE_CODE.SUCCESS,Constants.MESSAGE.ISEMPTY);
        }
        return new Response(Constants.RESPONSE_TYPE.SUCCESS,Constants.RESPONSE_CODE.SUCCESS).withData(lstRoom);
    }
    @PostMapping("/create-room")
    public Response createRoom(@RequestBody RoomForm roomForm) {
        boolean flag = roomService.createRoom(roomForm);
        if (flag) {
            return new Response(Constants.RESPONSE_TYPE.SUCCESS, Constants.RESPONSE_CODE.SUCCESS);
        }
        return new Response(Constants.RESPONSE_TYPE.ERROR, Constants.RESPONSE_CODE.ERROR);
    }
    @DeleteMapping("/delete-room")
    public Response deleteRoom(@RequestBody RoomForm roomForm) {
        boolean flag = roomService.deleteRoom(roomForm.getGuid());
        if (flag) {
            return new Response(Constants.RESPONSE_TYPE.SUCCESS, Constants.RESPONSE_CODE.SUCCESS);
        }
        return new Response(Constants.RESPONSE_TYPE.ERROR, Constants.RESPONSE_CODE.ERROR);
    }

//    public
}
