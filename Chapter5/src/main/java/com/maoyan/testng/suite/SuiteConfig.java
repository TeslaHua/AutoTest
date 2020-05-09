package com.maoyan.testng.suite;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

//通常包含测试套件中所有的公有方法
public class SuiteConfig {

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("Before Suite 运行了！");
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("After Suite 运行了！");
    }

    //在每一个测试方法前执行
    @BeforeTest
    public void beforeTest(){
        System.out.println("before Test 运行了！");
    }

    //在每一个测试方法后执行
    @AfterTest
    public void afterTest(){
        System.out.println("after Test 运行了！");
    }
}
