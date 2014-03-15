package com.vincent.multisms;

import java.io.Serializable;

public class ContactPeopleInfo implements Serializable{
	
	/**
	 * 实现序列号的接口，用来在intent之间传递contactpeople信息。
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String peoplename;
	private String peoplesent;
	private String peoplenum;
	private Boolean isSelected ;
	


	@Override
	public String toString() {
		return "ContactPeopleInfo [id=" + id + ", peoplename=" + peoplename
				+ ", peoplesent=" + peoplesent + ", peoplenum=" + peoplenum
				+ ", isSelected=" + isSelected + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPeoplename() {
		return peoplename;
	}
	public void setPeoplename(String peoplename) {
		this.peoplename = peoplename;
	}
	public String getPeoplesent() {
		return peoplesent;
	}
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setPeoplesent(String peoplesent) {
		this.peoplesent = peoplesent;
	}
	public String getPeoplenum() {
		return peoplenum;
	}
	public void setPeoplenum(String peoplenum) {
		this.peoplenum = peoplenum;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	

	public ContactPeopleInfo(String id, String peoplename, String peoplesent,
			String peoplenum, Boolean isSelected) {
		super();
		this.id = id;
		this.peoplename = peoplename;
		this.peoplesent = peoplesent;
		this.peoplenum = peoplenum;
		this.isSelected = isSelected;
	}
	public ContactPeopleInfo() {
		super();
	}


}
