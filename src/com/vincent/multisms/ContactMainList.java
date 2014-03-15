package com.vincent.multisms;

import java.util.ArrayList;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class ContactMainList extends TabActivity {
	ArrayList<ContactPeopleInfo> peopleinfos ;
	String smsinfo;
    /** Called when the activity is first created. */
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactmainlist);
        String contactpeoplestr = "联系人";
        String contactgroupstr = "群组";
        
        //执行获取工作
        
		Intent intent = getIntent();
		peopleinfos = (ArrayList<ContactPeopleInfo>) intent.getSerializableExtra("ddata");
		smsinfo = intent.getStringExtra("body");
        
        
        TabHost tabhost = getTabHost();
        TabHost.TabSpec contactpeoplespec = tabhost.newTabSpec(contactpeoplestr);
        Intent contactpeopleintent = new Intent();
      
        Resources rs = getResources();
        contactpeoplespec.setIndicator(contactpeoplestr, rs.getDrawable(android.R.drawable.sym_contact_card));
        //执行传递工作
        contactpeopleintent.putExtra("ddata", peopleinfos);
        contactpeopleintent.putExtra("body", smsinfo);
        contactpeopleintent.setClass(this, ContactPeopleList.class);
        contactpeoplespec.setContent(contactpeopleintent);
        //少了这个语句，导致you must specify a way to create the tab content错误
        tabhost.addTab(contactpeoplespec);
        

        TabHost.TabSpec contactgroupspec = tabhost.newTabSpec(contactgroupstr);
        Intent contactgroupintent = new Intent();
      
        contactgroupspec.setIndicator(contactgroupstr, rs.getDrawable(android.R.drawable.stat_sys_download));
        //执行传递工作
        contactgroupintent.setClass(this, ContactGroupList.class);
        contactgroupspec.setContent(contactgroupintent);
        tabhost.addTab(contactgroupspec);
        
    }
    

}