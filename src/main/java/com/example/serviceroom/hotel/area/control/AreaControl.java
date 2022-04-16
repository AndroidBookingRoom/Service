package com.example.serviceroom.hotel.area.control;

import com.example.serviceroom.common.Constants;
import com.example.serviceroom.common.Response;
import com.example.serviceroom.hotel.area.form.AreaForm;
import com.example.serviceroom.hotel.area.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/area")
public class AreaControl {
    @Autowired
    private AreaService areaService;

    @GetMapping("/getListArea")
    public @ResponseBody
    Response getListArea() {
        return new Response().withData(areaService.getListArea());
    }

    @PostMapping("/saveOrUpdate")
    public @ResponseBody
    Response saveOrUpdate(@RequestBody AreaForm areaForm) {
        boolean flag = areaService.saveOrUpdate(areaForm);
        if (flag){
            return new Response(Constants.RESPONSE_TYPE.SUCCESS, Constants.RESPONSE_CODE.SUCCESS);
        }
        return new Response(Constants.RESPONSE_TYPE.ERROR);
    }
}
