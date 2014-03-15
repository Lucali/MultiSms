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
        String contactpeoplestr = "��ϵ��";
        String contactgroupstr = "Ⱥ��";
        
        //ִ�л�ȡ����
        
		Intent intent = getIntent();
		peopleinfos = (ArrayList<ContactPeopleInfo>) intent.getSerializableExtra("ddata");
		smsinfo = intent.getStringExtra("body");
        
        
        TabHost tabhost = getTabHost();
        TabHost.TabSpec contactpeoplespec = tabhost.newTabSpec(contactpeoplestr);
        Intent contactpeopleintent = new Intent();
      
        Resources rs = getResources();
        contactpeoplespec.setIndicator(contactpeoplestr, rs.getDrawable(android.R.drawable.sym_contact_card));
        //ִ�д��ݹ���
        contactpeopleintent.putExtra("ddata", peopleinfos);
        contactpeopleintent.putExtra("body", smsinfo);
        contactpeopleintent.setClass(this, ContactPeopleList.class);
        contactpeoplespec.setContent(contactpeopleintent);
        //���������䣬����you must specify a way to create the tab content����
        tabhost.addTab(contactpeoplespec);
        

        TabHost.TabSpec contactgroupspec = tabhost.newTabSpec(contactgroupstr);
        Intent contactgroupintent = new Intent();
      
        contactgroupspec.setIndicator(contactgroupstr, rs.getDrawable(android.R.drawable.stat_sys_download));
        //ִ�д��ݹ���
        contactgroupintent.setClass(this, ContactGroupList.class);
        contactgroupspec.setContent(contactgroupintent);
        tabhost.addTab(contactgroupspec);
        
    }
    

}