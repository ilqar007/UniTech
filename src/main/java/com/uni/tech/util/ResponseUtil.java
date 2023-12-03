package com.uni.tech.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    public  static <V> Map<String, V> createResponse(V value)
    {
        Map<String, V> response =  new HashMap<>();
        response.put("msg", value);
        return response;
    }
}
