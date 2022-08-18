package com.example.diary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 主界面
 * 
 * @author Administrator
 *
 */
public class MainActivity extends BaseActivity implements OnClickListener {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.btn1).setOnClickListener(this);
		findViewById(R.id.btn4).setOnClickListener(this);
		
	}
	


	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {

		case R.id.btn1:
			startActivity(new Intent(MainActivity.this, PlanListActivity.class));
			break;

		case R.id.btn4:
			finish();
			break;

		}

	}

}
