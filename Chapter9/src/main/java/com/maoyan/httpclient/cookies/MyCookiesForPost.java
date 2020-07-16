package com.maoyan.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForPost {

    private String url;
    private ResourceBundle resourceBundle;
    //存储cookies
    private CookieStore store;

    //拼接请求链接
    @BeforeTest
    public void beforeTest(){
        resourceBundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = resourceBundle.getString("test.url");
    }

    @Test
    public void testGetCookies() throws IOException {

        String result;
        //拼接请求
        String uri = resourceBundle.getString("get.cookies.uri");
        String testUrl = url+uri;

        //执行Http请求并获取响应结果
        HttpGet httpGet = new HttpGet(testUrl);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(httpGet);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        //获取响应的cookies信息
        this.store = httpClient.getCookieStore();
        List<Cookie> cookieList = store.getCookies();
        for (Cookie cookie : cookieList){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie name = "+name+"; cookie value = "+value);
        }

    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testPostMethod() throws IOException{

        //1. 拼接最终的测试地址
        String uri = resourceBundle.getString("post.with.cookies.uri");
        String testUrl = url+uri;

        //2. 声明一个 Post 方法
        HttpPost post = new HttpPost(testUrl);

        //3. 声明一个 client 对象，用于方法的执行
        DefaultHttpClient httpClient = new DefaultHttpClient();

        //4. 添加请求头参数
        JSONObject param = new JSONObject();
        param.put("name","Jack");
        param.put("age","18");

        //5. 设置请求头
        post.setHeader("content-type","application/json");

        //6. 将参数信息添加到请求方法中
        StringEntity entity = new StringEntity(param.toString(),"UTF-8");
        post.setEntity(entity);

        //7. 设置 cookies 信息
        httpClient.setCookieStore(this.store);

        //8. 执行Post方法
        HttpResponse response = httpClient.execute(post);

        //9. 获取响应结果并输出
        String result = EntityUtils.toString(response.getEntity(),"UTF-8");
        System.out.println(result);


        //10. 处理响应结果，判断结果是否符合预期
        JSONObject resultJson = new JSONObject(result);

        //11. 获取到结果值
        String success = (String) resultJson.get("jack");
        String status = (String) resultJson.get("status");

        //12. 断言结果值是否符合预期
        Assert.assertEquals("success",success);
        Assert.assertEquals("1",status);


    }
}
