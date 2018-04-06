package com.geedys.geedysite;

import com.geedys.geedysite.mapper.PlatformUserTabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class HelloController {
    @Autowired
    PlatformUserTabMapper mapper;

    @RequestMapping("/hello")
    public Object hello() {
        Map<String, Object> r = new HashMap<>();
        r.put("res", null);
        mapper.login("SHUYUAN", "", r);
        return mapper.selectByIds("'SHUYUAN'");
    }
}
