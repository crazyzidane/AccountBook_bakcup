package com.example.accountbook.bean;

public class ListItemBean {
	
	//日期格式为MM-dd
	public String itemShortDate;
	
	//完整日期格式
	public String itemLongDate;
	
	public String itemWeek;

	public int itemImageResId;
	public String itemCategory;
	public float itemMoney;
	
	
	public ListItemBean(String itemShortDate, String itemLongDate, String itemWeek, int itemImageResId,
			String itemCategory, float itemMoeny) {
		super();
		this.itemShortDate = itemShortDate;
		this.itemLongDate = itemLongDate;
		this.itemWeek = itemWeek;
		this.itemImageResId = itemImageResId;
		this.itemCategory = itemCategory;
		this.itemMoney = itemMoeny;
	}
	
	
	
}
