package io.github.dodo939.interceptor;

import io.github.dodo939.utils.JwtUtil;
import io.github.dodo939.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        String token = request.getHeader("Authorization");
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            String key = "big-event:tokens:" + claims.get("id") + ":" + token;
            if (redisTemplate.opsForValue().get(key) == null) {
                System.out.println(key);
                response.setStatus(401);
                return false;
            } else {
                // 如果验证通过，则更新 token 的过期时间
                redisTemplate.expire(key, 24, TimeUnit.HOURS);
            }
            ThreadLocalUtil.setClaim(claims);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        ThreadLocalUtil.remove();
    }
}
