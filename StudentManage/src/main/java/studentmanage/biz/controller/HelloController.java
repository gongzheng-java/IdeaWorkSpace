package studentmanage.biz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gongzheng on 2019-07-21.
 */
@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("hello","张三");
        return "layout/hello";
    }

    @RequestMapping("/index")
    public String index(){
        return "layout/index";
    }

    @RequestMapping("/layout")
    public String layout(Model model){
        model.addAttribute("title","这是标题！");
        return "layout/layout";
    }

    @RequestMapping("/home")
    public String home(Model model){
        model.addAttribute("title","这是标题！");
        return "layout/home";
    }



}
