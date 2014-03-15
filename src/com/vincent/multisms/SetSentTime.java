package com.vincent.multisms;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.Toast;

public class SetSentTime extends Activity{
	
	private Calendar c;
	private int myear ;
	private int mmonth ;
	private int mday ;
	private int mhour;
	private int mmin;
	private DatePicker datepicker;
	private TimePicker timepicker;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setsenttime);
		editsenttime();
		Button setsenttime_makesure = (Button)findViewById(R.id.setsenttime_makesure);
		Button setsenttime_makecancel = (Button)findViewById(R.id.setsenttime_cancel);
		
		setsenttime_makesure.setOnClickListener(new onmakesure());
		setsenttime_makecancel.setOnClickListener(new onmakecancel());
		
		}
		
		private void editsenttime(){
			c = Calendar.getInstance();//初始化日历
			myear = c.get(Calendar.YEAR);
			mmonth = c.get(Calendar.MONTH);
			mday = c.get(Calendar.DAY_OF_MONTH);
			mhour = c.get(Calendar.HOUR_OF_DAY);
			mmin = c.get(Calendar.MINUTE);
			
			datepicker = (DatePicker)findViewById(R.id.datepicker);
			datepicker.init(myear,mmonth,mday,new OnDateChangedListener(){

				@Override
				public void onDateChanged(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					myear = year;
					mmonth = monthOfYear+1;
					mday = dayOfMonth;
					
					
				}
				
			});
			timepicker = (TimePicker)findViewById(R.id.timepicker);
			timepicker.setIs24HourView(true);
			timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
				
				@Override
				public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
					// TODO Auto-generated method stub
					mhour = hourOfDay;
					mmin = minute;
				}
			});
		}
		
		class onmakesure implements OnClickListener{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String senttime = myear+":"+mmonth+":"+mday+"  "+mhour+":"+mmin;
				
				Toast.makeText(SetSentTime.this, "此短信将在"+senttime+"发送", Toast.LENGTH_LONG).show(); 
				
			}
			
		}
		
		class onmakecancel implements OnClickListener{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SetSentTime.this, MultiSms.class);
				startActivity(intent);
			}
			
		}
		
	}	


