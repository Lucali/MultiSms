package com.vincent.multisms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MultiSms extends Activity {
	private EditText textphonenum = null;
	private EditText textcontent = null;
	private ArrayList<ContactPeopleInfo> ddata;
	private String phonestr = null;
	private List<String> sendphonenum = null;
	private List<String> sendphonename = null;
	private String init_phonecontent = "[称谓]";
	private String content;
	private String smsbody;
//	private static final int SHOW_CONTACTMAINLIST = 1;
	//private static final int SHOW_SMSMAINLIST = 2;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button contact = (Button) findViewById(R.id.button1);
		Button smsinfo = (Button) findViewById(R.id.button2);
		Button sentclick = (Button) findViewById(R.id.button3);
		Button settimetosend = (Button) findViewById(R.id.button4);
		textphonenum = (EditText) findViewById(R.id.phonenum);
		textcontent = (EditText) findViewById(R.id.content);
		textcontent.setText(init_phonecontent);
		contact.setOnClickListener(new onContactClick());
		smsinfo.setOnClickListener(new onSmsinfoClick());
		sentclick.setOnClickListener(new onSentClick());
		settimetosend.setOnClickListener(new onSetTimeToSend());
		initsentlist();
		initsentcontent();

	}

	@SuppressWarnings("unchecked")
	private void initsentlist() {
		ddata = new ArrayList<ContactPeopleInfo>();
		phonestr = new String();
		sendphonenum = new ArrayList<String>();
		sendphonename = new ArrayList<String>();
		Intent intent = getIntent();
		ddata = (ArrayList<ContactPeopleInfo>) intent
				.getSerializableExtra("ddata");

		if (ddata != null) {
			for (int i = 0; i < ddata.size(); i++) {
				if (ddata.get(i).getIsSelected()) {

					phonestr = phonestr + ddata.get(i).getPeoplename() + ";";
					sendphonenum.add(ddata.get(i).getPeoplenum());
					sendphonename.add(ddata.get(i).getPeoplesent());

				}

			}
			textphonenum.setText(phonestr);
		}
	}

	private void initsentcontent() {



		Intent intent = getIntent();
		smsbody = intent.getStringExtra("body");
		if (smsbody != null) {
			textcontent.setText(init_phonecontent + smsbody);
		}

	}

	/**

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode)

		{

		case (SHOW_CONTACTMAINLIST):

		{

			if (resultCode == Activity.RESULT_OK)	{

			}

			break;

		}

		case (SHOW_SMSMAINLIST):

		{

			if (resultCode == Activity.RESULT_OK)

			{

				// TODO: Handle OK click.

			}

			break;

		}
		}

	}
	*/

	class onContactClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MultiSms.this, ContactMainList.class);
			//把ddata的值还有smsinfo的值传入
			intent.putExtra("ddata", ddata);
			intent.putExtra("body", smsbody);
			startActivity(intent);
			
			//startActivity(intent, SHOW_CONTACTMAINLIST);  

		}

	}

	class onSmsinfoClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.putExtra("ddata", ddata);
			intent.setClass(MultiSms.this, SmsMainList.class);
			startActivity(intent);
		}

	}

	class onSentClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			// String phonenum=textphonenum.getText().toString(); //取得手机号码
			String phonenum_onsent = textphonenum.getText().toString();
			content = textcontent.getText().toString();

			// if((phonenum_onsent==null||phonenum==null||"".equals(phonenum))||(content==null||"".equals(content))){
			if (phonenum_onsent == null
					|| (content == null || "".equals(content)) || ddata == null) {
				Toast.makeText(MultiSms.this, R.string.failure, 1).show(); // 显示一个Toast提示

			} else {

				for (int i = 0; i < sendphonenum.size(); i++) {
					String phonenum = sendphonenum.get(i);

					content = content.replace("[称谓]", sendphonename.get(i));
					// System.out.println(phonenum);

					SmsManager manage = SmsManager.getDefault(); // 取得默认的SmsManager用于短信的发送
					List<String> all = manage.divideMessage(content); // 短信的内容是有限的，要根据短信长度截取。逐条发送
					Iterator<String> it = all.iterator();
					while (it.hasNext()) {
						manage.sendTextMessage(phonenum, null, it.next(), null,
								null); // 逐条发送短息

					}
					Toast.makeText(MultiSms.this,
							sendphonename.get(i) + "发送成功", Toast.LENGTH_LONG)
							.show();
				}

			}
		}
	}

	class onSetTimeToSend implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MultiSms.this, SetTimeMain.class);
			startActivity(intent);
		}

	}

}