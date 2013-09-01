package asz.cdvexample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import asz.model.ApiMT;
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
				//TODO
				//	Intent intent = new Intent(getApplicationContext(), NextActivity.class);
				//	startActivity(intent);	
			}

		};
		btn.setOnClickListener(n);


		cdv = new ApiMT("http://cto.timetoknow.com/lms/js/libs/t2k/t2k.html", new CallBack(){

			@Override
			public void call(String msg){
				msg+="";
			}

		}, this);

		//--------------------------------------------------------------------------------------------------

		

		Button btndo = (Button) findViewById(R.id.btnDo);

		OnClickListener nd = new OnClickListener(){

			@Override
			public void onClick(View v) {
				
//				cdv.loadApi(new CallBack(){
//
//					@Override
//					public void call(String msg){
//						Log.d(TAG , "success:  "+msg);	
//						TextView out = (TextView) findViewById(R.id.output);
//						out.setText("success:  "+msg);
//					}
//				}, new CallBack(){
//
//					@Override
//					public void call(String msg){
//						Log.d(TAG , "failed:  "+msg);
//						TextView out = (TextView) findViewById(R.id.output);
//						out.setText("failed:  "+msg);
//					}
//				});
			
				cdv.logInMF("'deva.teacher'", "'123456'", new CallBack(){

					@Override
					public void call(String msg){
						Log.d(TAG , "success:  "+msg);	
						TextView out = (TextView) findViewById(R.id.output);
						out.setText("success:  "+msg);
					}
				}, new CallBack(){

					@Override
					public void call(String msg){
						Log.d(TAG , "failed:  "+msg);
						TextView out = (TextView) findViewById(R.id.output);
						out.setText("failed:  "+msg);
					}
				});
				
				
				
				
			
				
				
				
				
			}
		};
		
		
		
		
		btndo.setOnClickListener(nd);
		
		LinearLayout mv = (LinearLayout) findViewById(R.id.mainl);
		mv.addView(cdv.getWebView());
		
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
		if (cdv.getWebView() != null) {
			cdv.getWebView().loadUrl("javascript:try{cordova.require('cordova/channel').onDestroy.fire();}catch(e){console.log('exception firing destroy event from native');};");
			cdv.getWebView().loadUrl("about:blank");
			((CordovaWebView)cdv.getWebView()).handleDestroy();
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
