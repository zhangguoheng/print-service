package com.example.printservice.service;

import com.alibaba.fastjson.JSONObject;

public interface PrintService {

    String print(JSONObject body);
}
