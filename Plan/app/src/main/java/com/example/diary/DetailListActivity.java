package com.example.diary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.bean.Detail;
import com.example.bean.DetailAdapter;
import com.example.db.DetailDBService;
import com.example.util.T;

import java.util.ArrayList;

/**
 * 学习计划详情列表界面（学习记录）
 *
 * @author Administrator
 */
public class DetailListActivity extends BaseActivity {

    ListView lv;
    ImageView iv_add, iv_me;
    DetailDBService iservice;
    ArrayList<Detail> plist;
    DetailAdapter adapter;
    public static DetailListActivity main = null;
    EditText et;
    ArrayList<Detail> slist;
    int pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        pid = getIntent().getIntExtra("pid", 0);
        main = this;
        iservice = new DetailDBService(this);
        lv = (ListView) findViewById(R.id.listview);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        iv_me = (ImageView) findViewById(R.id.iv_me);
        iv_add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailListActivity.this, AddDetailActivity.class).putExtra("pid", pid));

            }
        });
        iv_me.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(DetailListActivity.this).setTitle("选项")
                        .setItems(new String[]{"编辑", "删除", "取消"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                dialogInterface.dismiss();
                                switch (j) {


                                    case 0:
                                        startActivity(new Intent(DetailListActivity.this, EditDetailActivity.class).putExtra("pic",
                                                plist.get(position)));

                                        break;
                                    case 1:
                                        int pid = plist.get(position).getPid();
                                        iservice.delete(pid);
                                        T.Show(DetailListActivity.this, "删除成功");
                                        search(et.getText().toString());
                                        break;

                                }
                            }

                        }).show();

            }
        });


        et = (EditText) findViewById(R.id.et);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                search(str);
            }
        });


    }

    private void search(String gjz) {
        if (gjz.equals("")) {
            slist = iservice.search(pid);
            plist = slist;
            if (adapter == null) {
                adapter = new DetailAdapter(DetailListActivity.this, plist);
                lv.setAdapter(adapter);
            } else {
                adapter.setList(plist);
            }
        } else {
            slist = iservice.search(gjz, pid);
            plist = slist;
            if (adapter == null) {
                adapter = new DetailAdapter(DetailListActivity.this, plist);
                lv.setAdapter(adapter);
            } else {
                adapter.setList(plist);
            }
        }


    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        search(et.getText().toString());
    }


}
