package com.example.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bean.Detail;

import java.util.ArrayList;

public class DetailDBService {

    private DetailDBHelper helperss;

    public DetailDBService(Context context) {
        helperss = new DetailDBHelper(context);
    }

    /**
     * 保存
     *
     * @param
     */
    public boolean save(Detail a) {
        SQLiteDatabase db = helperss.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pid", a.getPid());
        values.put("progress", a.getProgress());
        values.put("image", a.getImage());
        values.put("content", a.getContent());
        long i = db.insert("detail", null, values); // 第一个参数是数据表名字，第二个字段是允许为空的字段的名字
        db.close();
        if (i == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 查询所有计划详情
     *
     * @return
     */
    public ArrayList<Detail> search(int pid) {
        ArrayList<Detail> list = new ArrayList<Detail>();
        SQLiteDatabase db = helperss.getReadableDatabase();
        Cursor cursor = db.query("detail", null, "pid = ?", new String[]{pid + ""}, null, null, null, null);// 第二个字段如果是null，返回数据库里所有属性
        while (cursor.moveToNext()) {

            int did = cursor.getInt(cursor.getColumnIndex("did"));
            int progress = cursor.getInt(cursor.getColumnIndex("progress"));
            String image = cursor.getString(cursor.getColumnIndex("image"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            Detail p = new Detail(did, pid, image, content, progress);
            list.add(p);

        }
        db.close();
        return list;
    }

    public int searchTotalBFB(int pid) {
        SQLiteDatabase db = helperss.getReadableDatabase();
        String sql = "select sum(progress) as num from detail where pid="+pid;
        Cursor cursor = db.rawQuery(sql,null);
        //Cursor cursor = db.query("detail", null, "pid = ?", new String[]{pid + ""}, null, null, null, null);// 第二个字段如果是null，返回数据库里所有属性
        if (cursor.moveToNext()) {

            int num = cursor.getInt(cursor.getColumnIndex("num"));
            return num;

        }
        db.close();
        return 0;
    }

    //模糊搜索
    public ArrayList<Detail> search(String gjz, int pid) {
        ArrayList<Detail> list = new ArrayList<Detail>();
        SQLiteDatabase db = helperss.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from detail where pid = " + pid + " and content like '%" + gjz + "%'", null);
        while (cursor.moveToNext()) {

            int did = cursor.getInt(cursor.getColumnIndex("did"));
            int progress = cursor.getInt(cursor.getColumnIndex("progress"));
            String image = cursor.getString(cursor.getColumnIndex("image"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            Detail p = new Detail(did, pid, image, content, progress);
            if (pid == cursor.getInt(cursor.getColumnIndex("pid"))) {
                list.add(p);
            }


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
    public void update(Detail sj) {
        SQLiteDatabase db = helperss.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("content", sj.getContent());
        values.put("image", sj.getImage());
        values.put("progress", sj.getProgress());

        db.update("detail", values, "did=?", new String[]{sj.getPid() + ""});
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
        db.delete("detail", "did=?", new String[]{id + ""});
        db.close();
    }

}
