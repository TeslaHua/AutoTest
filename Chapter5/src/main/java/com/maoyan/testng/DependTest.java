package com.maoyan.testng;

import org.testng.annotations.Test;

public class DependTest {

    @Test
    public void test1(){
        System.out.println("test1");
        throw new RuntimeException();
    }

    //test2的执行依赖于test1
    //当test1执行异常时，test2被忽略
    @Test(dependsOnMethods = {"test1"})
    public void test2(){
        System.out.println("test2");
    }
}
