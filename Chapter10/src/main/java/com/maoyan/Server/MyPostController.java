package com.maoyan.Server;


import com.maoyan.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/", description = "这是全部的POST请求")
@RequestMapping("/v1")
public class MyPostController {

    //用于装Cookies信息的变量
    private static Cookie cookie;

    //模拟用户登录成功获取到Cookies, 然后再访问其他接口获取列表
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "登录接口，成功后获取Cookies信息",httpMethod = "POST")
    public String login(HttpServletResponse response,
                        @RequestParam(value = "userName",required = true) String username,
                        @RequestParam(value = "password",required = true) String password){
        if (username.equals("jack") && password.equals("123456")){
            cookie = new Cookie("Login","true");
            response.addCookie(cookie);
            return "恭喜你登录成功了！";
        }
        return "用户名或密码错误！";
    }

    // (在Jmeter上添加Cookies信息验证)

    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表", httpMethod = "POST")
    public String getUserList(HttpServletRequest request, @RequestBody User u){

        User user;
        // 获取 cookies
        Cookie[] cookies = request.getCookies();

        // 验证 cookies 是否合法
        for (Cookie c: cookies){
            if (c.getName().equals("login")
                    && c.getValue().equals("true")
                    && u.getUserName().equals("jack")
                    && u.getPassword().equals("123456")
                ){
                user = new User();
                user.setName("rose");
                user.setAge("18");
                user.setSex("male");
                return user.toString();
            }
        }
        return "参数不合法";
    }


}
