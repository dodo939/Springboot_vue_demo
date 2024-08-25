package io.github.dodo939.service.impl;

import io.github.dodo939.mapper.UserMapper;
import io.github.dodo939.pojo.User;
import io.github.dodo939.service.UserService;
import io.github.dodo939.utils.JwtUtil;
import io.github.dodo939.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public void register(String username, String password) {
        String md5Password = MD5Util.getMD5(password);
        userMapper.add(username, md5Password);
    }

    @Override
    public Boolean checkPassword(User user, String password) {
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
    public Map<String, Object> parseToken(String token) {
        return JwtUtil.parseToken(token);
    }
}
