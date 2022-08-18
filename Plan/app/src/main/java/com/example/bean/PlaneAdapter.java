package com.example.bean;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.db.DetailDBService;
import com.example.diary.R;

import java.util.ArrayList;

/**
 * 计划列表数据适配器
 *
 * @author Administrator
 */
public class PlaneAdapter extends BaseAdapter {
    Context context;
    ArrayList<Plane> list;

    Activity activity;

    public PlaneAdapter(Context context, ArrayList<Plane> list) {
        this.context = context;
        this.list = list;
        activity = (Activity) context;

    }

    public void setList(ArrayList<Plane> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.plane_item, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.luntan_listitem_textView_name);
            holder.tv_time = (TextView) convertView.findViewById(R.id.luntan_listitem_textView_time);
            holder.tv_content = (TextView) convertView.findViewById(R.id.luntan_listitem_textView_content);
            holder.tvBFB = (TextView) convertView.findViewById(R.id.tvBFB);
            holder.pb = (ProgressBar) convertView.findViewById(R.id.pb);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_name.setText("计划：" + list.get(position).getTitle());

        holder.tv_time.setText("截止时间：" + list.get(position).getTime());
        holder.tv_content.setText(list.get(position).getContent());
        int bfb = new DetailDBService(context).searchTotalBFB(list.get(position).getPid());
        holder.tvBFB.setText(bfb + "%");
        holder.pb.setProgress(bfb);


        return convertView;
    }

    public float getRawSize(int unit, float value) {
        Resources res = context.getResources();
        return TypedValue.applyDimension(unit, value, res.getDisplayMetrics());
    }

    private class ViewHolder {

        private TextView tv_name;
        private TextView tv_time;
        private TextView tv_content;
        private TextView tvBFB;
        private ProgressBar pb;

    }

}
