package com.example.accountbook;

import java.sql.Date;
import java.text.SimpleDateFormat;
import com.example.accountbook.db.DBHelper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class IncomeAccountPageActivity extends Activity {

	//收入选项
	private static final String[] mIncomeCategory = {
		"工资收入","奖金","其他"
	};
	
	DBHelper dbHelper;
	
	private Spinner mSpinnerView;
	private ArrayAdapter<String> mAdapter;
	
	private EditText mEditMoney;
	private EditText mEditTime;
	private EditText mEditTips;
	private Button mBtnSave;
	private Context context;
	
	//
	private String mCurrentCategory;
	private String mCurrTime;
	private float mCurrMoney;
	private String mCurrTips;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.income_account_page);
		
		//测试
		/*TextView textView = new TextView(this);
		textView.setText("This is income tab");
		setContentView(textView);*/
		
		mSpinnerView = (Spinner) findViewById(R.id.spin_income_category);
		mEditTime = (EditText) findViewById(R.id.edit_income_time);
		mEditMoney = (EditText) findViewById(R.id.edit_income_money);
		mEditTips = (EditText) findViewById(R.id.edit_income_tips);
		
		mBtnSave = (Button) findViewById(R.id.btn_income_save);
		dbHelper = new DBHelper(IncomeAccountPageActivity.this);
		
		initViews();
		
		mBtnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//测试
				//dbHelper.insert(dbHelper.TABLE_NAME_EXPENSE, (float) 11.2, "餐饮", "2015-04-14 22:06", "测试数据库");
				
				//获取当前的数额和备注信息
				mCurrMoney = Float.parseFloat(mEditMoney.getText().toString()) ;
				mCurrTips = mEditTips.getText().toString();
				
				//将当前页面信息插入数据库中
				//dbHelper.insert(DBHelper.TABLE_NAME_EXPENSE, mCurrMoney, mCurrentCategory, mCurrTime, mCurrTips);
			}
		});
		
		
	}

	
	/**
	 * 初始化控件相关项
	 */
	private void initViews() {
		// TODO Auto-generated method stub
		// 将可选内容与ArrayAdapter连接起来
		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mIncomeCategory);

		// 设置下拉列表的风格
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter添加到spinner中
		mSpinnerView.setAdapter(mAdapter);

		// 添加spinner的选中事件监听
		mSpinnerView.setOnItemSelectedListener(new SpinnerSelectedListener());

		// 设置默认值
		mSpinnerView.setVisibility(View.VISIBLE);
		
		
		//设置时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日   HH:mm ");
		Date curDate = new Date(System.currentTimeMillis());
		mEditTime.setText(format.format(curDate));
		mCurrTime = format.format(curDate).toString();
		
	}

	class SpinnerSelectedListener implements OnItemSelectedListener{


		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			mCurrentCategory = mIncomeCategory[position];
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}

		
		
	}
	
	
	
}
