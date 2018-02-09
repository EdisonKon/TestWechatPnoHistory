# TestWechatPnoHistory
测试微信公众号历史链接

主类是 TestCookieClient.java junit测试testLogin即可

主要流程
1.请求登录
请求 https://mp.weixin.qq.com/cgi-bin/bizlogin?action=startlogin 
带参数 
	mapKv.put("username", "xxxx账号");
    	mapKv.put("pwd", "md5加密的密码");
    	mapKv.put("imgcode", "");
    	mapKv.put("f", "json");
带herder

	String Host = "mp.weixin.qq.com";
	String agent  = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 MicroMessenger/6.5.2.501 NetType/WIFI WindowsWechat QBCore/3.43.691.400 QQBrowser/9.0.2524.400";
	String refer = "https://mp.weixin.qq.com";
	String xrw = "XMLHttpRequest";

	mapHeader.put("User-Agent",agent);
	mapHeader.put("Referer","https://mp.weixin.qq.com/cgi-bin/bizlogin?action=validate&lang=zh_CN&account=huainian_cl@126.com");
	mapHeader.put("Host",Host);
	mapHeader.put("X-Requested-With",xrw);

获得
{"base_resp":{"err_msg":"ok","ret":0},"redirect_url":"/cgi-bin/bizlogin?action=validate&lang=zh_CN&account=huainian_cl@126.com"}

和很多cookie
cookie store1:[[version: 0][name: bizuin][value: 3511285890][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: cert][value: oJiMD5Cp1WIjNIStgTonef_xiYFakOKr][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: fake_id][value: 3528287138][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: login_certificate][value: YSyicJXOHdEY4crstqGgwO60y1GPTH1OMoS7feBrw+I=][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: login_sid_ticket][value: ea5415881ffc6ff369bc5585b406601ee084370e][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ticket][value: 109dca8a8e48dec8499e3d0fb9910ccf984ca691][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ticket_certificate][value: GH9nP81euAnvkxefPEYAWxsRQIvhMqahCNsXcGjWcZs=][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ticket_id][value: gh_9df5a050571b][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ticket_uin][value: 3511285890][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: uuid][value: 9fe56dcac9645fc7686dda2a3e618ddc][domain: mp.weixin.qq.com][path: /][expiry: null]]

2.请求获取二维码
request line:GET https://mp.weixin.qq.com/cgi-bin/loginqrcode?action=getqrcode&param=4300&rd=120 HTTP/1.1
获得cookie
cookie store2:[[version: 0][name: bizuin][value: 3511285890][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: cert][value: oJiMD5Cp1WIjNIStgTonef_xiYFakOKr][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: fake_id][value: 3528287138][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: login_certificate][value: YSyicJXOHdEY4crstqGgwO60y1GPTH1OMoS7feBrw+I=][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: login_sid_ticket][value: ea5415881ffc6ff369bc5585b406601ee084370e][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ticket][value: 109dca8a8e48dec8499e3d0fb9910ccf984ca691][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ticket_certificate][value: GH9nP81euAnvkxefPEYAWxsRQIvhMqahCNsXcGjWcZs=][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ticket_id][value: gh_9df5a050571b][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ticket_uin][value: 3511285890][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: uuid][value: 9fe56dcac9645fc7686dda2a3e618ddc][domain: mp.weixin.qq.com][path: /][expiry: null]]

!!!!!!!需要手机扫码确定可访问

3.轮训访问是不是手机认了
扫码并同意访问
失败情况:status!=1
request line:GET https://mp.weixin.qq.com/cgi-bin/loginqrcode?action=ask&f=json&ajax=1&random=0.72205102949145856 HTTP/1.1
status:HTTP/1.1 200 OK
headers:
response length:67
response content:{"base_resp":{"err_msg":"ok","ret":0},"status":0,"user_category":0}

成功情况:status=1
请扫码并同意访问
0.5624547854744200
request line:GET https://mp.weixin.qq.com/cgi-bin/loginqrcode?action=ask&f=json&ajax=1&random=0.5624547854744200 HTTP/1.1
status:HTTP/1.1 200 OK
headers:
response length:67
response content:{"base_resp":{"err_msg":"ok","ret":0},"status":1,"user_category":2}
cookie store3:[[version: 0][name: bizuin][value: 3511285890][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: cert][value: oJiMD5Cp1WIjNIStgTonef_xiYFakOKr][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: fake_id][value: 3528287138][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: login_certificate][value: YSyicJXOHdEY4crstqGgwO60y1GPTH1OMoS7feBrw+I=][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: login_sid_ticket][value: ea5415881ffc6ff369bc5585b406601ee084370e][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ticket][value: 109dca8a8e48dec8499e3d0fb9910ccf984ca691][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ticket_certificate][value: GH9nP81euAnvkxefPEYAWxsRQIvhMqahCNsXcGjWcZs=][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ticket_id][value: gh_9df5a050571b][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ticket_uin][value: 3511285890][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: uuid][value: 9fe56dcac9645fc7686dda2a3e618ddc][domain: mp.weixin.qq.com][path: /][expiry: null]]

4.确定登录,目的获取token
request line:POST https://mp.weixin.qq.com/cgi-bin/bizlogin?action=login HTTP/1.1
status:HTTP/1.1 200 OK
param:
	parameterMap.clear();
            parameterMap.put("userlang","zh_CN");
            parameterMap.put("token","");
            parameterMap.put("lang","zh_CN");
            parameterMap.put("f","json");
            parameterMap.put("ajax","1");
headers:
            mapHeader.put("User-Agent",agent);
            mapHeader.put("Referer",doLoginReferUrl);//doLoginReferUrl-->第一步获取到的返回redirect_url
            mapHeader.put("Host",Host);
            mapHeader.put("Accept-Encoding","gzip, deflate, br");
            mapHeader.put("X-Requested-With",xrw);
            mapHeader.put("Connection","keep-alive");
            mapHeader.put("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            mapHeader.put("Accept","*/*");

response length:109
response content:{"base_resp":{"err_msg":"ok","ret":0},"redirect_url":"/cgi-bin/home?t=home/index&lang=zh_CN&token=608398971"}
cookie store4:[[version: 0][name: bizuin][value: 3528287138][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: cert][value: oJiMD5Cp1WIjNIStgTonef_xiYFakOKr][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: data_bizuin][value: 3511285890][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: data_ticket][value: cqq8dsZtvi1livQgufz+AkPTQ0BlMV1UrR5muclKB1IyNjPl9KWmAiw1j3L8R/g/][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: mm_lang][value: zh_CN][domain: mp.weixin.qq.com][path: /][expiry: Sun Feb 07 14:28:15 CST 2106], [version: 0][name: openid2ticket_ogvhJ0eUIUwcZUH4bfzECRwAhr94][value: YSyicJXOHdEY4crstqGgwO60y1GPTH1OMoS7feBrw+I=][domain: mp.weixin.qq.com][path: /][expiry: Mon Mar 12 09:55:04 CST 2018], [version: 0][name: slave_sid][value: OUxXYklUcjRuSnM0OWVyRFJlUTJKdFZHcENDSjFIQ1l3d0UzN1JyTTN0aFFuWHJvVjkwQzZQT2pXTTN0U3oxMlpFNUphYWJnbWFQWUlLZm9mbnJnQUNmd1NUSWMwN3ZZMmVZaU9kY2ZPa05DaVFjS2Y0aGdhdjBqMkxsZXB0dkp1UnFFdnJVSmhjRjNnUjhQ][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: slave_user][value: gh_9df5a050571b][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ticket][value: 109dca8a8e48dec8499e3d0fb9910ccf984ca691][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ticket_id][value: gh_9df5a050571b][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ua_id][value: 75kO4filn8ZeXlgpAAAAAJeJEMartFF4AlFH2HBjqRU=][domain: mp.weixin.qq.com][path: /][expiry: Tue Jan 19 11:14:07 CST 2038], [version: 0][name: uuid][value: 9fe56dcac9645fc7686dda2a3e618ddc][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: xid][value: ][domain: mp.weixin.qq.com][path: /][expiry: Tue Jan 19 11:14:07 CST 2038]]
获取到的token : 608398971


5.登录成功,获取指定公众号信息 目标获取fakeid
request line:GET https://mp.weixin.qq.com/cgi-bin/searchbiz?action=search_biz&token=608398971&lang=zh_CN&f=json&ajax=1&random=0.56995298448885424&query=girlnba&begin=0&count=5 HTTP/1.1
status:HTTP/1.1 200 OK
headers:
response length:295
response content:{"base_resp":{"ret":0,"err_msg":"ok"},"list":[{"fakeid":"MjM5OTc2MDY5NQ==","nickname":"懂球妹","alias":"girlnba","round_head_img":"http:\/\/mmbiz.qpic.cn\/mmbiz\/sNLWybhlEdSUfjv09hrLGn3KMFtyKicAaO4ianZCj0sIUDRicqkj4cnrKD575hiakXyaTV8co9Wqqia9nDCVyqhVFyg\/0?wx_fmt=png","service_type":1}],"total":1}
[{"service_type":1,"fakeid":"MjM5OTc2MDY5NQ==","round_head_img":"http://mmbiz.qpic.cn/mmbiz/sNLWybhlEdSUfjv09hrLGn3KMFtyKicAaO4ianZCj0sIUDRicqkj4cnrKD575hiakXyaTV8co9Wqqia9nDCVyqhVFyg/0?wx_fmt=png","nickname":"懂球妹","alias":"girlnba"}]
懂球妹
获取到的fakeid : MjM5OTc2MDY5NQ==
cookie store5:[[version: 0][name: bizuin][value: 3528287138][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: cert][value: oJiMD5Cp1WIjNIStgTonef_xiYFakOKr][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: data_bizuin][value: 3511285890][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: data_ticket][value: cqq8dsZtvi1livQgufz+AkPTQ0BlMV1UrR5muclKB1IyNjPl9KWmAiw1j3L8R/g/][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: mm_lang][value: zh_CN][domain: mp.weixin.qq.com][path: /][expiry: Sun Feb 07 14:28:15 CST 2106], [version: 0][name: openid2ticket_ogvhJ0eUIUwcZUH4bfzECRwAhr94][value: YSyicJXOHdEY4crstqGgwO60y1GPTH1OMoS7feBrw+I=][domain: mp.weixin.qq.com][path: /][expiry: Mon Mar 12 09:55:04 CST 2018], [version: 0][name: slave_sid][value: OUxXYklUcjRuSnM0OWVyRFJlUTJKdFZHcENDSjFIQ1l3d0UzN1JyTTN0aFFuWHJvVjkwQzZQT2pXTTN0U3oxMlpFNUphYWJnbWFQWUlLZm9mbnJnQUNmd1NUSWMwN3ZZMmVZaU9kY2ZPa05DaVFjS2Y0aGdhdjBqMkxsZXB0dkp1UnFFdnJVSmhjRjNnUjhQ][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: slave_user][value: gh_9df5a050571b][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ticket][value: 109dca8a8e48dec8499e3d0fb9910ccf984ca691][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ticket_id][value: gh_9df5a050571b][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: ua_id][value: 75kO4filn8ZeXlgpAAAAAJeJEMartFF4AlFH2HBjqRU=][domain: mp.weixin.qq.com][path: /][expiry: Tue Jan 19 11:14:07 CST 2038], [version: 0][name: uuid][value: 9fe56dcac9645fc7686dda2a3e618ddc][domain: mp.weixin.qq.com][path: /][expiry: null], [version: 0][name: xid][value: ][domain: mp.weixin.qq.com][path: /][expiry: Tue Jan 19 11:14:07 CST 2038]]

6.获取公众号历史链接
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

频度过快,目前未摸索封禁模式

request line:GET https://mp.weixin.qq.com/cgi-bin/appmsg?token=608398971&lang=zh_CN&f=json&ajax=1&random=0.51749493645268120&action=list_ex&begin=0&count=5&query=&fakeid=MjM5OTc2MDY5NQ==&type=9 HTTP/1.1
status:HTTP/1.1 200 OK
headers:
response length:53
response content:{"base_resp":{"err_msg":"freq control","ret":200013}}

正常
返回"app_msg_list" 链接list
"app_msg_cnt"-->总共链接数
根据总链接数去,循环遍历
{"app_msg_cnt":5,"app_msg_list":[{"aid":"2652917055_2","appmsgid":2652917055,"cover":"https://mmbiz.qlogo.cn/mmbiz_jpg/sNLWybhlEdR0mJb4wX7v2ZD3YQaiaxjGNmt9RQxhKa3jD6XyUnk4ZgiaBnVOSkUicK7zoI6WPIRMhvEhWKZPlrBKg/0?wx_fmt=jpeg","digest":"心疼奥斯丁马刺的球员们！","itemidx":2,"link":"http://mp.weixin.qq.com/s?__biz=MjM5OTc2MDY5NQ==&mid=2652917055&idx=2&sn=63ff14b6c93b0056b27bc8bfec3a7613&chksm=bce26b678b95e27135db6aa157f0512139e03d3a4c46999e60afb1e89583f497e601bd922087#rd","title":"「前朝」周琦下放第一天就砍15+4+3+2，<em>帕</em><em>克</em>你惭愧不","update_time":1510030317},{"aid":"2652916105_2","appmsgid":2652916105,"cover":"https://mmbiz.qlogo.cn/mmbiz_jpg/sNLWybhlEdTf6qoEY3FYB0dxEwEjE57nsGeUn4SMQBxZEgeRuebNQXJ410ywDr6LPSKkVqDryIvCwfB7CaunDQ/0?wx_fmt=jpeg","digest":"真的没有什么事情是波波维奇干不出来的！","itemidx":2,"link":"http://mp.weixin.qq.com/s?__biz=MjM5OTc2MDY5NQ==&mid=2652916105&idx=2&sn=71914a5aaa02dd98cdd55a5ef75855fd&chksm=bce26fd18b95e6c75fe6b85576fc9c501a63565e96c2563308fa06fdd29cce17c2ebaeca5419#rd","title":"「前朝」别慌！不就下放<em>帕</em><em>克</em>嘛，马刺又不是第一次干","update_time":1508989673},{"aid":"2652915557_1","appmsgid":2652915557,"cover":"https://mmbiz.qlogo.cn/mmbiz_jpg/sNLWybhlEdQc9XsWhnr7DACY4Ph3Sa1YMcSSFibHK63R7A0d5ObBMa8A7SLbw9cmIH4cQqVzMS9OdicouWb7VnEA/0?wx_fmt=jpeg","digest":"每日一问：森林狼的天赋兑现了吗？","itemidx":1,"link":"http://mp.weixin.qq.com/s?__biz=MjM5OTc2MDY5NQ==&mid=2652915557&idx=1&sn=9832cb40706720fed361605f1277f03d&chksm=bce2613d8b95e82b1327355dd469db922a34699bfc26db95fec4d9898f55c82520b3e87b145b#rd","title":"<em>帕</em><em>克</em>的首发算是丢了！好气哦，又被马刺耍了","update_time":1508389738},{"aid":"2652903221_5","appmsgid":2652903221,"cover":"https://mmbiz.qlogo.cn/mmbiz_jpg/sNLWybhlEdSrauWJLP3cHAYdHp1VswoDWibfMricDyvROZOyNfZX37tiaoewSc3OaTxqeic5G5mCdK9ILQvlNuVnnw/0?wx_fmt=jpeg","digest":"老将都不容易呀……","itemidx":5,"link":"http://mp.weixin.qq.com/s?__biz=MjM5OTc2MDY5NQ==&mid=2652903221&idx=5&sn=ba0f3e5e65956209bb4e22f58a98d473&chksm=bce2316d8b95b87bae3e6195fbacc196f6efcbbaaff9e2e36efb6bdae16646f28dbc49a3b0a3#rd","title":"「贡品」滚你M的伤病！还我<em>帕</em><em>克</em>啊","update_time":1493961839},{"aid":"2652886282_2","appmsgid":2652886282,"cover":"https://mmbiz.qlogo.cn/mmbiz_jpg/sNLWybhlEdRhNw5aJlwo32ficRkTsrBjyZgicpKT2lUOHT1ZQ00S6WiaTiaJibcibUtuUaZsGn6t5ttvQhiaMGBmqOkvA/0?wx_fmt=jpeg","digest":"法国主帅这句话，值得我们深思。","itemidx":2,"link":"http://mp.weixin.qq.com/s?__biz=MjM5OTc2MDY5NQ==&mid=2652886282&idx=2&sn=f376d721680b8c280ea6f9cd95dca167#rd","title":"他说：对不起<em>帕</em><em>克</em>，让你以如此局面谢幕","update_time":1471495312}],"base_resp":{"err_msg":"ok","ret":0}}
