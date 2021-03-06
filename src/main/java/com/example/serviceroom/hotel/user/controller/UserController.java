package com.example.serviceroom.hotel.user.controller;

import com.example.serviceroom.common.Constants;
import com.example.serviceroom.common.Response;
import com.example.serviceroom.config.jwt.JwtUtils;
import com.example.serviceroom.hotel.user.bean.UserBean;
import com.example.serviceroom.hotel.user.bo.UserBO;
import com.example.serviceroom.hotel.user.form.UserForm;
import com.example.serviceroom.hotel.user.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/user")
public class UserController {
    private static final Logger log = LogManager.getLogger(UserController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @PostMapping("/saveOrUpdate")
    public Response saveOrUpdate(@RequestBody UserForm userForm) {
        boolean flag = userService.createdUser(userForm);
        if (flag) {
            return new Response(Constants.RESPONSE_TYPE.SUCCESS, Constants.RESPONSE_CODE.SUCCESS);
        }
        return new Response(Constants.RESPONSE_TYPE.ERROR, Constants.RESPONSE_CODE.ERROR);
    }

    @PostMapping("/authenticate")
    public @ResponseBody
    Response authentication(@RequestBody UserForm userForm) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userForm.getUsername(), userForm.getPassword())
            );
        } catch (AuthenticationException e) {
            return Response.warning(Constants.RESPONSE_CODE.WARNING, Constants.MESSAGE.LOGIN_FAIL);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(userForm.getUsername());
        HashMap<String, Object> res = new HashMap<>();
        UserBean bean = userService.findUserByUsername(userForm.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        res.put(Constants.KEY_USER, bean);
        res.put(Constants.KEY_TOKEN, jwt);
        return Response.success(Constants.RESPONSE_CODE.SUCCESS).withData(res);
    }
}
