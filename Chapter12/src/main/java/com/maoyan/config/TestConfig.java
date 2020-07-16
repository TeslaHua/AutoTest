package com.maoyan.config;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class TestConfig {

    public static String loginUrl;
    public static String updateUserInfoUrl;
    public static String getUserListUrl;
    public static String getUserInfoUrl;
    public static String addUserUrl;

    // DefaultHttpClient deprecated 替换
    public static DefaultHttpClient defaultHttpClient;

    public static CookieStore store;
}
