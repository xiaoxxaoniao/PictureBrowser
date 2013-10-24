package me.joysinfo.picturebrowser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import me.joysinfo.http.NetworkUtils;
import me.joysinfo.imp.PictureImp;
import android.os.Bundle;
import android.util.Log;
import android.app.Activity;

public class MainActivity extends Activity implements PictureImp{
    private ArrayList<String> pictureUrls;
    private int page=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		getImagesURL();
	}
	
	@Override
	public void initData() {
		pictureUrls=new ArrayList<String>();
	}
	@Override
	public void refresh() {
		
	}
	private void getImagesURL(){
		NetworkUtils.get(page, "»¢", null, new JsonHttpResponseHandler(){
			public void onSuccess(int arg0, JSONObject json) {
				super.onSuccess(arg0, json);
				try {
					JSONArray array=json.getJSONArray("data");
					for (int i = 0; i < array.length(); i++) {
						 String objURL = array.getJSONObject(i).getString("objURL"); 
						 Log.d("CUI", "ImagesUrls:"+objURL);
						 pictureUrls.add(objURL);  
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Log.d("CUI","numbers:"+pictureUrls.size());
				page++;
			}
		});
	}
}
