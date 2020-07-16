package com.maoyan.cases;

import com.maoyan.config.TestConfig;
import com.maoyan.model.GetUserInfoCase;
import com.maoyan.model.User;
import com.maoyan.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoTest {
    @Test(dependsOnGroups = "loginTrue",description = "获取 userId 为1的用户信息")
    public void getUserInfo() throws IOException{
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfoCase",1);
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);

        // 请求得到的结果
        JSONArray resultJson = getJsonResult(getUserInfoCase);

        // 实际的结果
        User user = session.selectOne(getUserInfoCase.getExpected(),getUserInfoCase);
        List userlist = new ArrayList();
        userlist.add(user);
        JSONArray jsonArray = new JSONArray((userlist));
        JSONArray jsonArray1 = new JSONArray(resultJson.getString(0));

        // 验证结果是否相同
        Assert.assertEquals(jsonArray.toString(),jsonArray1.toString());

    }

    private JSONArray getJsonResult(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject param = new JSONObject();

        param.put("id",getUserInfoCase.getUserId());

        // 设置请求头信息
        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        // 设置 Cookies 信息
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        // 发送请求, 获得响应
        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);

        result = EntityUtils.toString(response.getEntity(),"utf-8");

        List resultList = Arrays.asList(result);
        JSONArray array = new JSONArray(resultList);

        return array;
    }
}
