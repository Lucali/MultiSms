package com.vincent.multisms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LocalSmsList extends ListActivity {
	/** Called when the activity is first created. */

//	private Handler handler;
	/** 标识List的滚动状态。 */

	//private List<Map<String, Object>> mData;
	private List<Map<String, String>> mData;
	private ArrayList<SmsInfo> smsdata;
	ArrayList<ContactPeopleInfo> peopleinfos;

	private MyAdapter adapter;


	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.localsmslist);
		Intent intent = getIntent();
		peopleinfos = (ArrayList<ContactPeopleInfo>) intent.getSerializableExtra("ddata");

		smsdata = getData();
		mData = buildHashmap(smsdata);
	
		adapter = new MyAdapter(this);
		setListAdapter(adapter);
	
	
	}

	
	@SuppressLint("SimpleDateFormat")
	private ArrayList<SmsInfo> getData() {
		ArrayList<SmsInfo> smsinfos = new ArrayList<SmsInfo>();
		SmsInfo smsinfo;

		// 获得所有短信

		Cursor	cur = managedQuery(Uri.parse("content://sms/"), new String[] {"person", "body","date"}, null, null,"date desc");
		// 循环遍历
		if (cur.moveToFirst()) {

	         int nameColumn = cur.getColumnIndex("person");
	        
	         int smsColumn = cur.getColumnIndex("body");
	         int dateColumn = cur.getColumnIndex("date");
	      

			do {
				   System.out.println("this is "+dateColumn);
				smsinfo = new SmsInfo();
				
				String person = cur.getString(nameColumn);
				String body = cur.getString(smsColumn);
				//String date =formatTimeStampString(this,dateColumn);
					
                SimpleDateFormat dateFormat = new SimpleDateFormat(   
                        "yyyy-MM-dd hh:mm:ss");   
                Date d = new Date(Long.parseLong(cur.getString(dateColumn)));   //最关键的语句实现时间的转换
                String date = dateFormat.format(d);  
				smsinfo.setPerson(person);
				smsinfo.setBody(body);
				smsinfo.setDate(date);	
				smsinfos.add(smsinfo);
			} while (cur.moveToNext());

		}
		cur.close();
		return smsinfos;
	}

	

	private List<Map<String, String>> buildHashmap(
			List<SmsInfo> smsinfos) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		// HashMap<String, String> map ;
		for (Iterator<SmsInfo> iterator = smsinfos.iterator(); iterator.hasNext();) {
			SmsInfo smsinfo = (SmsInfo) iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("body", smsinfo.getBody());
			//map.put("person", smsinfo.getPerson());
			map.put("date", smsinfo.getDate());
			list.add(map);
		}
		return list;
	}



	public final class ViewHolder {

		public TextView sms_info;
		public TextView sms_sentname;
		public TextView sms_senttime;
	}

	public class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		
		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);

		}

		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {

				holder = new ViewHolder();

				convertView = mInflater.inflate(R.layout.smsinfolist, null);

				holder.sms_info = (TextView) convertView
						.findViewById(R.id.smsinfo);
			//	holder.sms_sentname = (TextView) convertView.findViewById(R.id.smssendname);
				holder.sms_senttime = (TextView) convertView
						.findViewById(R.id.smssendtime);
				convertView.setTag(holder);
				
			} else {

				holder = (ViewHolder) convertView.getTag();
				
			}
			
			
			holder.sms_info.setText((String) mData.get(position).get("body"));
			//holder.sms_sentname.setText((String) mData.get(position).get("person")+"  ");
			holder.sms_senttime.setText(mData.get(position).get("date").toString());	

			return convertView;
		}
		
		
		
		
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {

		Intent intent = new Intent();
		intent.putExtra("body", mData.get(position).get("body"));
		intent.putExtra("ddata", peopleinfos);
		intent.setClass(LocalSmsList.this, MultiSms.class);
		startActivity(intent);

	}
	
	public static String formatTimeStampString(Context context, long when) {
        Time then = new Time();
        then.set(when);
        Time now = new Time();
        now.setToNow();

        // Basic settings for formatDateTime() we want for all cases.
        int format_flags = DateUtils.FORMAT_NO_NOON_MIDNIGHT |
                           DateUtils.FORMAT_ABBREV_ALL |
                           DateUtils.FORMAT_CAP_AMPM;

        // If the message is from a different year, show the date and year.
        if (then.year != now.year) {
            format_flags |= DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_DATE;
        } else if (then.yearDay != now.yearDay) {
            // If it is from a different day than today, show only the date.
            format_flags |= DateUtils.FORMAT_SHOW_DATE;
        } else {
            // Otherwise, if the message is from today, show the time.
            format_flags |= DateUtils.FORMAT_SHOW_TIME;
        }



        return DateUtils.formatDateTime(context, when, format_flags);
    }
	
	

}
