
package com.example.accountbook;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TabHost;

/**
 * 支出和收入的切换选项卡
 * @author lsp-2015
 *
 */


public class AccountPagesTabWidget extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_account_pages);
		
		Resources res = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;
		
		intent = new Intent().setClass(AccountPagesTabWidget.this, ExpenseAccountPageActivity.class);
		spec = tabHost.newTabSpec("expense").setIndicator("支出").setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(AccountPagesTabWidget.this, IncomeAccountPageActivity.class);
		spec = tabHost.newTabSpec("income").setIndicator("收入").setContent(intent);
		tabHost.addTab(spec);
		
		//tabHost.setCurrentTab(1);
		
	}

	
	
}
