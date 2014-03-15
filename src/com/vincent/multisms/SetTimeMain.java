package com.vincent.multisms;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class SetTimeMain extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactmainlist);
        String contactpeoplestr = "设置";
        String contactgroupstr = "运行任务";
        
        TabHost tabhost = getTabHost();
        TabHost.TabSpec contactpeoplespec = tabhost.newTabSpec(contactpeoplestr);
        Intent contactpeopleintent = new Intent();
      
        Resources rs = getResources();
        contactpeoplespec.setIndicator(contactpeoplestr, rs.getDrawable(android.R.drawable.sym_contact_card));
        contactpeopleintent.setClass(this, SetSentTime.class);
        contactpeoplespec.setContent(contactpeopleintent);
        //少了这个语句，导致you must specify a way to create the tab content错误
        tabhost.addTab(contactpeoplespec);
        

        TabHost.TabSpec contactgroupspec = tabhost.newTabSpec(contactgroupstr);
        Intent contactgroupintent = new Intent();
      
        contactgroupspec.setIndicator(contactgroupstr, rs.getDrawable(android.R.drawable.stat_sys_download));
        contactgroupintent.setClass(this, ContactGroupList.class);
        contactgroupspec.setContent(contactgroupintent);
        tabhost.addTab(contactgroupspec);
        
    }
    
    
}