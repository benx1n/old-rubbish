package com.example.diary;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bean.Detail;
import com.example.db.DetailDBService;
import com.example.util.SelectImg;
import com.example.util.T;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * 编辑计划详情界面（学习记录）
 *
 * @author Administrator
 */
public class EditDetailActivity extends BaseActivity {


    EditText et_content, etXQ;
    TextView tv_fb;

    GridView mGridView;
    MyAdapter adapter;
    LinearLayout ll, llBack;
    DetailDBService service;
    SelectImg select;
    Detail rj;
    int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail);
        rj = (Detail) getIntent().getSerializableExtra("pic");
        service = new DetailDBService(this);

        llBack = (LinearLayout) findViewById(R.id.llBack);

        select = new SelectImg(EditDetailActivity.this);

        mGridView = (GridView) findViewById(R.id.luntan_state_gridview);
        mGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mImages.size()) {// 选择添加图片方式
                    AlertDialog.Builder builder = new AlertDialog.Builder(// 实例化了一个对话框
                            EditDetailActivity.this).setTitle("添加图片").setItems(new String[]{"拍照", "图库"},
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        // 拍照选择方式
                                        select.takeCamara();

                                    } else if (which == 1) {
                                        // 图库选择方式
                                        select.takePhoto();

                                    }

                                }

                            });
                    builder.show();
                } else {
                    startActivity(new Intent(EditDetailActivity.this, ShowPhotoActivity.class).putExtra("path",
                            mImages.get(position)));
                }

            }
        });
        mGridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {// 根据手机屏幕宽度适配图片大小
            @Override
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onGlobalLayout() {

                final int width = mGridView.getWidth();
                final int desireSize = getResources().getDimensionPixelOffset(R.dimen.image_size);
                final int numCount = width / desireSize;
                final int columnSpace = getResources().getDimensionPixelOffset(R.dimen.space_size);
                int columnWidth = (width - columnSpace * (numCount - 1)) / numCount;
                adapter.setItemSize(columnWidth);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mGridView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

        tv_fb = (TextView) findViewById(R.id.tv_fabiao_btn);
        tv_fb.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String content = et_content.getText().toString();
                String xq = etXQ.getText().toString();
                if (xq.equals("") || content.equals("")) {
                    T.Show(EditDetailActivity.this, "所填内容不能为空");
                    return;
                }

                if (mImages.size() == 0) {
                    T.Show(EditDetailActivity.this, "请至少添加一张照片");
                    return;
                }
                progress = Integer.parseInt(xq);
                if (progress < 0 || progress > 100) {
                    T.Show(EditDetailActivity.this, "进度为0~100之间");
                    return;
                }


                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < mImages.size(); i++) {
                    if (i != mImages.size() - 1) {
                        sb.append(mImages.get(i) + ";");
                    } else {
                        sb.append(mImages.get(i));
                    }
                }

                String image = sb.toString();
                Detail p = new Detail(rj.getDid(), rj.getPid(), image, content, progress);
                service.update(p);
                T.Show(EditDetailActivity.this, "编辑成功");
                finish();

            }
        });
        ll = (LinearLayout) findViewById(R.id.layout_back);
        ll.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });

        etXQ = (EditText) findViewById(R.id.etXQ);
        etXQ.setText(rj.getProgress() + "");
        et_content = (EditText) findViewById(R.id.editText_content);
        et_content.setText(rj.getContent());
        String arr[] = rj.getImage().split(";");
        for (int i = 0; i < arr.length; i++) {
            mImages.add(arr[i]);
        }
        adapter = new MyAdapter();
        mGridView.setAdapter(adapter);

    }

    /**
     * 拍照和图库选完后结果在此处理
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (!(resultCode == RESULT_OK)) {
            return;
        }
        String path = "";
        if (requestCode == SelectImg.CAMARA) {
            path = select.getCamaraPath();


        } else if (requestCode == SelectImg.IMG) {
            path = select.getPhotoPath(data.getData());

        }


        mImages.add(path);
        adapter.notifyDataSetChanged();
        System.out.println(path);

    }

    private ArrayList<String> mImages = new ArrayList<String>();

    private class MyAdapter extends BaseAdapter {
        private GridView.LayoutParams mItemLayoutParams;
        private int mItemSize = 60;

        public MyAdapter() {
            super();
            mItemLayoutParams = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT,
                    GridView.LayoutParams.MATCH_PARENT);

        }

        /**
         * 重置每个Column的Size
         *
         * @param columnWidth
         */
        public void setItemSize(int columnWidth) {

            if (mItemSize == columnWidth) {
                return;
            }

            mItemSize = columnWidth;

            mItemLayoutParams = new GridView.LayoutParams(mItemSize, mItemSize);

            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (mImages.size() == 6) {
                return mImages.size();
            }
            return mImages.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mImages.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (mImages.size() != 6 && position >= mImages.size()) {
                convertView = LayoutInflater.from(EditDetailActivity.this).inflate(R.layout.grid_item_add, parent, false);
                convertView.setTag(null);
            } else {
                Viewholder holder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(EditDetailActivity.this).inflate(R.layout.grid_item_image, parent,
                            false);
                    holder = new Viewholder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (Viewholder) convertView.getTag();
                    if (holder == null) {
                        convertView = LayoutInflater.from(EditDetailActivity.this).inflate(R.layout.grid_item_image,
                                parent, false);
                        holder = new Viewholder(convertView);
                    }
                }
                holder.indicator.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mImages.remove(position);
                        notifyDataSetChanged();
                    }
                });

                File imageFile = new File(mImages.get(position));

                Picasso.with(EditDetailActivity.this).load(imageFile).placeholder(R.drawable.default_error)
                        // .error(R.drawable.default_error)
                        .resize(mItemSize, mItemSize).centerCrop().into(holder.image);

            }
            GridView.LayoutParams lp = (GridView.LayoutParams) convertView.getLayoutParams();
            if (lp.height != mItemSize) {
                convertView.setLayoutParams(mItemLayoutParams);
            }

            return convertView;
        }

        private class Viewholder {
            private ImageView image;
            private ImageView indicator;

            Viewholder(View view) {
                image = (ImageView) view.findViewById(R.id.grid_item_image);
                indicator = (ImageView) view.findViewById(R.id.grid_item_delete);

            }
        }

    }


}
