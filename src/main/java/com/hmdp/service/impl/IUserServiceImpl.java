package com.hmdp.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.controller.UserController;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import com.hmdp.mapper.UserMapper;
import com.hmdp.service.IUserService;
import com.hmdp.utils.UserHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Override
    public Result sendCode(String phone, HttpSession session) {

        boolean isPhone = Validator.isMobile(phone);

        if (!isPhone) {
            return Result.fail("手机号格式错误");
        }
        String code = RandomUtil.randomNumbers(6);

        session.setAttribute("code", code);

//        TODO 发送验证码

        logger.info("验证码是: {}", code);

        return Result.ok();
    }

    @Override
    public Result login(LoginFormDTO loginFormDTO, HttpSession session) {

        String phone = loginFormDTO.getPhone();
        String code = loginFormDTO.getCode();

        Object cacheCode = session.getAttribute("code");
        //验证码是否一直
        if (cacheCode == null || !cacheCode.toString().equals(code)) {
            return Result.fail("验证码错误");
        }
        //查询数据用户是否存在
        User user = query().eq("phone", phone).one();

        if (user == null) {
            user = createUserWithPhone(phone);
        }

        session.setAttribute("user", BeanUtil.copyProperties(user, UserDTO.class));

        return Result.ok();
    }

    private User createUserWithPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setNickName("用户昵称" + RandomUtil.randomString(10));
        save(user);
        return user;
    }
}
