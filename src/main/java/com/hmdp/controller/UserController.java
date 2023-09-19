package com.hmdp.controller;


import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import com.hmdp.service.IUserService;
import com.hmdp.utils.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */

@Api(value = "用户模块Controller", tags = { "用户访问接口" })
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private IUserService userService;

    @ApiOperation(value = "根据用户手机号发送验证码")
    @PostMapping("/code")
    public Result sendCode(@RequestParam("phone") String phone , HttpSession session){

        Result result = userService.sendCode(phone, session);

        return result;
    }


    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginFormDTO, HttpSession session){

        return userService.login(loginFormDTO, session);
    }

    @ApiOperation(value = "用户登录信息状态返回")
    @GetMapping("/me")
    public Result me() {
        //获取当前登录用户的状态并返回
        UserDTO user = UserHolder.getUser();
        log.debug("用户信息{}", user);
        return Result.ok(user);
    }


}



























