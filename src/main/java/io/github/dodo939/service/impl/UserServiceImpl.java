package io.github.dodo939.service.impl;

import io.github.dodo939.mapper.UserMapper;
import io.github.dodo939.pojo.Result;
import io.github.dodo939.pojo.User;
import io.github.dodo939.service.UserService;
import io.github.dodo939.utils.JwtUtil;
import io.github.dodo939.utils.MD5Util;
import io.github.dodo939.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public void register(String username, String password) {
        String md5Password = MD5Util.getMD5(password);
        userMapper.addUser(username, md5Password);
    }

    @Override
    public Boolean checkPassword(User user, String password) {
        return user.getPassword().equals(MD5Util.getMD5(password));
    }

    @Override
    public Boolean checkPassword(String password) {
        User user = getCurrentUser();
        return user.getPassword().equals(MD5Util.getMD5(password));
    }

    @Override
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        return JwtUtil.genToken(claims);
    }

    @Override
    public User getCurrentUser() {
        Map<String, Object> claims = ThreadLocalUtil.getClaim();
        String username = (String) claims.get("username");
        /*
         * User 中的 createTime 和 updateTime 字段在数据库中为下划线命名 create_time 和 update_time
         * updateTime, createTime 和 update_time, create_time 无法对应，因此会缺少这两个字段
         * 所以在 application.properties 中配置了驼峰命名自动映射
         * mybatis.configuration.map-underscore-to-camel-case=true
         */
        return userMapper.getUserByUsername(username);
    }

    @Override
    public Integer getCurrentUserId() {
        Map<String, Object> claims = ThreadLocalUtil.getClaim();
        return (Integer) claims.get("id");
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Integer Id = getCurrentUserId();
        userMapper.updateAvatar(Id, avatarUrl);
    }

    @Override
    public Result<Void> updatePassword(Map<String, String> params) {
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("参数不能为空");
        }
        if (!rePwd.equals(newPwd)) {
            return Result.error("两次密码输入不一致");
        } else if (newPwd.equals(oldPwd)) {
            return Result.error("新密码不能与旧密码相同");
        }
        if (!java.util.regex.Pattern.matches("^[a-zA-Z0-9_]{5,16}$", newPwd)) {
            return Result.error("密码格式错误");
        }
        if (checkPassword(oldPwd)) {
            Integer Id = getCurrentUserId();
            String md5Password = MD5Util.getMD5(newPwd);
            userMapper.updatePassword(Id, md5Password);
            return Result.success();
        } else {
            return Result.error("旧密码错误");
        }
    }
}
