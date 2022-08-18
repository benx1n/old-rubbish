package com.example.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bean.Plane;

import java.util.ArrayList;

public class PlanDBService {

    private PlanDBHelper helperss;

    public PlanDBService(Context context) {
        helperss = new PlanDBHelper(context);
    }

    /**
     * 保存计划信息
     *
     * @param
     */
    public boolean save(String title, String content, String time) {
        SQLiteDatabase db = helperss.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("content", content);
        values.put("time", time);
        values.put("title", title);
        values.put("progress", 0);
        long i = db.insert("plans", null, values); // 第一个参数是数据表名字，第二个字段是允许为空的字段的名字
        db.close();
        if (i == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 查询所有计划
     *
     * @return
     */
    public ArrayList<Plane> search() {
        ArrayList<Plane> list = new ArrayList<Plane>();
        SQLiteDatabase db = helperss.getReadableDatabase();
        Cursor cursor = db.query("plans", null, null, null, null, null, null, null);// 第二个字段如果是null，返回数据库里所有属性
        while (cursor.moveToNext()) {

            int pid = cursor.getInt(cursor.getColumnIndex("pid"));
            int progress = cursor.getInt(cursor.getColumnIndex("progress"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            Plane p = new Plane(pid, content, time, title);
            p.setProgress(progress);
            list.add(p);

        }
        db.close();
        return list;
    }

    //模糊搜索计划
    public ArrayList<Plane> search(String gjz) {
        ArrayList<Plane> list = new ArrayList<Plane>();
        SQLiteDatabase db = helperss.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from plans where title like '%" + gjz + "%' or content like '%" + gjz + "%' or xq like '%" + gjz + "%' or time like '%" + gjz + "%'", null);
        while (cursor.moveToNext()) {

            int pid = cursor.getInt(cursor.getColumnIndex("pid"));
            int progress = cursor.getInt(cursor.getColumnIndex("progress"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            Plane p = new Plane(pid, content, time, title);
            p.setProgress(progress);
            list.add(p);


        }
        db.close();
        return list;
    }


    /**
     * 修改数据库表
     *
     * @param
     * @param
     */
    public void update(Plane sj) {
        SQLiteDatabase db = helperss.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("content", sj.getContent());
        values.put("title", sj.getTitle());
        values.put("time", sj.getTime());

        db.update("plans", values, "pid=?", new String[]{sj.getPid() + ""});
        db.close();
    }

    public void update(int pid, int progress) {
        SQLiteDatabase db = helperss.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("progress", progress);

        db.update("plans", values, "pid=?", new String[]{pid + ""});
        db.close();
    }

    /**
     * 删除数据库表
     *
     * @param id
     * @return
     */
    public void delete(int id) {
        SQLiteDatabase db = helperss.getWritableDatabase();
        db.delete("plans", "pid=?", new String[]{id + ""});
        db.close();
    }

}
