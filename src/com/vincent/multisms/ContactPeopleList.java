package com.vincent.multisms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ContactPeopleList extends ListActivity {
	/** Called when the activity is first created. */

//	private Handler handler;
	/** 标识List的滚动状态。 */

	//private List<Map<String, Object>> mData;
	private List<Map<String, String>> mData;
	private ArrayList<ContactPeopleInfo> ddata;
	private ArrayList<ContactPeopleInfo> ddata_frommainlist;
	private Button select_all;
	private Button select_none;
	private Button makesure;
	private MyAdapter adapter;
	private String tempname = "tempname";
	private String smsbody;
	private int location;
//	private TextView txtOverlay = null;
	//private WindowManager windowManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contactpeoplelist);


		select_all = (Button) findViewById(R.id.select_all);
		select_none = (Button) findViewById(R.id.select_none);

		ddata =  init_ddata();
		mData = buildHashmap(ddata);
		smsbody= init_smsbody();
		changestate();
		adapter = new MyAdapter(this);
		setListAdapter(adapter);
		makesure = (Button) findViewById(R.id.makesure);
		makesure.setOnClickListener(new makesureonclick());
		/**
		handler = new Handler();
		// 初始化首字母悬浮提示框
				txtOverlay = (TextView) LayoutInflater.from(this).inflate(R.layout.txtonlist, null);
				txtOverlay.setVisibility(View.INVISIBLE);
				WindowManager.LayoutParams lp = new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION,
						WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
								| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, PixelFormat.TRANSLUCENT);
				windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
				windowManager.addView(txtOverlay, lp);
		ListView listMain = getListView();
	    listMain.setOnScrollListener(this);
		changeFastScrollerDrawable(listMain);
	    disapearThread = new DisapearThread();
		*/
	    
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<ContactPeopleInfo>  init_ddata(){
		ArrayList<ContactPeopleInfo> peopleinfos = new ArrayList<ContactPeopleInfo>();
		Intent intent = getIntent();
		ddata_frommainlist = (ArrayList<ContactPeopleInfo>) intent.getSerializableExtra("ddata");
		if (ddata_frommainlist==null)
		{peopleinfos = getData();
		}
		else{
			peopleinfos = ddata_frommainlist;
		}
		return peopleinfos;
	}
	
	private ArrayList<ContactPeopleInfo> getData() {
		ArrayList<ContactPeopleInfo> peopleinfos = new ArrayList<ContactPeopleInfo>();
		ContactPeopleInfo map;

		// 获得所有的联系人
		Cursor cur = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		// 循环遍历
		if (cur.moveToFirst()) {

			int idColumn = cur.getColumnIndex(ContactsContract.Contacts._ID);
			// System.out.println(idColumn);
			int displayNameColumn = cur
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
			do {
				map = new ContactPeopleInfo();
				// Map<String,String> map = new HashMap<String,String>();
				// 获得联系人的ID号
				String contactId = cur.getString(idColumn);
				map.setId(contactId);
				// 获得联系人姓名
				String disPlayName = cur.getString(displayNameColumn);

				map.setPeoplename(disPlayName);
				map.setPeoplesent(disPlayName);
				map.setIsSelected(false);
				// map.put("disPlayName",disPlayName);
				// 查看该联系人有多少个电话号码。如果没有这返回值为0
				int phoneCount = cur
						.getInt(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				if (phoneCount > 0) {
					// 获得联系人的电话号码
					Cursor phones = getContentResolver().query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = " + contactId, null, null);
					if (phones.moveToFirst()) {
						do {
							// 遍历所有的电话号码
							String phoneNumber = phones
									.getString(phones
											.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							map.setPeoplenum(phoneNumber);

							// map.put("phoneNumber", phoneNumber);

						} while (phones.moveToNext());
					}

				}

				peopleinfos.add(map);
			} while (cur.moveToNext());

		}
		cur.close();
		return peopleinfos;
	}

	private String init_smsbody(){
		String sms_body;
		Intent intent = getIntent();
		sms_body = intent.getStringExtra("body");
		return sms_body;
	}

	class makesureonclick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.putExtra("ddata", ddata);
			intent.putExtra("body", smsbody);
			//System.out.println(smsbody);
			intent.setClass(ContactPeopleList.this, MultiSms.class);
			startActivity(intent);

		}

	}

	private List<Map<String, String>> buildHashmap(
			List<ContactPeopleInfo> peopleinfos) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		// HashMap<String, String> map ;
		for (Iterator<ContactPeopleInfo> iterator = peopleinfos.iterator(); iterator.hasNext();) {
			ContactPeopleInfo peopleinfo = (ContactPeopleInfo) iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("disPlayName", peopleinfo.getPeoplename());
			map.put("phoneNumber", peopleinfo.getPeoplenum());
			list.add(map);
		}
		return list;
	}

	private void changestate() {
		select_all.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				for (int i = 0; i < ddata.size(); i++) {
					ddata.get(i).setIsSelected(true);
					adapter.notifyDataSetChanged();

				}
				
			}
		});
		select_none.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < ddata.size(); i++) {
					ddata.get(i).setIsSelected(false);
					adapter.notifyDataSetChanged();
				}

			}
		});

	}

	// ListView 中某项被选中后的逻辑
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		CheckBox checkbox = (CheckBox) v.findViewById(R.id.checkbox);

		checkbox.setChecked(!checkbox.isChecked());
		ddata.get(position).setIsSelected(checkbox.isChecked());
		if (ddata.get(position).getIsSelected()) {
		//	System.out.println("it's true");
		}

	}

	/**
	 * listview中点击按键弹出对话框
	 */
	public void editSentName(int position) {
		final EditText temptext = new EditText(this) ;
		location = position;
		new AlertDialog.Builder(this).setTitle("请在下面的对话框中输入您要发送的名字")
				.setView(temptext)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					tempname = temptext.getText().toString();
					System.out.println(location);
					System.out.println("1"+ddata.get(location).getPeoplesent());
					ddata.get(location).setPeoplesent(tempname);
					System.out.println("2"+ddata.get(location).getPeoplesent());
					}
				}).setNegativeButton("取消", null).show();
		
	}

	public final class ViewHolder {

		public TextView phone_name;
		public EditText phone_name2;
		public CheckBox checkbox;
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

				convertView = mInflater.inflate(R.layout.peoplelist, null);
				// holder.img = (ImageView)convertView.findViewById(R.id.img);
				holder.phone_name = (TextView) convertView
						.findViewById(R.id.phone_name);
				holder.phone_name2 = (EditText) convertView
						.findViewById(R.id.phone_name2);
				holder.checkbox = (CheckBox) convertView
						.findViewById(R.id.checkbox);
				convertView.setTag(holder);
				
			} else {

				holder = (ViewHolder) convertView.getTag();
				
			}

			holder.phone_name.setText((String) mData.get(position).get(
					"disPlayName"));
			holder.phone_name2.setText((String) ddata.get(position).getPeoplesent());
			holder.checkbox.setChecked(ddata.get(position).getIsSelected());
			
			holder.phone_name2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
					editSentName(position);
				
					//System.out.println("3"+ddata.get(location).getPeoplesent());
					
					notifyDataSetChanged();
				}
				
				
			});

			holder.checkbox.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (ddata.get(position).getIsSelected()) {
						ddata.get(position).setIsSelected(false);
					} else {
						ddata.get(position).setIsSelected(true);
					}
					notifyDataSetChanged();
				}
			});

			return convertView;
		}
		
		
	}


	/** ListView.OnScrollListener */
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
			int totalItemCount) {
		// 以中间的ListItem为标准项。
	//	txtOverlay.setText(String.valueOf(stringArr[firstVisibleItem + (visibleItemCount >> 1)].charAt(0)));
	}

	
	/**
	/** ListView.OnScrollListener 
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		this.scrollState = scrollState;
		if (scrollState == ListView.OnScrollListener.SCROLL_STATE_IDLE) {
			handler.removeCallbacks(disapearThread);
			// 提示延迟1.5s再消失
			boolean bool = handler.postDelayed(disapearThread, 1500);
			Log.d("ANDROID_INFO", "postDelayed=" + bool);
		} else {
			txtOverlay.setVisibility(View.VISIBLE);
		}
	}

	private class DisapearThread implements Runnable {
		public void run() {
			// 避免在1.5s内，用户再次拖动时提示框又执行隐藏命令。
			if (scrollState == ListView.OnScrollListener.SCROLL_STATE_IDLE) {
				txtOverlay.setVisibility(View.INVISIBLE);
			}
		}
	}

	public void onDestroy() {
		super.onDestroy();
		// 将txtOverlay删除。
		txtOverlay.setVisibility(View.INVISIBLE);
		windowManager.removeView(txtOverlay);
		
	}
	*/
}
