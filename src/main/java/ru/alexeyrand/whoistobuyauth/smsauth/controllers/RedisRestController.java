package ru.alexeyrand.whoistobuyauth.smsauth.controllers;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/debug")
public class RedisRestController {

    private final StringRedisTemplate redisTemplate;

    public RedisRestController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/keys")
    public List<Map<String, Object>> getAllKeys() {
        Set<String> keys = redisTemplate.keys("*");
        List<Map<String, Object>> result = new ArrayList<>();

        for (String key : keys) {
            Map<String, Object> keyInfo = new HashMap<>();
            keyInfo.put("key", key);
            keyInfo.put("value", redisTemplate.opsForValue().get(key));
            keyInfo.put("ttl", redisTemplate.getExpire(key));
            result.add(keyInfo);
        }

        return result;
    }

    @GetMapping("/keys/{pattern}")
    public List<Map<String, Object>> getKeysByPattern(@PathVariable String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        List<Map<String, Object>> result = new ArrayList<>();

        for (String key : keys) {
            Map<String, Object> keyInfo = new HashMap<>();
            keyInfo.put("key", key);
            keyInfo.put("value", redisTemplate.opsForValue().get(key));
            keyInfo.put("ttl", redisTemplate.getExpire(key));
            result.add(keyInfo);
        }

        return result;
    }

    @GetMapping("/value/{key}")
    public Map<String, Object> getValue(@PathVariable String key) {
        Map<String, Object> result = new HashMap<>();
        result.put("key", key);
        result.put("value", redisTemplate.opsForValue().get(key));
        result.put("exists", redisTemplate.hasKey(key));
        result.put("ttl", redisTemplate.getExpire(key));
        return result;
    }
}