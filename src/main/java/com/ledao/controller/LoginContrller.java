package com.ledao.controller;

import com.ledao.constant.Constant;
import com.ledao.entity.User;
import com.ledao.entity.vo.UserVo;
import com.ledao.service.UserService;
import com.ledao.util.Md5Util;
import com.ledao.util.RedisUtil;
import com.ledao.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author LeDao
 * @company
 * @create 2022-03-29 16:50
 */
@RestController
@RequestMapping("/user")
public class LoginContrller {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录
     *
     * @param userVo
     * @return
     */
    @RequestMapping("/login")
    public Map<String, Object> login(UserVo userVo, TimeUnit timeUnit) {
        Map<String, Object> resultMap = new HashMap<>(16);
        User user = userService.findByUserName(userVo.getUserName());
        if (user != null && user.getPassword().equals(Md5Util.backMd5(userVo.getPassword()))) {
            String token = Constant.REDIS_TOKEN_PREFIX + UUIDUtil.genUuid();
            RedisUtil.setKey(token, RedisUtil.entityToJson(user));
            RedisUtil.setKeyTime(token, (long) (30 * 60));
            resultMap.put("user", user);
            resultMap.put("token", token);
            return resultMap;
        }
        return null;
    }

    /**
     * 用户注册时检验用户名是否已经存在于数据库中
     *
     * @param userVo
     * @return
     */
    @RequestMapping("/checkUserName")
    public boolean checkUserName(UserVo userVo) {
        User user = userService.findByUserName(userVo.getUserName());
        return user == null;
    }

    /**
     * 用户注册时检验用户名是否已经存在于数据库中
     *
     * @param userVo
     * @return
     */
    @RequestMapping("/checkPhone")
    public boolean checkPhone(UserVo userVo) {
        User user = userService.findByPhone(userVo.getPhone());
        return user == null;
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @RequestMapping("/register")
    public boolean register(User user) {
        user.setRegisterDate(new Date());
        user.setPassword(Md5Util.backMd5(user.getPassword()));
        int key = userService.add(user);
        return key > 0;
    }

    /**
     * 注销
     *
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public boolean logout(HttpServletRequest request) {
        //从前端获取token
        String token = request.getHeader("token");
        return RedisUtil.delKey(token);
    }
}
