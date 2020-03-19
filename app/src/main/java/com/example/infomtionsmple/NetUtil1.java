package com.example.infomtionsmple;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtil1 {
    private String ioToString(InputStream inputStream) throws IOException {
        String json="";
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len = -1;
        while ((len = inputStream.read(bytes)) != -1) {
            byteArrayOutputStream.write(bytes, 0, len);
        }
        byte[] bytes1 = byteArrayOutputStream.toByteArray();
        json=new String(bytes1);
        return json;
    }
    public void doGet(final String httpUrl) {
        //联网是耗时的过程，需要在子线程中进行
        new Thread(new Runnable() {
            @Override
            public void run() {
                //这里就是子线程，可以进行联网操作
                try {
                    //创建URL对象
                    URL url = new URL(httpUrl);
                    //我们需要 HttpURLConnection
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //设置请求方式
                    httpURLConnection.setRequestMethod("GET");
                    //设置连接超时
                    httpURLConnection.setConnectTimeout(5000);
                    //设置读取超时
                    httpURLConnection.setReadTimeout(5000);
                    //真正的开启连接
                    httpURLConnection.connect();
                    //获取状态码，200就是成功
                    if (httpURLConnection.getResponseCode() == 200) {
                        //获取输入流
                        InputStream inputStream = httpURLConnection.getInputStream();
                        //将流转成json
                        String s = ioToString(inputStream);
                        Log.e("TAG", "请求成功" + s);
                    } else {
                        //失败
                        Log.e("TAG", "请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //失败
                    Log.e("TAG", "请求失败");
                }
            }
        }).start();
    }
}
