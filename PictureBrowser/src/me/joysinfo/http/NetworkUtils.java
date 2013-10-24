package me.joysinfo.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * created by cuiyuhu
 * a tool which can help access network
 */
public class NetworkUtils {

	 private static final String BASE_URL = "http://image.baidu.com/i?";

	  private static AsyncHttpClient client = new AsyncHttpClient();

	  public static void get(int page,String word, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		  client.get(getAbsoluteUrl(page,word), params, responseHandler);
	  }

	  public static void post(int page,String word, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.post(getAbsoluteUrl(page,word), params, responseHandler);
	  }

	  
	  //œ¬‘ÿÕº∆¨
	  public static void loadImage(Context context,String url,AsyncHttpResponseHandler responseHandler){
	    	client.cancelRequests(context, true);
	    	client.post(url, responseHandler);
	    }
	  
	  
	  
	  
	  private static String getAbsoluteUrl(int page,String word) {
		  String url="";
	      try {
		     url= BASE_URL +"tn=baiduimagejson&ie=utf-8&ic=0&rn=20&pn="+page+"&word=" + URLEncoder.encode(word,"UTF-8");
		  } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		  }
	      Log.d("CUI","url:"+url);
	  	return url;
	  }
	  
}
