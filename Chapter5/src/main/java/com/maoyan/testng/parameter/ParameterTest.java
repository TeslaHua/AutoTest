package com.maoyan.testng.parameter;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

//使用xml文件参数化
public class ParameterTest {

    @Test
    @Parameters({"name","age"})   //表示要接收两个参数进来，参数来自对应配置文件
    public void parameterTest1(String name, int age){
        System.out.println("name = "+name+" : "+"age = "+age);
    }
}
