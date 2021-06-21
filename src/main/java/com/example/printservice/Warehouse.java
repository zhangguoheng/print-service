package com.example.printservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "warehouse")
@Data
public class Warehouse {

    List<String> one;

    public JSONObject toJSON() {
        return new JSONObject().fluentPut("one", one);
    }
}
