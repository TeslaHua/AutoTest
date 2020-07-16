package com.maoyan.Server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@Api(value = "/", description = "这是全部的 GET 请求方法")
public class MyGetController {


    //GET 请求 Cookies 信息
    @RequestMapping(value = "/getCookies",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "这个GET请求可以获取到Cookies", httpMethod = "GET")
    public String getCookies(HttpServletResponse response){
        //HttpServerletRequest 装请求信息的类
        //HttpServerletResponse 装响应信息的类
        Cookie cookie = new Cookie("Login","true");
        response.addCookie(cookie);
        return "恭喜你获得GET请求的Cookies信息成功";
    }

    //GET 请求携带 Cookies 信息  (在Jmeter上添加Cookies信息验证)
    @RequestMapping(value = "/get/with/cookies",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "这个GET请求需要客户端携带Cookies访问", httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(Objects.isNull(cookies)){
            return "你必须携带Cookies 访问";
        }
        for (Cookie cookie: cookies){
            if(cookie.getName().equals("Login")
                    && cookie.getValue().equals("true")){
                return "恭喜你携带Cookies信息访问成功！";
            }
        }
        return "你必须携带正确的Cookies信息访问";
    }


    /* 开发一个需要携带参数才能访问的 GET 请求
     * 第一种实现方式：url: key=value&key=value
     * 以下模拟获取商品列表
     */

    @RequestMapping(value = "/get/with/param", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "需要携带参数才能访问的 GET 请求1",httpMethod = "GET")
    public Map<String,Integer> getList(@RequestParam Integer start,
                                       @RequestParam Integer end){
        Map<String,Integer> myList = new HashMap<>();
        myList.put("鞋",400);
        myList.put("干脆面",1);
        myList.put("衬衫",300);

        return myList;
    }



    /* 开发一个需要携带参数才能访问的 GET 请求
     * 第二种实现方式：url: ip:port/get/with/param/10/20
     * 以下模拟获取商品列表
     */
    @RequestMapping(value = "/get/with/param/{start}/{end}")
    @ResponseBody
    @ApiOperation(value = "需要携带参数才能访问的 GET 请求2",httpMethod = "GET")
    public Map<String,Integer> myGetList(@PathVariable Integer start,
                                         @PathVariable Integer end){
        Map<String,Integer> myList = new HashMap<>();
        myList.put("鞋",400);
        myList.put("干脆面",1);
        myList.put("衬衫",300);

        return myList;
    }

}
