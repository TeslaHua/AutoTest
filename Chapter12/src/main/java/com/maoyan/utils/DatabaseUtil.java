package com.maoyan.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.io.IOException;
import java.io.Reader;

public class DatabaseUtil {

    public static SqlSession getSqlSession() throws IOException {
        // 获取配置的资源文件
        Reader reader = Resources.getResourceAsReader("databaseConfig.xml");

        // 使用类加载器加载配置文件
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);

        // 返回SqlSession
        // 这个sqlSession 就是能够执行配置文件种的SQL语句了
        SqlSession sqlSession = factory.openSession();

        return sqlSession;
    }
}
