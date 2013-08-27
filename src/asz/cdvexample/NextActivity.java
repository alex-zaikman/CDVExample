package asz.cdvexample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import asz.model.CDVFactory;

public class NextActivity extends Activity implements CordovaInterface {

	  public static CordovaWebView cdv;
		private String TAG = "ASZ_CORDOVA_ACTIVITY2";
		private final ExecutorService threadPool = Executors.newCachedThreadPool();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_next);
		
		
		CDVFactory.MConfig c = new CDVFactory.MConfig();
		c.activity=this;
		c.url="http://www.ynet.co.il";

		
		

	WebViewClient client =new WebViewClient(){
			
			@Override
			public void onPageFinished(WebView view, String url) {
				Log.d(TAG , "onPageFinished loadded:"+url);
			}
		  };
		  c.webViewClient=client;
		
		LinearLayout l = (LinearLayout) this.findViewById(R.id.mainlnext);
		cdv = CDVFactory.buildCordovaWebView(c);
		l.addView(cdv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.next, menu);
		return true;
	}

	
	//=======================================================================================================
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (cdv != null) {
			cdv
			.loadUrl("javascript:try{cordova.require('cordova/channel').onDestroy.fire();}catch(e){console.log('exception firing destroy event from native');};");
			cdv.loadUrl("about:blank");
			cdv.handleDestroy();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
	}


	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public ExecutorService getThreadPool() {
		return threadPool;
	}

	@Override
	public Object onMessage(String message, Object obj) {
		Log.d(TAG, "onMessage message: "+message);
		return null;
	}

	@Override
	public void setActivityResultCallback(CordovaPlugin cordovaPlugin) {
		Log.d(TAG, "setActivityResultCallback is unimplemented");
	}

	@Override
	public void startActivityForResult(CordovaPlugin cordovaPlugin,
			Intent intent, int resultCode) {
		Log.d(TAG, "startActivityForResult is unimplemented");
	}

}
