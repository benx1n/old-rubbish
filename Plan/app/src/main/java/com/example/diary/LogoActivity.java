package com.example.diary;

import android.content.Intent;
import android.os.Bundle;

public class LogoActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					Thread.sleep(2000);
					startActivity(new Intent(LogoActivity.this, MainActivity.class));
					finish();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}.start();
	}

}
