package com.example.accountbook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.accountbook.bean.ListItemBean;
import com.example.accountbook.db.DBHelper;
import com.example.accountbook.tools.ExpenseAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ExpenseListViewActivity extends Activity {
	
	DBHelper dbHelper;
	
	ListView mViewExpense;
	ExpenseAdapter mAdapter;
	List<ListItemBean> mDataList = new ArrayList<ListItemBean>();
	
	String mShortDate;
	String mLongDate;
	String mWeek;
	int resId;
	String mCategory;
	String mMoney;
	

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expenselist);
		
		dbHelper = new DBHelper(this);
		
		//获取最近一周中每天的消费记录
		getData();
		
		showListView(mDataList);
		
		/**
		 * ListView中item的点击事件
		 */
		mViewExpense.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ListItemBean item = (ListItemBean) mViewExpense.getItemAtPosition(position);
				String date = item.itemLongDate;
				String category = item.itemCategory;
				float money = item.itemMoney;
				//Toast.makeText(getApplicationContext(), date + " " + category + " " + money, Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent();
				intent.setClass(ExpenseListViewActivity.this, ExpenseAccountPageActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putFloat("money", money);
				bundle.putString("date", date);
				bundle.putString("category", category);
				
				intent.putExtras(bundle);
				startActivity(intent);
				
			}
		});
		
	}

	private void showListView(List<ListItemBean> mDataList2) {
		// TODO Auto-generated method stub
		if(mAdapter == null){
			mViewExpense = (ListView) findViewById(R.id.lv_expense);
			mAdapter = new ExpenseAdapter(this, mDataList2);
			mViewExpense.setAdapter(mAdapter);
		}else {
			mAdapter.onDateChanged(mDataList2);
		}
	}

	
	
	/**
	 * 获取最近一周的消费记录
	 */
	private void getData() {
		// TODO Auto-generated method stub
		
		String today;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date currDay = new Date(System.currentTimeMillis());
		//Calendar calendar = Calendar.getInstance();
		today = format.format(currDay);
		String dayTemp;
		for(int i=0; i<7; i++){
			dayTemp = getSpecifiedDayBefore(today, -i);
			
			//获取此日期下的消费记录
			dbHelper.getDayList(DBHelper.TABLE_NAME_EXPENSE, dayTemp, mDataList);
		}
		
	}
	
	
	
	/**
	 * 获取与给定日期相差balance天的日期，返回日期的格式为yyyy-MM-dd
	 * 与MainActivity中的同名方法功能相同，只是返回日期的格式不同，带上年份，是为了计算对应的星期几
	 * @param specifiedDay
	 * @param balance
	 * @return
	 */
	private static String getSpecifiedDayBefore(String specifiedDay, int balance){
		Calendar calendar = Calendar.getInstance();		//得到日历
		Date date = null;
		try {
			date =  new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//把给定日期时间赋给日历
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		
		//设置相差balance的日期
		calendar.set(Calendar.DATE, day + balance);
		
		//格式化日期	并转换为String
		String dayResult = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		
		return dayResult;
		
	}
	
	
	

	

}
