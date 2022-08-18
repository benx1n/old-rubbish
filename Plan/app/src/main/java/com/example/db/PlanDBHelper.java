package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 计划数据库
 */
public class PlanDBHelper extends SQLiteOpenHelper {

	public PlanDBHelper(Context context) {
		/**
		 * context 上下文对象 name 数据库文件名 factory 游标工厂 version 版本 不能设置0
		 */
		super(context, "plans.db", null, 1);

	}

	/**
	 * 当数据库被创建的时候调用
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {// 建表
		String sql = "create table plans(pid integer primary key autoincrement,progress integer,content text,time text,title varchar(100))";
		db.execSQL(sql); // 执行一个sql语句

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
