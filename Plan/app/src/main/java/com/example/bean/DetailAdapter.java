package com.example.bean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.diary.R;
import com.example.diary.ShowPhotoActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * 学习内容列表数据适配器
 * @author Administrator
 *
 */
public class DetailAdapter extends BaseAdapter {
	Context context;
	ArrayList<Detail> list;

	Activity activity;

	public DetailAdapter(Context context, ArrayList<Detail> list) {
		this.context = context;
		this.list = list;
		activity = (Activity) context;

	}

	public void setList(ArrayList<Detail> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.pic_item, null);
			holder.tv_content = (TextView) convertView.findViewById(R.id.luntan_listitem_textView_content);
			holder.tvBFB = (TextView) convertView.findViewById(R.id.tvBFB);
			holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.luntan_listitem_photo_list);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_content.setText("学习内容："+list.get(position).getContent());
		holder.tvBFB.setText("计划完成度："+list.get(position).getProgress()+"%");

		holder.linearLayout.removeAllViews();
		if (list.get(position).getImage().equals("")) {
		} else {
			ImageView imageView;
			final String[] grid_img = list.get(position).getImage().split(";");
			for (int i = 0; i < grid_img.length; i++) {
				imageView = new ImageView(context);
				imageView.setLayoutParams(
						new LinearLayout.LayoutParams((int) getRawSize(TypedValue.COMPLEX_UNIT_DIP, 100),
								(int) getRawSize(TypedValue.COMPLEX_UNIT_DIP, 100)));
				int padding = (int) getRawSize(TypedValue.COMPLEX_UNIT_DIP, 3);
				imageView.setPadding(padding, padding, padding, padding);
				final int finalI = i;
				imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context, ShowPhotoActivity.class);
						intent.putExtra("path", grid_img[finalI]);

						context.startActivity(intent);
					}
				});

				Picasso.with(context).load(new File(grid_img[i])).resize(200, 200).centerInside()
						.placeholder(R.drawable.aio_image_default_round).error(R.drawable.aio_image_default_round)
						.into(imageView);
				holder.linearLayout.addView(imageView);
			}
		}

		return convertView;
	}

	public float getRawSize(int unit, float value) {
		Resources res = context.getResources();
		return TypedValue.applyDimension(unit, value, res.getDisplayMetrics());
	}

	private class ViewHolder {

		private TextView tv_content,tvBFB;
		private LinearLayout linearLayout;

	}

}
