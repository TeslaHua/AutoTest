package com.maoyan.testng.parameter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProviderTest {

    //1、形式1
    @Test(dataProvider = "data")
    public void testDataProvider(String name, int age){
        System.out.println("name = "+ name + "; age = "+age);
    }

    @DataProvider(name = "data")  //和上面的dataProvider属性要对应
    public Object[][] providerData(){

        //对象o里的每组数据都会被测试一次
        Object[][] o = new Object[][]{
                {"zhangsan", 10},
                {"lisi",20},
                {"wangwu",30}
        };
        return o;
    }

    //2、形式2
    @Test(dataProvider = "methodData")
    public void test1(String name, int age){
        System.out.println("test1方法 name = "+ name + "; age = "+age);
    }

    @Test(dataProvider = "methodData")
    public void test2(String name, int age){
        System.out.println("test2方法 name = "+ name + "; age = "+age);
    }


    @DataProvider(name = "methodData")
    public Object[][] methodDataTest(Method method){
        Object[][] result = null;

        if(method.getName().equals("test1")){
            result = new Object[][]{
                    {"zhangsan",10},
                    {"lisi",20}
            };
        }else if(method.getName().equals("test2")){
            result = new Object[][]{
                    {"wangwu",30},
                    {"zhaoliu",40}
            };
        }
        return result;
    }
}
