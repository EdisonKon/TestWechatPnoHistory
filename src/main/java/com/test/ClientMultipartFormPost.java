/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package com.test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Example how to use multipart/form encoded POST request.
 */
public class ClientMultipartFormPost {
	String Host = "mp.weixin.qq.com";
	String agent  = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 MicroMessenger/6.5.2.501 NetType/WIFI WindowsWechat QBCore/3.43.691.400 QQBrowser/9.0.2524.400";
	String refer = "https://mp.weixin.qq.com";
	String xrw = "XMLHttpRequest";

    public String sendToPost(String url,String fileContent,Map<String,String> mapKv,Map<String,String> mapHeader,String getOrPost) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resStr = "";
        Charset charset = Charset.forName("UTF-8");//设置编码
        try {
        	HttpEntity reqEntity = null;
        	MultipartEntityBuilder meb = MultipartEntityBuilder.create();
			HttpGet httpGet = new HttpGet(url);
			HttpPost httppost = new HttpPost(url);
        	if("get".equalsIgnoreCase(getOrPost)){
        		int inx = 1;
				for (Entry<String, String> b : mapKv.entrySet()) {
					if(inx==1){
						url+="?"+b.getKey()+"="+b.getValue();
						inx++;
					}else{
						url+="&"+b.getKey()+"="+b.getValue();
					}
				}
				System.out.println(url);
				for (Entry<String, String> b : mapHeader.entrySet()) {
					httpGet.setHeader(b.getKey(), b.getValue());
				}
			}else{
				if(!"".equals(fileContent)&&fileContent != null){
					byte[] data = fileContent.getBytes();
					ByteArrayBody body = new ByteArrayBody(data , "test.txt");
					meb.addPart("tp", body);
				}
				for (Entry<String, String> b : mapKv.entrySet()) {
					meb.addPart(b.getKey(), new StringBody(b.getValue(),charset));
				}
				reqEntity = meb.build();
				httppost.setEntity(reqEntity);
				for (Entry<String, String> b : mapHeader.entrySet()) {
					httppost.setHeader(b.getKey(), b.getValue());
				}
			}

			CloseableHttpResponse response = null;
			if("get".equalsIgnoreCase(getOrPost)){
				System.out.println("executing request " + httpGet.getRequestLine());
				response = httpclient.execute(httpGet);
			}else{
				System.out.println("executing request " + httppost.getRequestLine());
				response = httpclient.execute(httppost);
			}
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
				Header[] headers = response.getHeaders("Set-Cookie");
//				Header[] headers = response.getAllHeaders();
				for (int i=0;i<headers.length;i++){
					System.out.println(headers[i]);
				}
				HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                	System.out.println("Response content length: " + resEntity.getContentLength());
                	resStr = EntityUtils.toString(resEntity);
                    System.out.println("Response content : " + resStr);
                }
            } finally {
                response.close();
            }
        } finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
		return resStr;
    }

    public boolean sendDelHandel(String fileContent){
    	boolean cantoTask = true;
    	String url = "https://"+Host+"/cgi-bin/bizlogin?action=startlogin";
//    	String url = "https://mp.weixin.qq.com/cgi-bin/loginqrcode?action=getqrcode&param=4300&rd=120";
		Map<String,String> mapKv = new  HashMap<String, String>();
		Map<String,String> mapHeader = new  HashMap<String, String>();
		String returnStr = "";
    	mapKv.put("username", "huainian_cl@126.com");
    	mapKv.put("pwd", "8b358d9e5a669346324d2dc4560ebf07");
    	mapKv.put("imgcode", "");
    	mapKv.put("f", "json");
		mapHeader.put("User-Agent",agent);
		mapHeader.put("Referer","https://mp.weixin.qq.com/cgi-bin/bizlogin?action=validate&lang=zh_CN&account=huainian_cl@126.com");
		mapHeader.put("Host",Host);
		mapHeader.put("X-Requested-With",xrw);
    	try {
//    		returnStr = sendToPost(url, fileContent, mapKv,mapHeader,"get");
    		returnStr = sendToPost(url, fileContent, mapKv,mapHeader,"post");
		} catch (Exception e) {
			e.printStackTrace();
		}

        JSONObject jobj = null;
    	try {
    		if("".equals(returnStr)){
    			cantoTask = false;
    		}else{
    			jobj = JSON.parseObject(returnStr);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			cantoTask = false;
		}
		/*
    	if(cantoTask){
    		url = "http://"+Host+"/IumDeploy/iumTaskAdd";
    		if(jobj!=null){
        		String taskid = String.valueOf(jobj.get("redirect_url"));
        		String filename = (String) jobj.get("filename");
        		mapKv.clear();
        		mapKv.put("whererufrom", "phptrfile");
        		mapKv.put("isXpath", "12");
        		mapKv.put("taskid", taskid);
        		mapKv.put("filename", filename);
        		mapKv.put("username", "IUM自动处理");
        		try {
        			returnStr = sendToPost(url, "", mapKv,mapHeader);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        		if("".equals(returnStr)){
        			cantoTask = false;	
        		}
        	}
    	}*/
        return cantoTask;
    }

	public static void main(String[] args) {
		ClientMultipartFormPost cmf = new ClientMultipartFormPost();
    	cmf.sendDelHandel("");
	}
}
