package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 详情数据库
 */
public class DetailDBHelper extends SQLiteOpenHelper {

	public DetailDBHelper(Context context) {
		/**
		 * context 上下文对象 name 数据库文件名 factory 游标工厂 version 版本 不能设置0
		 */
		super(context, "detail.db", null, 1);

	}

	/**
	 * 当数据库被创建的时候调用
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {// 建表
		String sql = "create table detail(did integer primary key autoincrement,progress integer,image text,content text,time text,pid integer,xq varchar(50),tq varchar(50),title varchar(100))";
		db.execSQL(sql); // 执行一个sql语句

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
