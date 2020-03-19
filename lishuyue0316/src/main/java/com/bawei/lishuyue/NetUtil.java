package com.bawei.lishuyue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtil {
    private NetUtil(){

    }
    public static NetUtil getInstance(){
        return NET_UTIL;
    }
    private static final NetUtil NET_UTIL = new NetUtil();
    Handler handler=new Handler();
    private String ioToString(InputStream inputStream) throws IOException {
        byte[] bytes = new byte[1024];
        int len=-1;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((len=inputStream.read(bytes))!=-1){
            outputStream.write(bytes,0,len);
        }
        byte[] bytes1 = outputStream.toByteArray();
        String json=new String(bytes1);
        return json;
    }
    private Bitmap ioToBitmap(InputStream inputStream){
        return BitmapFactory.decodeStream(inputStream);
    }
    public interface MyCallback{
        void onDoGetSuccess(String json);
        void onDoGetError();
    }
    public void doGet(final String httpurl, final MyCallback myCallback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream=null;
                try {
                    URL url=new URL(httpurl);
                    HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(7000);
                    httpURLConnection.connect();
                    if(httpURLConnection.getResponseCode()==200){
                        inputStream=httpURLConnection.getInputStream();
                        final String json=ioToString(inputStream);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("TAG","请求成功"+json);
                                myCallback.onDoGetSuccess(json);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    myCallback.onDoGetError();
                }finally {
                    if(inputStream!=null){
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
    public void doGetPhone(final String phoneurl, final ImageView imageView){
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream=null;
                try {
                    URL url=new URL(phoneurl);
                    HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(7000);
                    httpURLConnection.connect();
                    if(httpURLConnection.getResponseCode()==200){
                        inputStream=httpURLConnection.getInputStream();
                        final Bitmap bitmap=ioToBitmap(inputStream);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if(inputStream!=null){
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
    public boolean hasNet(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isAvailable()){
            return true;
        }else {
            return false;
        }
    }
}
