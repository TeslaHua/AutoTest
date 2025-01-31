package com.maoyan.httpclient.cookies;

import javafx.beans.property.ReadOnlySetProperty;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {

    private String url;
    private ResourceBundle bundle;
    //用于存储cookies信息的变量
    private CookieStore store;

    @BeforeTest
    public void beforeTest(){
        //application 对应于resources 下的配置文件
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    @Test
    public void testGetCookies() throws IOException{
        String result;
        //从配置文件中拼接测试的url
        String uri = bundle.getString("get.cookies.uri");
        String testUrl = this.url+uri;

        //发送Http请求
        HttpGet httpGet = new HttpGet(testUrl);
        //HttpClient httpClient = new DefaultHttpClient();
        DefaultHttpClient httpClient = new DefaultHttpClient();   //用于获取cookies信息
        HttpResponse response = httpClient.execute(httpGet);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        //获取cookies信息
        this.store = httpClient.getCookieStore();
        List<Cookie> cookieList = store.getCookies();
        for (Cookie cookie: cookieList){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie name = "+name+"; cookie value = "+value);
        }

    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testGetWithCookies() throws IOException {
        String uri = bundle.getString("get.with.cookies.uri");
        String testUrl = this.url+uri;

        HttpGet get = new HttpGet(testUrl);
        DefaultHttpClient client = new DefaultHttpClient();

        //设置cookies信息: 上面的testGetCookies获取的cookies作为此次请求的cookies
        client.setCookieStore(this.store);
        HttpResponse httpResponse = client.execute(get);

        //获取响应的状态码
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("statusCode = "+statusCode);
        if (statusCode == 200){
            String result = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(result);
        }
    }
}
