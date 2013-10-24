package me.joysinfo.picturebrowser;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aphidmobile.flip.FlipViewController;
import com.aphidmobile.utils.IO;
import com.aphidmobile.utils.UI;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import me.joysinfo.http.NetworkUtils;
import me.joysinfo.imp.PictureImp;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
public class MainActivity extends Activity implements PictureImp{
	private FlipViewController flipView;
    private ArrayList<String> pictureUrls;
    private int page=2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 initImageLoader(this);
		 flipView = new FlipViewController(this);
		 setContentView(flipView);
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
		NetworkUtils.get(page, "∏„–¶", null, new JsonHttpResponseHandler(){
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
				flipView.setAdapter(new MyBaseAdapter(MainActivity.this, flipView,pictureUrls));
				page++;
			}
			@Override
			public void onFinish() {
				super.onFinish();
					
			}
		});
	}
	
	
 public static void initImageLoader(Context context) {
			// This configuration tuning is custom. You can tune every option, you may tune some of them,
			// or you can create default configuration by
			//  ImageLoaderConfiguration.createDefault(this);
			// method.
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
					.threadPriority(Thread.NORM_PRIORITY - 2)
					.denyCacheImageMultipleSizesInMemory()
					.discCacheFileNameGenerator(new Md5FileNameGenerator())
					.tasksProcessingOrder(QueueProcessingType.LIFO)
					.writeDebugLogs() // Remove for release app
					.build();
			// Initialize ImageLoader with configuration.
			ImageLoader.getInstance().init(config);
   }
	
	
	
private static class MyBaseAdapter extends BaseAdapter {

		    private FlipViewController controller;

		    private Context context;

		    private LayoutInflater inflater;

		    protected ImageLoader imageLoader = ImageLoader.getInstance();
		    private ArrayList<String> Urls;
		    private DisplayImageOptions options;
		    private MyBaseAdapter(Context context, FlipViewController controller,ArrayList<String> Urls) {
		      inflater = LayoutInflater.from(context);
		      this.context = context;
		      this.controller = controller;
              this.Urls=Urls;
              
              options = new DisplayImageOptions.Builder()
            //  .showImageOnLoading(new BitmapDrawable(BitmapFactory.decodeResource(context.getResources(),R.drawable.loading)))
      		  .showImageForEmptyUri(R.drawable.ic_launcher)
      		  .showImageOnFail(R.drawable.ic_launcher)
      		  .cacheInMemory(false)
      		  .cacheOnDisc(true)
      		  .bitmapConfig(Bitmap.Config.RGB_565)
      		  .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
      		  .resetViewBeforeLoading(true)
      		  .build();
		      //Use a system resource as the placeholder
		    }

		    @Override
		    public int getCount() {
		      return Urls.size();
		    }

		    @Override
		    public Object getItem(int position) {
		      return position;
		    }

		    @Override
		    public long getItemId(int position) {
		      return position;
		    }

		    @Override
		    public View getView(int position, View convertView, ViewGroup parent) {
		      View layout = convertView;
		      if (convertView == null) {
		        layout = inflater.inflate(R.layout.complex1, null);
		      }
		      ImageView photoView = UI.findViewById(layout, R.id.photo);
		      boolean needReload = true;
		      imageLoader.displayImage(Urls.get(position),photoView, options);
		      return layout;
		    }
		  }
	}
