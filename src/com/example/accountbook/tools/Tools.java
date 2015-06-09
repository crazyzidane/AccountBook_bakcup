package com.example.accountbook.tools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

public class Tools 
{
	private final static String ALBUM_PATH  = Environment.getExternalStorageDirectory() + "/yueqiu/";
	
    public  int dip2px(Context context, float dpValue) 
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    
   
    public  int px2dip(Context context, float pxValue) 
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    
    
    /**
     * 获取map中的key，放入list中，并对list进行排序，从小到大
     * @param map
     * @return
     */
    public ArrayList<Double> getintfrommap(HashMap<Double, Double> map)
    {
    	ArrayList<Double> dlk=new ArrayList<Double>();
    	int position=0;
    	@SuppressWarnings("rawtypes")
    	
    	//返回此映射中包含的映射关系的Set视图
		Set set= map.entrySet();   
        @SuppressWarnings("rawtypes")
		Iterator iterator = set.iterator();
 
		 while(iterator.hasNext())
		{   
			@SuppressWarnings("rawtypes")
			Map.Entry mapentry  = (Map.Entry)iterator.next();   
			dlk.add((Double)mapentry.getKey());
		} 
		 
		 //对map中的key值进行排序，从小到大
		 for(int i=0;i<dlk.size();i++)
		 {
			 int j=i+1;  
	            position=i;  
	            Double temp=dlk.get(i);  
	            for(;j<dlk.size();j++)
	            {  
	            	if(dlk.get(j)<temp)
	            	{  
	            		temp=dlk.get(j);  
	            		position=j;  
	            	}  
	            }  
	            
	            dlk.set(position,dlk.get(i)); 
	            dlk.set(i,temp);  
		 }
		return dlk;
    	
    }

    /**
     * 锟斤拷锟斤拷图片
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public Boolean saveFile(Bitmap bm, String fileName) throws IOException {   
    	File dirFile = new File(ALBUM_PATH); 
    	fileName=fileName.replace("/", "_");
        if(!dirFile.exists())
        {   
            dirFile.mkdir();   
        }   
        File myCaptureFile = new File(ALBUM_PATH + fileName);   
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));   
        Boolean b=bm.compress(Bitmap.CompressFormat.PNG, 80, bos);   
        bos.flush();   
        bos.close();  
        return b;
    }
}
