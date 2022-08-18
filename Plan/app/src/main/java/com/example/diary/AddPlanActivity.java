package com.example.diary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.db.PlanDBService;
import com.example.util.T;

import java.util.Calendar;

/**
 * 添加计划界面
 *
 * @author Administrator
 */
public class AddPlanActivity extends BaseActivity {

    EditText et_content, editText_title;
    TextView tv_fb, etBQ;
    PlanDBService service;
    Calendar calendar;
    int y, m, d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        calendar = Calendar.getInstance();
        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);
        d = calendar.get(Calendar.DAY_OF_MONTH);

        service = new PlanDBService(this);


        editText_title = (EditText) findViewById(R.id.editText_title);
        tv_fb = (TextView) findViewById(R.id.tv_fabiao_btn);
        tv_fb.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String title = editText_title.getText().toString();
                String content = et_content.getText().toString();
                String tq = etBQ.getText().toString();
                if (title.equals("") || content.equals("") || tq.equals("")) {
                    T.Show(AddPlanActivity.this, "所填内容不能为空");
                    return;
                }
                if (service.save(title, content, tq)) {
                    T.Show(AddPlanActivity.this, "添加成功");
                    finish();
                } else {
                    T.Show(AddPlanActivity.this, "添加失败");
                }
            }
        });
        findViewById(R.id.layout_back).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });

        et_content = (EditText) findViewById(R.id.editText_content);
        etBQ = (TextView) findViewById(R.id.etBQ);
        etBQ.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker = new DatePicker(AddPlanActivity.this);
                datePicker.init(y, m, d, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        y = i;
                        m = i1;
                        d = i2;
                    }
                });
                new AlertDialog.Builder(AddPlanActivity.this).setTitle("选择截止时间").setView(datePicker).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        etBQ.setText(y + "-" + (m + 1) + "-" + d);
                    }
                }).setNegativeButton("取消", null).show();


            }
        });

    }


}
