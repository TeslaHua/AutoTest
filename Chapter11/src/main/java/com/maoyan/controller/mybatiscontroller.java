package com.maoyan.controller;


import com.maoyan.mybatis.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j
@RestController
@Api(value = "v1",description = "这是第一个版本的demo")
@RequestMapping("v1")
public class mybatiscontroller {

    @Autowired
    private SqlSessionTemplate template;

    @RequestMapping(value = "/getUserCount",method = RequestMethod.GET)
    @ApiOperation(value = "可以获取到的用户数",httpMethod = "GET")
    public int getUserCount(){
        //getUserCount对应mysql.xml中的配置
        return template.selectOne("getUserCount");
    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    @ApiOperation(value = "插入一条用户数据",httpMethod = "POST")
    public int addUser(@RequestBody User user){
        int result =  template.insert("addUser",user);
        return result;
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ApiOperation(value = "更改一条用户数据",httpMethod = "POST")
    public int updateUser(@RequestBody User user){
        return template.update("updateUser",user);
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    @ApiOperation(value = "删除一条用户数据", httpMethod = "POST")
    public int deteleUser(@RequestParam int id){
        return template.delete("deleteUser",id);
    }
}
