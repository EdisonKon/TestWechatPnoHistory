package com.test;
/**
HttpClient4.x可以自带维持会话功能，只要使用同一个HttpClient且未关闭连接，则可以使用相同会话来访问其他要求登录验证的服务（见TestLogin()方法中的“执行get请求”部分）。
        如果需要使用HttpClient池，并且想要做到一次登录的会话供多个HttpClient连接使用，就需要自己保存会话信息。因为客户端的会话信息是保存在cookie中的（JSESSIONID），所以只需要将登录成功返回的cookie复制到各个HttpClient使用即可。
        使用Cookie的方法有两种，可以自己使用CookieStore来保存（见TestCookieStore()方法），也可以通过HttpClientContext上下文来维持（见TestContext()方法）。
        附带HttpClient4.3示例代码 http://www.myexception.cn/program/1459749.html 。
*/

        import java.io.File;
        import java.io.IOException;
        import java.math.BigDecimal;
        import java.text.NumberFormat;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Iterator;
        import java.util.List;
        import java.util.Map;
        import java.util.Map.Entry;

        import com.alibaba.fastjson.JSON;
        import com.alibaba.fastjson.JSONArray;
        import com.alibaba.fastjson.JSONObject;
        import org.apache.commons.io.FileUtils;
        import org.apache.http.HeaderIterator;
        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.NameValuePair;
        import org.apache.http.ParseException;
        import org.apache.http.client.CookieStore;
        import org.apache.http.client.config.CookieSpecs;
        import org.apache.http.client.entity.UrlEncodedFormEntity;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.client.protocol.HttpClientContext;
        import org.apache.http.config.Registry;
        import org.apache.http.config.RegistryBuilder;
        import org.apache.http.cookie.CookieSpecProvider;
        import org.apache.http.impl.client.BasicCookieStore;
        import org.apache.http.impl.client.CloseableHttpClient;
        import org.apache.http.impl.client.HttpClients;
        import org.apache.http.impl.cookie.BasicClientCookie;
        import org.apache.http.impl.cookie.BestMatchSpecFactory;
        import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
        import org.apache.http.message.BasicNameValuePair;
        import org.apache.http.util.EntityUtils;
        import org.junit.Test;

public class TestCookieClient {

    // 创建CookieStore实例
    static CookieStore cookieStore = null;

    static HttpClientContext context = null;
    String loginUrl = "https://mp.weixin.qq.com/cgi-bin/bizlogin?action=startlogin";
    String doLoginReferUrl = "https://mp.weixin.qq.com/cgi-bin/bizlogin?action=validate&lang=zh_CN&account=huainian_cl@126.com";
    String testUrl = "https://mp.weixin.qq.com/cgi-bin/loginqrcode?action=getqrcode&param=4300&rd=120";
    String checkLogin = "https://mp.weixin.qq.com/cgi-bin/loginqrcode?action=ask&f=json&ajax=1&random=";
    String doLogin = "https://mp.weixin.qq.com/cgi-bin/bizlogin?action=login";
    String searchUrl = "https://mp.weixin.qq.com/cgi-bin/searchbiz?action=search_biz&needToken&lang=zh_CN&f=json&ajax=1&needRandom&needQuery&begin=0&count=5";
//    String appmsg = "https://mp.weixin.qq.com/cgi-bin/appmsg?token=96274954&lang=zh_CN&f=json&ajax=1&random=0.19968593595274553&action=list_ex&begin=0&count=5&query=&fakeid=MjM5MjAxNDM4MA==&type=9";
    String appmsg = "https://mp.weixin.qq.com/cgi-bin/appmsg?needToken&lang=zh_CN&f=json&ajax=1&needRandom&action=list_ex&needBegin&needCount&query=&needFakeid&type=9";//原9,改为1后条数变多
    /**
     * needToken
     * needRandom
     * needBegin
     * needCount
     * needFakeid
     * */
    @Test
    public void testLogin() throws Exception {
        String publicNo = "girlnba";
        boolean isSpecial = false;
        String returnStr = "";
        String filepath = "D://kk.jpg";
        String Host = "mp.weixin.qq.com";
        String agent  = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 MicroMessenger/6.5.2.501 NetType/WIFI WindowsWechat QBCore/3.43.691.400 QQBrowser/9.0.2524.400";
        String refer = "https://mp.weixin.qq.com";
        String xrw = "XMLHttpRequest";

        System.out.println("----testLogin");

        // // 创建HttpClientBuilder
        // HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // // HttpClient
        // CloseableHttpClient client = httpClientBuilder.build();
        // 直接创建client
        cookieStore = new BasicCookieStore();
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        //CloseableHttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(loginUrl);
        Map parameterMap = new HashMap();
        Map<String,String> mapHeader = new  HashMap<String, String>();
        parameterMap.put("username", "xxx账号");
        parameterMap.put("pwd", "xxx密码 md5加密");
        parameterMap.put("imgcode", "");
        parameterMap.put("f", "json");

        mapHeader.put("User-Agent",agent);
        mapHeader.put("Referer","https://mp.weixin.qq.com/cgi-bin/bizlogin?action=validate&lang=zh_CN&account=huainian_cl@126.com");
        mapHeader.put("Host",Host);
        mapHeader.put("X-Requested-With",xrw);
        UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(getParam(parameterMap), "UTF-8");
        httpPost.setEntity(postEntity);
        for (Entry<String, String> b : mapHeader.entrySet()) {
            httpPost.setHeader(b.getKey(), b.getValue());
        }
        System.out.println("request line:" + httpPost.getRequestLine());

        try {
            // 执行post请求
            HttpResponse httpResponse = client.execute(httpPost);
            /*String location = httpResponse.getFirstHeader("Set-Cookie").getValue();
            if (location != null && location.startsWith(loginErrorUrl)) {
                System.out.println("----loginError");
            }*/
            printResponse(httpResponse,null);
            System.out.println("cookie store1:" + cookieStore.getCookies());
            // 执行get请求
            System.out.println("----the same client");
            HttpGet httpGet = new HttpGet(testUrl);
            System.out.println("request line:" + httpGet.getRequestLine());
            HttpResponse httpResponse1 = client.execute(httpGet);
//            printResponse(httpResponse1);
            File f = new File(filepath);
            //存文件二维码图片
            FileUtils.copyInputStreamToFile(httpResponse1.getEntity().getContent(),f);
            System.out.println("cookie store2:" + cookieStore.getCookies());
            /*
            //解析二维码成链接,并访问--只能通过客户端访问
            String qrurl = TestQR.decodeQR(filepath);
            HttpGet httpGetqr = new HttpGet(qrurl);
            System.out.println("request line:" + httpGetqr.getRequestLine());
            HttpResponse httpResponseqr = client.execute(httpGetqr);
            printResponse(httpResponseqr);
            */
            //轮训是不是点击[同意访问]
            boolean agree = false;
            while(!agree){
                System.out.println("请扫码并同意访问");
                HttpGet httpGet4Check = new HttpGet(checkLogin+MyRandom());
                System.out.println("request line:" + httpGet4Check.getRequestLine());
                HttpResponse httpResponseCl = client.execute(httpGet4Check);
                Map<String,String> kv = new HashMap<String, String>();
                kv.put("status","");
                kv = printResponse(httpResponseCl,kv);
                String value = kv.get("status");
                if("1".equals(value)){
                    agree = true;
                    System.out.println("cookie store3:" + cookieStore.getCookies());
                }else{
                    Thread.sleep(5000);
                }
            }
            //同意后访问登录首页获取token
            parameterMap.clear();
            parameterMap.put("userlang","zh_CN");
            parameterMap.put("token","");
            parameterMap.put("lang","zh_CN");
            parameterMap.put("f","json");
            parameterMap.put("ajax","1");

            mapHeader.put("User-Agent",agent);
            mapHeader.put("Referer",doLoginReferUrl);
            mapHeader.put("Host",Host);
            mapHeader.put("Accept-Encoding","gzip, deflate, br");
            mapHeader.put("X-Requested-With",xrw);
            mapHeader.put("Connection","keep-alive");
            mapHeader.put("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            mapHeader.put("Accept","*/*");

            HttpPost httpPostDoLogin = new HttpPost(doLogin);
            UrlEncodedFormEntity postEntitydoLogin = new UrlEncodedFormEntity(getParam(parameterMap), "UTF-8");
            httpPostDoLogin.setEntity(postEntitydoLogin);
            for (Entry<String, String> b : mapHeader.entrySet()) {
                httpPostDoLogin.setHeader(b.getKey(), b.getValue());
            }
            System.out.println("request line:" + httpPostDoLogin.getRequestLine());
            String token = "";
            int retry = 3;
            while(retry>0){
                HttpResponse httpResponseDologin = client.execute(httpPostDoLogin);
                Map<String,String> kv = new HashMap<String, String>();
                kv.put("redirect_url","");
                kv = printResponse(httpResponseDologin,kv);
                String redirecturl = kv.get("redirect_url");

                String[] containToken = redirecturl.split("token=");
                if(containToken.length<2){
                    retry--;
                }else{
                    retry = 0;
                    token = containToken[1].split("=")[0];
                }
            }
            System.out.println("cookie store4:" + cookieStore.getCookies());

            System.out.println("获取到的token : "+token);
            //接下来进行获取公众号信息,获取到fakeid
            searchUrl = searchUrl.replace("needToken","token="+token).replace("needRandom","random="+MyRandom()).replace("needQuery","query="+publicNo);
            HttpGet httpGet4publicNo = new HttpGet(searchUrl);
            System.out.println("request line:" + httpGet4publicNo.getRequestLine());
            HttpResponse httpResponsePcNo = client.execute(httpGet4publicNo);
            Map<String,String> kv = new HashMap<String, String>();
            kv.put("list","");
            kv = printResponse(httpResponsePcNo,kv);
            String value = kv.get("list");
            System.out.println(value);
            String fakeid = "";
            List<MyPuNoEntity> lme = JSON.parseArray(value,MyPuNoEntity.class);
            for (MyPuNoEntity me : lme) {
                System.out.println(me.getNickname());
                if(publicNo.equalsIgnoreCase(me.getAlias())){
                    fakeid = me.getFakeid();
                    System.out.println("获取到的fakeid : "+fakeid);
                    break;
                }
            }
            System.out.println("cookie store5:" + cookieStore.getCookies());
            if("".equals(fakeid)){
                System.out.println("未获取到fakeid,请检查是不是有该公众号");
            }
            //接下来进行获取公众号历史文章

            /**
             * 如果该公众号一天可以发很多的消息 比如人民日报/央视新闻
             * begin和count 无规律,猜测是按每天发送的次数去判断的比如今天发6 次,count=5 则返回前5 次的文章数据
             * 设置 isSpecial = true;
             * 如果特殊的话 只能获取数量并截取count大小去设置 比如begin = 0 count = 5 ,然后每次begin+5;
             *  需要替换这些值
             needToken
             needRandom
             needBegin 从今天算往前多少天
             needCount 一次获取多少天的数据,最大值是5 最多一次返回5天的数据
             needFakeid
             * */
            int begin = 0;
            int count = 5;
            appmsg = appmsg
                    .replace("needToken","token="+token)
                    .replace("needRandom","random="+MyRandom())
                    .replace("needFakeid","fakeid="+fakeid)
            ;
            //第一次先取一个总值
            String appmsgTemp = "";
            appmsgTemp = appmsg
                    .replace("needBegin","begin="+begin)
                    .replace("needCount","count="+count);
            //第一次先取一个总值
            HttpGet httpGet4msg = new HttpGet(appmsgTemp);
            System.out.println("request line:" + httpGet4msg.getRequestLine());
            HttpResponse httpResponsemsg = client.execute(httpGet4msg);
            kv = new HashMap<String, String>();
            kv.put("app_msg_list","");
            kv.put("app_msg_cnt","");
            kv = printResponse(httpResponsemsg,kv);
            String list = kv.get("app_msg_list");
            String totalCountStr = kv.get("app_msg_cnt");
            List<MyMsgEntity> lmex = JSON.parseArray(list,MyMsgEntity.class);
            for (MyMsgEntity me : lmex) {
                System.out.println(me.getTitle()+"\t"+me.getCover());
            }
            //从第二次开始循环取数据
            int total = Integer.parseInt(totalCountStr);
            int time = 0;
            for(begin=5;begin<total;begin+=count){
                time++;
                appmsgTemp = appmsg
                        .replace("needBegin","begin="+begin)
                        .replace("needCount","count="+count);
                HttpGet httpGet4msg2 = new HttpGet(appmsgTemp);
                System.out.println("request line:" + httpGet4msg2.getRequestLine());
                HttpResponse httpResponsemsg2 = client.execute(httpGet4msg2);
                kv = new HashMap<String, String>();
                kv.put("app_msg_list","");
                kv = printResponse(httpResponsemsg2,kv);
                String listx = kv.get("app_msg_list");
                List<MyMsgEntity> lmex2 = JSON.parseArray(listx,MyMsgEntity.class);
                for (MyMsgEntity me : lmex2) {
                    System.out.println(me.getTitle()+"\t"+me.getLink());
                }
                Thread.sleep(3000);
                if(time>=10){
                    System.out.println("已经请求10次,休息1分钟.");
                    Thread.sleep(60000);
                }
            }
            System.out.println("完成全部操作");
            // cookie store
//            setCookieStore(httpResponse);
            // context
//            setContext();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流并释放资源
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String,String> printResponse(HttpResponse httpResponse,Map<String,String> mapKv)throws ParseException, IOException {
        String value = "";
        // 获取响应消息实体
        HttpEntity entity = httpResponse.getEntity();
        // 响应状态
        System.out.println("status:" + httpResponse.getStatusLine());
        System.out.println("headers:");
        HeaderIterator iterator = httpResponse.headerIterator();
//        while (iterator.hasNext()) {
//            System.out.println("\t" + iterator.next());
//        }
        String content = "";
        // 判断响应实体是否为空
        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            System.out.println("response length:" + responseString.length());
            content = responseString.replace("\r\n", "");
            System.out.println("response content:"+ content);
        }
        if (mapKv != null) {
            for (Entry<String,String> kv : mapKv.entrySet()) {
                String key = kv.getKey();
                if (key != null && !"".equals(key)) {
                    try {
                        JSONObject obj = JSON.parseObject(content);
                        value = String.valueOf(obj.get(key));
                        kv.setValue(value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return mapKv;
    }


    @Test
    public void testContext() throws Exception {
        System.out.println("----testContext");
        // 使用context方式
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(testUrl);
        System.out.println("request line:" + httpGet.getRequestLine());
        try {
            // 执行get请求
            HttpResponse httpResponse = client.execute(httpGet, context);
            System.out.println("context cookies:"
                    + context.getCookieStore().getCookies());
            printResponse(httpResponse,null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流并释放资源
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testCookieStore() throws Exception {
        System.out.println("----testCookieStore");
        // 使用cookieStore方式
        CloseableHttpClient client = HttpClients.custom()
                .setDefaultCookieStore(cookieStore).build();
        HttpGet httpGet = new HttpGet(testUrl);
        System.out.println("request line:" + httpGet.getRequestLine());
        try {
            // 执行get请求
            HttpResponse httpResponse = client.execute(httpGet);
            System.out.println("cookie store:" + cookieStore.getCookies());
            printResponse(httpResponse,null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流并释放资源
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void setContext() {
        System.out.println("----setContext");
        context = HttpClientContext.create();
        Registry<CookieSpecProvider> registry = RegistryBuilder
                .<CookieSpecProvider> create()
                .register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
                .register(CookieSpecs.BROWSER_COMPATIBILITY,
                        new BrowserCompatSpecFactory()).build();
        context.setCookieSpecRegistry(registry);
        context.setCookieStore(cookieStore);
    }

    public static void setCookieStore(HttpResponse httpResponse) {
        System.out.println("----setCookieStore");
        cookieStore = new BasicCookieStore();
        // JSESSIONID
        String setCookie = httpResponse.getFirstHeader("Set-Cookie")
                .getValue();
        String JSESSIONID = setCookie.substring("JSESSIONID=".length(),
                setCookie.indexOf(";"));
        System.out.println("JSESSIONID:" + JSESSIONID);
        // 新建一个Cookie
        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",
                JSESSIONID);
        cookie.setVersion(0);
        cookie.setDomain("127.0.0.1");
        cookie.setPath("/CwlProClient");
        // cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
        // cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
        // cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
        // cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
        cookieStore.addCookie(cookie);
    }

    public static List<NameValuePair> getParam(Map parameterMap) {
        List<NameValuePair> param = new ArrayList<NameValuePair>();
        Iterator it = parameterMap.entrySet().iterator();
        while (it.hasNext()) {
            Entry parmEntry = (Entry) it.next();
            param.add(new BasicNameValuePair((String) parmEntry.getKey(),
                    (String) parmEntry.getValue()));
        }
        return param;
    }

    public String MyRandom(){
        long ll= (long) (Math.random()*100000000000000000L);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        String result = "0."+nf.format(ll);
        System.out.println(result);
        return result;
    }
    @Test
    public void testMyR(){
       String s =  MyRandom();
    }
}
