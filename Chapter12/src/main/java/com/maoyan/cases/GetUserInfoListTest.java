package com.maoyan.cases;

import com.maoyan.config.TestConfig;
import com.maoyan.model.GetUserListCase;
import com.maoyan.model.User;
import com.maoyan.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.CORBA.ULongSeqHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetUserInfoListTest {

    @Test(dependsOnGroups = "loginTrue", description = "获取性别为男的用户")
    public void getUserListInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = session.selectOne("getUserListCase",1);
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);

        // 发送请求获取结果
        JSONArray resultJson = getJsonResult(getUserListCase);

        // 验证返回结果
        List<User> userList = session.selectList(getUserListCase.getExpected(),getUserListCase);
        for(User u:userList){
            System.out.println("获取的user: "+u.toString());
        }
        JSONArray userListJson = new JSONArray(userList);
        // 先验证它们的长度是否相同
        Assert.assertEquals(userListJson.length(),resultJson.length());

        // 挨个验证值是否相同
        for(int i=0;i<resultJson.length();i++){
            JSONObject expect = (JSONObject) resultJson.get(i);
            JSONObject actual = (JSONObject) userListJson.get(i);
            Assert.assertEquals(expect.toString(),actual.toString());
        }
    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserListUrl);
        JSONObject param = new JSONObject();
        param.put("userName",getUserListCase.getUserName());
        param.put("sex",getUserListCase.getSex());
        param.put("age",getUserListCase.getAge());

        // 设置头信息
        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        // 设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        String result;

        // 发送请求
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);

        result = EntityUtils.toString(response.getEntity(),"utf-8");

        System.out.println(result);
        JSONArray jsonArray = new JSONArray();

        return jsonArray;

    }
}
