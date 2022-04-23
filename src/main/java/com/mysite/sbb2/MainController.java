package com.mysite.sbb2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @RequestMapping("/sbb")
    @ResponseBody
    public String index(){
        return "안녕하세요 sbb에 오신것을 환영 합니다.";
    }

    @RequestMapping("/")
    public String root() {
        return "redirect:/question/list";
    }

}
