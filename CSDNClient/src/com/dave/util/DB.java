package com.dave.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dave.entity.NewsItem;

public class DB extends SQLiteOpenHelper{
    // ���ݿ����Ƴ���
    private static final String DATABASE_NAME = "csdn.db";
    // ���ݿ�汾����
    private static final int DATABASE_VERSION = 1;
    // �����Ƴ���
    public static final String TABLES_NAME = "newsTbl";
	// ���췽��
	public DB(Context context) {
		// �������ݿ�
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		System.out.println("creat DB");
	}

	// ����ʱ���ã������ݿ�����򲻵���
	public void onCreate(SQLiteDatabase db) {
		System.out.println("onCreat DB");
		db.execSQL("CREATE TABLE " + TABLES_NAME + " ("
                + "_ID" + " INTEGER PRIMARY KEY,"
				+ "title" +  " TEXT,"
				+ "content" +  " TEXT,"
                + "date" + " TEXT,"
                + "img" + " TEXT,"
                + "link" + " TEXT,"
                + "newstype" + " INTRGER"
                + ");");
	}

	// �汾����ʱ����
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// ɾ����
		System.out.println("delete DB");
		db.execSQL("DROP TABLE IF EXISTS newsTbl");	
        onCreate(db);	//�����±�
	}
	
	public void insert(List<NewsItem> list){
		SQLiteDatabase db = this.getWritableDatabase();
		for(NewsItem item : list){
			ContentValues values = new ContentValues();
			values.put("title", item.getTitle());
			values.put("content", item.getContent());
			values.put("date", item.getDate());
			values.put("img", item.getImgLink());
			values.put("link", item.getLink());
			values.put("newstype", item.getType());
			
			db.insert("newsTbl", null, values);
		}
	}
	public void delete(int newsType){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from newsTbl where newstype = '" + newsType + "'");
	}
	public List<NewsItem> query(int newsType){
		List<NewsItem> list = new ArrayList<NewsItem>();
		SQLiteDatabase db = this.getReadableDatabase();
		String SQL = "select * from newsTbl where newsType = '" + newsType + "'";
		Cursor cursor = db.rawQuery(SQL, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
	        do{
	        	NewsItem item = new NewsItem();
	        	item.setTitle(cursor.getString(1));
	        	item.setContent(cursor.getString(2));
	        	item.setDate(cursor.getString(3));
	        	item.setImgLink(cursor.getString(4));
	        	item.setLink(cursor.getString(5));
	        	item.setType(newsType);
	        	
	        	list.add(item);
	        }while(cursor.moveToNext());
        }
        cursor.close();
        return list;
	}
}
	
