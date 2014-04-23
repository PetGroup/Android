package com.ruyicai.model;

public class ChatServer {
    private String address; 
    private String name; 
    private String id; 
    private String version;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "ChatServer [address=" + address + ", name=" + name + ", id="
				+ id + ", version=" + version + "]";
	}
	
	
	
}
