package com.ruyicai.model;

public class ContactsBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String mobileid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileid() {
		return mobileid;
	}

	public void setMobileid(String mobileid) {
		this.mobileid = mobileid;
	}

	@Override
	public String toString() {
		return "ContactsBean [name=" + name + ", mobileid=" + mobileid + "]";
	}

}
