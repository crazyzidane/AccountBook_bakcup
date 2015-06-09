package com.example.accountbook.tools;

import java.util.List;

import com.example.accountbook.bean.ListItemBean;

import com.example.accountbook.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpenseAdapter extends BaseAdapter {
	
	private List<ListItemBean> mDataList;
	private LayoutInflater mInflater;
	
	

	public ExpenseAdapter(Context context, List<ListItemBean> list) {
		// TODO Auto-generated constructor stub
		mDataList = list;
		mInflater = LayoutInflater.from(context);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.listitem, null);
			holder = new ViewHolder();
			holder.date = (TextView) convertView.findViewById(R.id.txt_date);
			holder.week = (TextView) convertView.findViewById(R.id.txt_week);
			holder.img = (ImageView) convertView.findViewById(R.id.iv_image);
			holder.category = (TextView) convertView.findViewById(R.id.tv_category);
			holder.money = (TextView) convertView.findViewById(R.id.tv_money);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		ListItemBean bean = mDataList.get(position);
		
		//显示时间时可以去掉年份
		holder.date.setText(bean.itemShortDate);
		
		//根据时间转换星期
		holder.week.setText(bean.itemWeek);
		
		holder.img.setBackgroundResource(bean.itemImageResId);
		holder.category.setText(bean.itemCategory);
		holder.money.setText(String.valueOf(bean.itemMoney));
		
		return convertView;
		
	}
	
	class ViewHolder{
		
		TextView date;
		TextView week;
		ImageView img;
		TextView category;
		TextView money;
	}

	public void onDateChanged(List<ListItemBean> mDataList2) {
		// TODO Auto-generated method stub
		this.mDataList = mDataList2;
		this.notifyDataSetChanged();
	}

}
