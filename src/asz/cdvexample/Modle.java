package asz.cdvexample;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaWebView;

import android.app.Activity;
import android.webkit.WebViewClient;

public class Modle {

	private static final Object lock = new Object();
	private static Modle instance=null;
	public CordovaWebView cwv;
	public static MConfig config = new MConfig();


	public static CordovaWebView buildCordovaWebView(MConfig c){
		
		Config.init(c.activity);
		CordovaWebView ret = new CordovaWebView(c.activity);
		
		if(c.javascriptInterface!=null)
			ret.addJavascriptInterface(c.javascriptInterface, c.nameSpace);

		if(c.webViewClient!=null)
			ret.setWebViewClient(c.webViewClient);
		
		//must be last
		ret.loadUrl(c.url);
		
		return ret;
	}
	
	private Modle(){	
		Config.init(config.activity);
		cwv = new CordovaWebView(config.activity);

		if(config.javascriptInterface!=null)
			cwv.addJavascriptInterface(config.javascriptInterface, config.nameSpace);

		if(config.webViewClient!=null)
			cwv.setWebViewClient(config.webViewClient);
		
		//must be last
		cwv.loadUrl(config.url);
	}

	public static Modle the(){

		if(instance==null){
			synchronized(lock){
				if(instance==null) 
					instance = new Modle();
			}
		}
		return instance;
	}
	
	
	//=======static config class================== 
	public static class MConfig{
		public Activity activity=null;
		public String url=null;
		public String nameSpace="ASZDEFAULTJSNAMESPACE";
		public Object javascriptInterface=null;
		public WebViewClient webViewClient;
	}

}
