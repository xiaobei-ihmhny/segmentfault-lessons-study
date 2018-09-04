package com.segmentfault.springbootlesson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;
import java.util.HashMap;
import java.util.Map;

@Controller
@SpringBootApplication
public class SpringBootLessonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLessonApplication.class, args);
    }


    @RequestMapping("/")
    @ResponseBody
    String home(){
        return "hello,world";
    }

    @RequestMapping("/rest")
    @ResponseBody
    public Map<String, Object> rest() {
        Map<String,Object> data = new HashMap<>();

        data.put("小屁屁", "真的是一个小屁屁");
        data.put("小屁惠", "是一个小屁惠吗？");
        return data;
    }
}
