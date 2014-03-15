package com.vincent.multisms;

import java.util.ArrayList;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class SmsMainList extends TabActivity {
    /** Called when the activity is first created. */
	ArrayList<ContactPeopleInfo> peopleinfos ;
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactmainlist);
        String localsmsstr = "���ض���";
        String remotesmsstr = "�������";
        
        //��ô��ݵ�ֵ
		Intent intent = getIntent();
		peopleinfos = (ArrayList<ContactPeopleInfo>) intent.getSerializableExtra("ddata");
        
        
        TabHost tabhost = getTabHost();
        TabHost.TabSpec localsmsspec = tabhost.newTabSpec(localsmsstr);
        Intent localsmsintent = new Intent();
      
        Resources rs = getResources();
        localsmsspec.setIndicator(localsmsstr, rs.getDrawable(android.R.drawable.sym_contact_card));
        localsmsintent.putExtra("ddata", peopleinfos);
        localsmsintent.setClass(this, LocalSmsList.class);
        localsmsspec.setContent(localsmsintent);
        //���������䣬����you must specify a way to create the tab content����
        tabhost.addTab(localsmsspec);
        

        TabHost.TabSpec remotesmsspec = tabhost.newTabSpec(remotesmsstr);
        Intent remotesmsintent = new Intent();
      
        remotesmsspec.setIndicator(remotesmsstr, rs.getDrawable(android.R.drawable.stat_sys_download));
        remotesmsintent.setClass(this, LocalSmsList.class);
        remotesmsspec.setContent(remotesmsintent);
        tabhost.addTab(remotesmsspec);
        
    }
    
    
}