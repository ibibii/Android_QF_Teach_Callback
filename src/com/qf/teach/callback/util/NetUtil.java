package com.qf.teach.callback.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

public class NetUtil {
	private static final int TIMEOUT = 5000;
	private Listener mListener;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			JSONObject jsonObject = (JSONObject) msg.obj;
			mListener.excute(jsonObject);
		}
	};
	
	public void requestGet(final String uri, final Listener listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					HttpEntity httpEntity = null;
					HttpClient httpClient = new DefaultHttpClient();
					HttpParams httpParams = httpClient.getParams();
					HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
					HttpGet httpGet = new HttpGet(uri);
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						mListener = listener;
						
						httpEntity = httpResponse.getEntity();
						JSONObject jsonObject = new JSONObject(EntityUtils.toString(httpEntity, "UTF-8"));
						Message msg = Message.obtain();
						msg.obj = jsonObject;
						handler.sendMessage(msg);
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public interface Listener {
		public void excute(JSONObject response);
	}
}
