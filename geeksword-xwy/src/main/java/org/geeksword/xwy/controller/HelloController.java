package org.geeksword.xwy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Author xwy
 * @date 2018/9/18下午6:52
 */
@RestController
public class HelloController {


    //@RequestMapping
    @RequestMapping(value = {"/hello","hi"},method = RequestMethod.GET)
    public String index(){

        return "welcome to xwy springboot demo";
    }
}
