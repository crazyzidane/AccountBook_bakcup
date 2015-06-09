package com.example.accountbook.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.accountbook.R;
import com.example.accountbook.bean.ListItemBean;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.session.PlaybackState.CustomAction;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "account_db";
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME_EXPENSE = "expense_table";
	public final static String TABLE_NAME_INCOME = "income_table";
	
	public static final String EXPENSE_ID = "expense_id";
	public static final String EXPENSE_MONEY = "expense_money";
	public static final String EXPENSE_TIME = "expense_time";
	public static final String EXPENSE_CATEGORY = "expense_category";
	public static final String EXPENSE_TIPS = "expense_tips";
	
	public float mSumMoney;
	public float mTheDayMoeny;
	
	
	private String itemShortDate;
	private String itemLongDate;
	private String itemWeek;
	private int itemImageResId;
	private String itemCategory;
	private float itemMoeny;
	

	/**
	 * 在构造函数中进行数据库的创建
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		//创建支出表
		String sql = "Create table " + TABLE_NAME_EXPENSE + " (" + EXPENSE_ID + " integer primary key autoincrement, " + 
						EXPENSE_MONEY + " real, " + EXPENSE_CATEGORY + " text, " + EXPENSE_TIME + " text, " + 
						EXPENSE_TIPS + " text );";
		db.execSQL(sql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//更新支出表
		String sql = " DROP TABLE IF EXISTS " + TABLE_NAME_EXPENSE;
		db.execSQL(sql);
		onCreate(db);
	}
	
	/**
	 * 查询表中的所有条目
	 * @param tableName
	 * @return
	 */
	
	public void queryAll(String tablename){
		SQLiteDatabase db = this.getReadableDatabase();
		//Cursor cursor = db.query(tableName, null, null, null, null, null, " _id desc");
		//Cursor cursor = db.query(tableName, new String[]{"expense_money"},"expense_id>=?", new String[]{"0"}, null, null, null);
		
		//Cursor cursor = db.query(true, tableName, new String[]{"expense_money"}, null, null, null, null, null, null, null);
		
		mSumMoney = 0;
		Cursor cursor = db.rawQuery("select expense_money from " + tablename, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			float currMoney = cursor.getFloat(0);
			mSumMoney += currMoney;
			cursor.moveToNext();
		}
		//System.out.println("wocaaaaaaaaaaaaaaaaaa" + mSumMoney);
		cursor.close();
	}
	
	
	
	
	/**
	 * 查询某天的支出或收入总额
	 * @param tableName
	 * @param currentDate
	 */
	public void queryDayMoney(String tableName, String currentDate){
		mTheDayMoeny = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		String current_sql_sel = "select expense_money from " + tableName +
									" where " + EXPENSE_TIME + " like '%" + 
									currentDate + "%'";
		
		Cursor cursor = db.rawQuery(current_sql_sel, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			float currMoney = cursor.getFloat(0);
			mTheDayMoeny += currMoney;
			cursor.moveToNext();
		}
		cursor.close();
	}
	
	
	
	/**
	 * 获取指定日期的支出列表，放入list中
	 * @param tableName
	 * @param theDate
	 * @param mList
	 */
	public void getDayList(String tableName, String theDate, List<ListItemBean> mList){
		SQLiteDatabase db = this.getReadableDatabase();
		String the_sql_sel = "select * from " + tableName +
								" where " + EXPENSE_TIME + " like '%" + 
								theDate + "%'";
		Cursor cursor = db.rawQuery(the_sql_sel, null);
		ListItemBean mListItem = new ListItemBean(itemShortDate, itemLongDate, itemWeek, itemImageResId, itemCategory, itemMoeny);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//String date = cursor.getString(cursor.getColumnIndex("expense_time"));
			String date = theDate;
			
			mListItem.itemLongDate = cursor.getString(cursor.getColumnIndex("expense_time"));
			
			//日期格式化，只显示月和日
			mListItem.itemShortDate = dateFormat(date);
			
			//获取日期对应的星期几
			mListItem.itemWeek = getWeekfromDate(date);
			
			//暂时的哈，稍后找几个好看的图片
			mListItem.itemImageResId = R.drawable.ic_launcher;
			
			mListItem.itemCategory = cursor.getString(cursor.getColumnIndex("expense_category"));
			mListItem.itemMoney = cursor.getFloat(cursor.getColumnIndex("expense_money"));
			
			
			mList.add(mListItem);
			
			cursor.moveToNext();
		}
		
		cursor.close();
		
		
	}
	
	
	/**
	 * 根据当前日期获取对应的星期几
	 * @param currDate
	 * @return
	 */
	public String getWeekfromDate(String currDate){
		Calendar calendar = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(currDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		calendar.setTime(date);
		
		String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		int week_index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		
		//索引为0或小于0时为星期日
		if(week_index < 0){
			week_index = 0;
		}
		
		return weeks[week_index];
		
	}
	
	
	/**
	 * 将指定日期转换为MM-dd的格式
	 * @param currDate
	 * @return
	 */
	public String dateFormat(String currDate){
		Calendar calendar = Calendar.getInstance();		//得到日历
		Date date = null;
		try {
			date =  new SimpleDateFormat("yyyy-MM-dd").parse(currDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//把给定日期时间赋给日历
		calendar.setTime(date);
		
		//格式化日期	并转换为String
		String dayResult = new SimpleDateFormat("MM-dd").format(calendar
				.getTime());

		return dayResult;
		
	}
	
	
	
	/**
	 * 插入数据
	 * @param tableName
	 * @param money
	 * @param category
	 * @param dateTime
	 * @param tips
	 */
	public void insert(String tableName, float money, String category, String dateTime, String tips){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(EXPENSE_MONEY, money);
		contentValues.put(EXPENSE_CATEGORY, category);
		contentValues.put(EXPENSE_TIME, dateTime);
		contentValues.put(EXPENSE_TIPS, tips);
		db.insert(tableName, null, contentValues);
	}
	
	
	/**
	 * 删除相应表中对应id的条目
	 * @param tableName
	 * @param id
	 */
	public void delete(String tableName, int id){
		String tempId = null;
		if(tableName == TABLE_NAME_EXPENSE){
			tempId = EXPENSE_ID;
		}else {
			
		}
		SQLiteDatabase db = this.getWritableDatabase();
		String where = tempId + "=?";
		String[] whereValues = {Integer.toString(id)};
		db.delete(tableName, where, whereValues);
	}
	
	/**
	 * 更新某表
	 * @param tableName
	 * @param id
	 * @param money
	 * @param category
	 * @param dateTime
	 * @param tips
	 */
	public void update(String tableName, int id, float money, String category, String dateTime, String tips){
		String tempId = null;
		if(tableName == TABLE_NAME_EXPENSE){
			tempId = EXPENSE_ID;
		}else {
			
		}
		SQLiteDatabase db = this.getWritableDatabase();
		String where = tempId + "=?";
		String[] whereValues = {Integer.toString(id)};
		ContentValues contentValues = new ContentValues();
		contentValues.put(EXPENSE_MONEY, money);
		contentValues.put(EXPENSE_CATEGORY, category);
		contentValues.put(EXPENSE_TIME, dateTime);
		contentValues.put(EXPENSE_TIPS, tips);
		db.update(tableName, contentValues, where, whereValues);
	}
	

}
