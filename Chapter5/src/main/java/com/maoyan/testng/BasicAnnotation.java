package com.maoyan.testng;

import org.testng.annotations.*;

//测试中用到的基本注解
public class BasicAnnotation {

    //最基本的注解：用来将方法声明为测试的一部分
    @Test
    public void testCase1(){
        System.out.println("Test1这是测试用例1");
    }

    @Test
    public void testCase2(){
        System.out.println("Test2这是测试用例2");
    }

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("beforeMethod这是在测试方法前执行的！");
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("afterMethod这是在测试方法后执行的！");
    }

    @BeforeClass
    public void beforeClass(){
        System.out.println("beforeClass这是在类前执行的！");
    }

    @AfterClass
    public void afterClass(){
        System.out.println("afterClass这是在类后执行的！");
    }

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("beforeSuite测试套件之前运行！");
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("afterSuite测试套件之后运行！");
    }
}
