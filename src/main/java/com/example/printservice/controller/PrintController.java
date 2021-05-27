package com.example.printservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.printservice.service.PrintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PrintController {

    @Autowired
    private PrintService printService;

    @PostMapping(value = "print")
    private String print(@RequestBody String body) {
        log.info("pint:"+body);
        JSONObject jsonObject = JSONObject.parseObject(body);

        return printService.print(jsonObject);
    }
    @GetMapping(value = "test")
    private String test() {
        String string = "{\"name\":\"我\",\"code\":\"123\",\"no\":\"321\",\"mealType\":\"午餐\",\"type\":\"客户\"}";

        return printService.print(JSONObject.parseObject(string));
    }

}
