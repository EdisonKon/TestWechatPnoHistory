package com.test;

import org.junit.Assert;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author : kaifa
 * @Description :
 * @Date : Create in 11:27 2018/2/7
 * @modified : kaifa
 */
public class GetRedirectUrlTest {
    @Test
    public void test_getRedirectUrl() throws Exception {
        String url="https://mp.weixin.qq.com/";
//        String expectUrl="http://www.zhihu.com/question/20583607/answer/16597802";
        String redictURL = getRedirectUrl(url);
        System.out.println(redictURL);
//        Assert.assertEquals(expectUrl, redictURL);
    }

    /**
     * 获取重定向地址
     * @param path
     * @return
     * @throws Exception
     */
    private String getRedirectUrl(String path) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(path)
                .openConnection();
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(5000);
        return conn.getHeaderField("Location");
    }

}
