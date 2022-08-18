package com.example.diary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.bean.Plane;
import com.example.bean.PlaneAdapter;
import com.example.db.PlanDBService;
import com.example.util.T;

import java.util.ArrayList;

/**
 * 计划列表界面
 *
 * @author Administrator
 */
public class PlanListActivity extends BaseActivity {

    ListView lv;
    ImageView iv_add, iv_me;
    PlanDBService iservice;
    ArrayList<Plane> plist;
    PlaneAdapter adapter;
    public static PlanListActivity main = null;
    ArrayList<Plane> slist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);
        main = this;
        iservice = new PlanDBService(this);
        lv = (ListView) findViewById(R.id.listview);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        iv_me = (ImageView) findViewById(R.id.iv_me);
        iv_add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanListActivity.this, AddPlanActivity.class));

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

                new AlertDialog.Builder(PlanListActivity.this).setTitle("选项")
                        .setItems(new String[]{"查看", "删除", "取消"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                dialogInterface.dismiss();
                                switch (j) {

                                    case 0:
                                        startActivity(new Intent(PlanListActivity.this, DetailListActivity.class).putExtra("pid",
                                                plist.get(position).getPid()));
                                        break;

                                    case 1:
                                        int pid = plist.get(position).getPid();
                                        iservice.delete(pid);
                                        T.Show(PlanListActivity.this, "删除成功");
                                        search();
                                        break;
                                }
                            }

                        }).show();
            }
        });
    }

    private void search() {
        slist = iservice.search();

        plist = slist;
        if (adapter == null) {
            adapter = new PlaneAdapter(PlanListActivity.this, plist);
            lv.setAdapter(adapter);
        } else {
            adapter.setList(plist);
        }


    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        search();
    }


}
