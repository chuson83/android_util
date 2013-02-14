package com.chikuwastudio.photospotsearch.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;


public class HttpIF {

	/**
	 * 分割して送ってリクエストを取得
	 * @param authority
	 * @param querys
	 * @return レスポンス
	 */
	public String requestGet(String scheme,String url,String path,LinkedHashMap<String,String> querys){

		//分割して送るときの仕組み
		Uri.Builder builder = new Uri.Builder();
        builder.scheme(scheme);	//exp "http"
        builder.authority(url);	//exp "example.com"
        builder.path(path);		//exp "top/json"

        for(Map.Entry<String, String> query : querys.entrySet()) {
        	builder.appendQueryParameter(query.getKey(), query.getValue());
        }
        Log.i("url", builder.toString());
        String res = reqText(builder.toString());

        return res;
	}

	/**
	 * POST形式で送信し、リクエスト取得
	 * @param url
	 * @param querys
	 * @return レスポンス
	 */
	public String requestPost(String url, LinkedHashMap<String,String> querys){
		//クライアント設定
        HttpClient client = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        //POST送信するデータを格納（PHPだと「$_POST[query.getkey()]=query.getValue()」の形で取得できる）
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        for(Map.Entry<String, String> query : querys.entrySet()) {
        	nameValuePair.add(new BasicNameValuePair(query.getKey(), query.getValue()));
        }

        try{
            //POST送信、UTF-8で送る。
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
            HttpResponse response = client.execute(httppost);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            response.getEntity().writeTo(byteArrayOutputStream);

            //サーバーからの応答を取得
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                String res = byteArrayOutputStream.toString();
                return res;
            }
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally{
        	//通信を閉じる
            client.getConnectionManager().shutdown();
        }
		return null;
	}

	/**
	 * テキスト取得HTTP通信
	 * @param url
	 * @return レスポンス
	 */
	public String reqText(String url){

    	//HTTP通信するための仕組み
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse res;
        HttpEntity entity;
        String str ="";

        try{
        	//HTTP通信する（リクエストを送信する）
        	res = client.execute(get);

        	//ステータスコードのチェック(200:OK)
        	if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
        		//ボディ部を抽出する。
        		entity = res.getEntity();

        		//受信データを文字列に変換する。
        		str = EntityUtils.toString(entity);
        		return str;

        	}
        } catch (ClientProtocolException e){
        	e.printStackTrace();
        } catch (IOException e){
        	e.printStackTrace();
        }
        finally{
            client.getConnectionManager().shutdown();
        }

    	return null;
    }

	/**
	 * イメージ取得HTTP通信
	 * @param url
	 * @return URLから取得したBitmap
	 */
	public Bitmap reqImg(String url){

    	//HTTP通信するための仕組み
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse res;
        HttpEntity entity;
        Bitmap bmp = null;

        try{
        	//HTTP通信する（リクエストを送信する）
        	res = client.execute(get);

        	//ステータスコードのチェック(200:OK)
        	if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
        		//ボディ部を抽出する。
        		entity = res.getEntity();

        		//入力データを取得
        		InputStream is  = entity.getContent();

        		//画像データに変換
        		bmp = BitmapFactory.decodeStream(is);

        		return bmp;
        	}
        } catch (ClientProtocolException e){
        	e.printStackTrace();
        } catch (IOException e){
        	e.printStackTrace();
        }
        finally{
            client.getConnectionManager().shutdown();
        }

    	return bmp;
    }
	
}