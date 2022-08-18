package com.example.diary;

import java.io.File;

import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.widget.ImageView;

/**
 * 显示图片
 * 
 * @author Administrator
 *
 */
public class ShowPhotoActivity extends BaseActivity {
	ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_show_photo);
		String path = getIntent().getStringExtra("path");// 获取前一个类传过来的路径
		iv = (ImageView) findViewById(R.id.imageView1);// 初始化图片显示控件
		Picasso.with(this).load(new File(path)).placeholder(R.drawable.default_error)
				// .error(R.drawable.default_error)
				.resize(800, 800).centerInside().into(iv);// 根据路径显示
	}

}
