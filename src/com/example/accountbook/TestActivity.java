package com.example.accountbook;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;

import com.example.accountbook.ExpenseTrendView;
import com.example.accountbook.R;

import com.example.accountbook.tools.Tools;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestActivity extends Activity {


	ExpenseTrendView tu;
	//Button BT_Add;
	Timer mTimer =new Timer();
	HashMap<String, Float> map;
	Double key=8.0;
	Double value=0.0;
	Tools tool=new Tools();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		//BT_Add=(Button)findViewById(R.id.bt_add);
		tu= (ExpenseTrendView)findViewById(R.id.menulist);
		tu.SetTuView(map,50,10,"","元",false);	//设置绘图的相关初始参数
		map=new HashMap<String, Float>();
		map.put("01-02", (float) 0);	//初始图的坐标值
    	map.put("01-03", 3.23f);
    	map.put("01-04", 32.0f);
    	map.put("01-05", 41.0f);
    	map.put("01-06", 16.0f);
    	map.put("01-07", 36.0f);
    	map.put("01-08", 26.0f);
    	tu.setTotalvalue(50);	
    	tu.setPjvalue(10);
    	tu.setMap(map);
//		tu.setXstr("");
//		tu.setYstr("");
    	
		//tu.setMargint(20);
		//tu.setMarginb(50);
		
    	//tu.setMstyle(Mstyle.Line);
	
		//BT_Add.setOnClickListener(new click());
	}

/*	class click implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			Random rd=new Random(System.currentTimeMillis());
			Double temp= rd.nextDouble();
			randmap(map, temp*50);
		}}*/
	

}
