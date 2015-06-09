package com.example.accountbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 支出列表视图类
 * 注意显示时间时去掉年份
 * 时间到星期的转换
 * @author lsp-2015
 *
 */

public class ExpenseListView extends ListView {

	public ExpenseListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public ExpenseListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ExpenseListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("NewApi")
	public ExpenseListView(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
