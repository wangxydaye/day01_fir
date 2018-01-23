package com.bwei.wangxuyang20171221;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by wonder on 2017/12/21.
 */

public class Netutil {
    public static String shj(String str){
        StringBuilder sb=new StringBuilder();
        try {
            //获取url
            URL url=new URL(str);
            //创建连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //设置连接超时时长
            urlConnection.setConnectTimeout(5000);
            //获取状态码
            int responseCode = urlConnection.getResponseCode();
            if(responseCode==200){
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
                String line=null;
                while ((line=br.readLine())!=null){
                    sb.append(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static int getNetype(Context context) {
            int netType = -1;
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo == null) {
                return netType;
            }
            int nType = networkInfo.getType();
            if (nType == ConnectivityManager.TYPE_MOBILE) {
                netType = 2;
            } else if (nType == ConnectivityManager.TYPE_WIFI) {
                netType = 1;
            }
            return netType;
        }
}
