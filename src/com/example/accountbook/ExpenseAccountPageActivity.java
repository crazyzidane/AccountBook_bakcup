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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ExpenseAccountPageActivity extends Activity implements OnClickListener {

	//支出选项
	private static final String[] mExpenseCategory = {
		"餐饮","服饰","休闲","其他"
	};
	
	DBHelper dbHelper;
	
	private Spinner mSpinnerView;
	private ArrayAdapter<String> mAdapter;
	
	private EditText mEditMoney;
	private EditText mEditTime;
	private EditText mEditTips;
	private Button mBtnSave;
	private Context context;
	private Button mBtnBack;
	
	private String mCurrentCategory;
	private String mCurrTime;
	private float mCurrMoney;
	
	//支出备注
	private String mCurrTips;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_account_page);
		
		//测试
		/*TextView textView = new TextView(this);
		textView.setText("This is expense tab");
		setContentView(textView);*/
		
		mSpinnerView = (Spinner) findViewById(R.id.spin_expense_category);
		mEditTime = (EditText) findViewById(R.id.edit_expense_time);
		mEditMoney = (EditText) findViewById(R.id.edit_expense_money);
		mEditTips = (EditText) findViewById(R.id.edit_expense_tips);
		
		mBtnSave = (Button) findViewById(R.id.btn_expense_save);
		mBtnBack = (Button) findViewById(R.id.btn_expense_back);
		dbHelper = new DBHelper(ExpenseAccountPageActivity.this);
		
		
		initViews();
		
		
		/**
		 * 如果是点击listview的item进入此activity
		 */
		//取得intent中的bundle对象
		Bundle bundle = this.getIntent().getExtras();
		
		//取得bundle对象中的数据
		if(bundle != null){
			float money = bundle.getFloat("money");
			String date = bundle.getString("date");
			String category = bundle.getString("category");
			
			mEditMoney.setText(String.valueOf(money));
			mEditTime.setText(date);
			
			for(int i=0; i<mExpenseCategory.length; i++){
				if(category.equals(mExpenseCategory[i])){
					mSpinnerView.setSelection(i);
				}
			}
			
		}
		
		
		
		mBtnSave.setOnClickListener(this);
		mBtnBack.setOnClickListener(this);

		
		
	}

	
	/**
	 * 初始化控件相关项
	 */
	private void initViews() {
		// TODO Auto-generated method stub
		// 将可选内容与ArrayAdapter连接起来
		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mExpenseCategory);

		// 设置下拉列表的风格
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter添加到spinner中
		mSpinnerView.setAdapter(mAdapter);

		// 添加spinner的选中事件监听
		mSpinnerView.setOnItemSelectedListener(new SpinnerSelectedListener());

		// 设置默认值
		mSpinnerView.setVisibility(View.VISIBLE);
		
		//设置spinner弹出dialog的标题
		mSpinnerView.setPrompt("支出种类");
		
		//设置时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd   HH:mm ");
		Date curDate = new Date(System.currentTimeMillis());
		mEditTime.setText(format.format(curDate));
		mCurrTime = format.format(curDate).toString();
		
	}

	class SpinnerSelectedListener implements OnItemSelectedListener{


		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			mCurrentCategory = mExpenseCategory[position];
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}

		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		//此处有bug，如果目前是更改现有账目，比如点击listview的item进入，修改之后再保存
		case R.id.btn_expense_save:
			//获取当前的数额和备注信息
			mCurrMoney = Float.parseFloat(mEditMoney.getText().toString()) ;
			mCurrTips = mEditTips.getText().toString();
			
			//将当前页面信息插入数据库中
			dbHelper.insert(DBHelper.TABLE_NAME_EXPENSE, mCurrMoney, mCurrentCategory, mCurrTime, mCurrTips);
			break;
		case R.id.btn_expense_back:
			finish();
			break;

		default:
			break;
		}
	}
	
	
	
}
