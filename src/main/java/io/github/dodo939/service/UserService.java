package io.github.dodo939.service;

import io.github.dodo939.pojo.User;

import java.util.Map;

public interface UserService {

    User findUserByUsername(String username);

    void register(String username, String md5Password);

    Boolean checkPassword(User user, String password);

    String generateToken(User user);

    Map<String, Object> parseToken(String token);
}
