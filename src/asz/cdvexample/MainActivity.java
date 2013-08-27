package asz.cdvexample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText; 
import android.widget.LinearLayout;
import asz.model.ApiMT;
import asz.model.CDVFactory;
import asz.model.util.CallBack;

public class MainActivity extends Activity  implements CordovaInterface{

	public final String NAME_SPASE = "ASZNSP";

	public static ApiMT cdv;
	private String TAG = "ASZ_CORDOVA_ACTIVITY";
	private final ExecutorService threadPool = Executors.newCachedThreadPool();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btn = (Button) findViewById(R.id.btnNext);

		OnClickListener n = new OnClickListener(){

			@Override
			public void onClick(View v) {
				
			//	Intent intent = new Intent(getApplicationContext(), NextActivity.class);
			//	startActivity(intent);	
			}

		};
		btn.setOnClickListener(n);


	//	CDVFactory.MConfig c = new CDVFactory.MConfig();
	//	c.activity=this;
	//	c.url="http://cto.timetoknow.com/test/index.html";
	//	c.javascriptInterface = this;
	//	c.nameSpace = NAME_SPASE;



		WebViewClient client =new WebViewClient(){

			@Override
			public void onPageFinished(WebView view, String url) {
				Log.d(TAG , "onPageFinished loadded:"+url);
			}
		};

	//	c.webViewClient=client;

		LinearLayout l = (LinearLayout) this.findViewById(R.id.mainl);
	
		cdv = new ApiMT("http://cto.timetoknow.com/test/index.html", new CallBack(){
			
			@Override
			public void call(String msg){
				msg+="";
			}
			
		}, this);
		
		
		
	//	l.addView(cdv);


		//=============================================================

		final EditText jscommand = (EditText) findViewById(R.id.command);

		Button btndo = (Button) findViewById(R.id.btnDo);

		OnClickListener nd = new OnClickListener(){

			@Override
			public void onClick(View v) {
				//String command = jscommand.getText().toString();
				//jscommand.setText("");

				List<String> params = new ArrayList<String>();
				params.add("1");
				params.add("2");
				
				cdv.testjs("add", params, new CallBack(){
					
					@Override
					public void call(String msg){
						Log.d(TAG , "success:  "+msg);					
					}
				}, new CallBack(){

					@Override
					public void call(String msg){
						Log.d(TAG , "failed:  "+msg);		
					}
				});
			}
		};
		btndo.setOnClickListener(nd);
	}


	//=====================================================================================================

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//=======================================================================================================

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (cdv.getCordovaWebView() != null) {
			
			cdv.getCordovaWebView().loadUrl("javascript:try{cordova.require('cordova/channel').onDestroy.fire();}catch(e){console.log('exception firing destroy event from native');};");
			cdv.getCordovaWebView().loadUrl("about:blank");
			cdv.getCordovaWebView().handleDestroy();
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
