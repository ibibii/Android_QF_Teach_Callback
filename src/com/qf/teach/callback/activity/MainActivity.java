package com.qf.teach.callback.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.qf.teach.callback.R;
import com.qf.teach.callback.util.NetUtil;
import com.qf.teach.callback.util.NetUtil.Listener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		NetUtil util = new NetUtil();
		util.requestGet("http://news-at.zhihu.com/api/3/stories/latest", new Listener() {
			@Override
			public void excute(JSONObject response) {
				Log.i("MainActivity", response.toString());
			}
		});
	}

}
