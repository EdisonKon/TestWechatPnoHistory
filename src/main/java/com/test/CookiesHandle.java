package com.test;

import org.apache.http.client.CookieStore;

import java.io.*;

/**
 * @Author : kaifa
 * @Description :
 * @Date : Create in 15:03 2018/2/9
 * @modified : kaifa
 */
public class CookiesHandle {
    String cookieSavePath = "D://";

    public void saveCookies(CookieStore cookiestore) throws Exception {
        new File(cookieSavePath).mkdirs();
        File file = new File(cookieSavePath + "/cookie.file");
        FileOutputStream fos = new FileOutputStream(file);
        // 写入的文件是以二进制文件存储
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        System.out.println("cookile:" + cookiestore);
        oos.writeObject(cookiestore);
        oos.close();
        fos.close();
    }

    public CookieStore myGetCookies() {
        File file = new File(cookieSavePath);
        CookieStore cookieStore = null;
        // 读cookie
        if (file.exists()) {
            FileInputStream fin = null;
            try {
                fin = new FileInputStream(file);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            ObjectInputStream in;
            try {
                in = new ObjectInputStream(fin);
                cookieStore = (CookieStore) in.readObject();
                System.out.println(cookieStore);
                in.close();
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
        }
    return cookieStore;
    }
}
