package asz.cdvexample;

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

public class MainActivity extends Activity  implements CordovaInterface{

	public final String NAME_SPASE = "ASZNSP";

	public static CordovaWebView cdv;
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
				//	LinearLayout l = (LinearLayout) findViewById(R.id.mainl);
				//	l.removeView(cdv);

				Intent intent = new Intent(getApplicationContext(), NextActivity.class);
				startActivity(intent);	
			}

		};
		btn.setOnClickListener(n);


		Modle.MConfig c = new Modle.MConfig();
		c.activity=this;
		c.url="http://cto.timetoknow.com/test/index.html";
		c.javascriptInterface = this;
		c.nameSpace = NAME_SPASE;



		WebViewClient client =new WebViewClient(){

			@Override
			public void onPageFinished(WebView view, String url) {
				Log.d(TAG , "onPageFinished loadded:"+url);
			}
		};

		c.webViewClient=client;

		LinearLayout l = (LinearLayout) this.findViewById(R.id.mainl);
		cdv = Modle.buildCordovaWebView(c);
		
		
		
	//	l.addView(cdv);


		//=============================================================

		final EditText jscommand = (EditText) findViewById(R.id.command);

		Button btndo = (Button) findViewById(R.id.btnDo);

		OnClickListener nd = new OnClickListener(){

			@Override
			public void onClick(View v) {
				String command = jscommand.getText().toString();
				jscommand.setText("");

				//echomsg('yo yo yo!!!!'  ,"+NAME_SPASE+".nativesuccess   , "+NAME_SPASE+".nativefaliure  )
				
				String ccc="javascript:add(33, 2 , function(msg){"+NAME_SPASE+".nativesuccess(msg);} , function(msg){"+NAME_SPASE+".nativefaliure(msg);} )";
				
			//	ccc = "javascript:ASZNSP.nativesuccess('me here!!!')"; 
				cdv.loadUrl(ccc);

				//TODO

			}

		};
		btndo.setOnClickListener(nd);



		cdv.reload();



	}

	//============================================================================================================


	@JavascriptInterface
	public void nativesuccess(String  msg){
		Log.d(TAG , "nativesuccess:  "+msg);
	}
	@JavascriptInterface
	public void nativefaliure(String msg){
		Log.d(TAG , "nativefaliure:  "+msg);
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
