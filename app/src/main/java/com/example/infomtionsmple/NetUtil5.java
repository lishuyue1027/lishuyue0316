package com.example.infomtionsmple;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NetUtil5 {
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
}
