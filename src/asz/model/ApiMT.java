package asz.model;


import java.util.ArrayList;
import java.util.List;

import org.apache.cordova.CordovaWebView;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import asz.model.util.CallBack;



public class ApiMT  {

	private JSI jsi=null;
	CordovaWebView webview=null;
	public final String NAME_SPASE = "ASZNSP";


	public ApiMT(String apiUrl, CallBack callbackOnLoadded, Activity activity){

		final CallBack call = callbackOnLoadded;
		
		jsi = new JSI(NAME_SPASE);
		
		CDVFactory.config.activity=activity;
		CDVFactory.config.url=apiUrl;
		CDVFactory.config.javascriptInterface = jsi;
		CDVFactory.config.nameSpace = NAME_SPASE;
		
		WebViewClient client =new WebViewClient(){

			boolean first=true;
			@Override
			public void onPageFinished(WebView view, String url) {
				Log.d("JSI" , "onPageFinished loadded:"+url);
				if( first ){
					first = false;
					call.call("AOK");
				}
			}
		};

		CDVFactory.config.webViewClient=client;
		
		webview=CDVFactory.the().cwv;
		
		jsi.setWebview(webview);
		
	}

	public CordovaWebView getCordovaWebView(){
		return this.webview;
	}
	
	//-------------------------------------API-----------------------------------------------------------------

	public void loadApi(CallBack success,CallBack faliure){
		List<String> params= new ArrayList<String>();
		params.add(comma("html5"));
		jsi.execJS("T2K.api.load", params, success, faliure);
	}

	public void initApi(CallBack success,CallBack faliure){
		jsi.execJS("T2K.server.initData", null, success, faliure);
	}

	public void logIn(String username, String password, CallBack success,CallBack faliure){	
		List<String> params= new ArrayList<String>();

		params.add(comma(username));
		params.add(comma(password));

		jsi.execJS("T2K.user.login", params, success, faliure);

	}

	public void logInMF(String username, String password, CallBack success,CallBack faliure){

		final String u= username;
		final String p= password;
		final CallBack s= success;
		final CallBack f= faliure;

		loadApi(  new CallBack(){
			public void call(String msg){
				initApi( new CallBack(){
					public void call(String msg){
						logIn( u,  p,  s, f);
					}
				}, f );
			}
		}  , f );
		
	}


	public void logOut(CallBack success,CallBack faliure){

		jsi.execJS("T2K.user.logout", null, success, faliure);
	}

	public void getStudyClasses(CallBack success,CallBack faliure){
		jsi.execJS( "T2K.user.getStudyClasses", null, success, faliure);
	}

	public void getCourse(String cid , CallBack success,CallBack faliure){

		List<String> params= new ArrayList<String>();

		params.add(cid);

		jsi.execJS( "T2K.content.getCourseByClass", params, success, faliure);
	}

	public void getLessonContent(String courseId, String lessonId , CallBack success,CallBack faliure){

		List<String> params= new ArrayList<String>();

		params.add(comma(courseId));
		params.add(comma(lessonId));

		jsi.execJS( "T2K.content.getLessonContent", params, success, faliure);
	}

	//======================================================================================================

	public void testjs(String functionName , List<String> params , CallBack success,CallBack faliure){
		jsi.execJS(functionName, params, success, faliure);
	}
	
	public static String comma(String me){
		return "'"+me+"'";
	}

	protected JSI getJSI(){
		return jsi;
	}

	protected WebView getInnerView(){
		return webview;
	}

	
	//==========================================================================================================
	



	
	//============
	

}
