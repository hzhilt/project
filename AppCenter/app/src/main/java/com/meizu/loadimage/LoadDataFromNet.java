package com.meizu.loadimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by root on 14-12-4.
 * 加载网络图片
 */
public class LoadDataFromNet {

    String dataUrl;
    String imageUrl;

    public LoadDataFromNet(String dataUrl, String imageUrl) {
        this.dataUrl = dataUrl;
        this.imageUrl = imageUrl;
    }


    public String getResult() {
        HttpGet httpRequest = new HttpGet(dataUrl);
        String strResult = "";
        StringBuffer sb = new StringBuffer();
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            InputStream inputStream;
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                inputStream = httpResponse.getEntity().getContent();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(inputStream, "UTF-8"));
                String data = "";

                while ((data = br.readLine()) != null) {
                    sb.append(data);
                }
                strResult = sb.toString();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResult;
    }

    public Bitmap loadImageFormNet() throws IOException {

        URL url = new URL(imageUrl);
        HttpURLConnection conn = (HttpURLConnection) url
                .openConnection();
        conn.setInstanceFollowRedirects(true);
        InputStream is = conn.getInputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        is.close();

        return bitmap;
    }
}
