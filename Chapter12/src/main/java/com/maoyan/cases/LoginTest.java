package com.maoyan.cases;

import com.maoyan.config.TestConfig;
import com.maoyan.model.InterfaceName;
import com.maoyan.model.LoginCase;
import com.maoyan.utils.ConfigFile;
import com.maoyan.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {
    @BeforeTest(groups = "loginTrue", description = "测试准备工作，获取 HttpClient 对象")
    public void beforeTest(){

        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);


        TestConfig.defaultHttpClient = new DefaultHttpClient();
    }

    @Test(groups = "loginTrue", description = "用户登录成功接口测试")
    public void loginTrue() throws IOException{
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",1);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        // 发送请求
        String result = getResult(loginCase);

        // 验证结果
        Assert.assertEquals(loginCase.getExpected(),result);
    }

    @Test(groups = "loginFalse",description = "用户登录失败接口测试")
    public void loginFalse() throws IOException{
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        // 发送请求
        String result = getResult(loginCase);

        // 验证结果
        Assert.assertEquals(loginCase.getExpected(),result);
    }

    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.loginUrl);
        JSONObject param = new JSONObject();

        param.put("userName",loginCase.getUserName());
        param.put("password",loginCase.getPassword());

        // 设置头信息
        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        String result;

        // 发送请求，并获得响应
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);

        // 得到返回的 Cookies 数据
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();

        return result;
    }
}
