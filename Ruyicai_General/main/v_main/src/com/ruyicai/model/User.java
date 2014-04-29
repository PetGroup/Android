package com.ruyicai.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	private static final long serialVersionUID = 3854244894045271534L;

	private String rarenum = "";
	private String alias = "";// 别名
	private String username = "";// 用户名
	private String nickname = "";// 昵称
	private String distance = "";// 与我的距离
	private String remark = "";
	private String longitude;// 经度
	private String latitude;// 纬度
	private String updateUserLocationDate = "";
	private String id = "";
	private String signature = "";// 签名
	private String password = "";
	private String img = "";
	private String gender = "";// 0 男 1女
	private String createTime = "";
	private String constellation = "";
	private String phoneNumber = "";// 手机号
	private String email = "";
	private String birthdate = "";
	private String hobby;// 爱好
	private String realname = "";
	private String city = "";// 城市
	private String ifFraudulent = "";
	private String deviceToken = "";
	private String backgroundImg = "";
	private String modTime = "";
	private String age = "";
	private String superstar = "";
	private String superremark = "";
	private String shipType = "";
	private String nameSort;// 显示姓名首字母
	private String active;

	private ArrayList<String> imgList = null;
	private String heahImage;

	public ArrayList<String> getImgList() {
		return imgList;
	}

	public void setImgList(ArrayList<String> imgList) {
		this.imgList = imgList;
	}

	public String getNameSort() {
		return nameSort;
	}

	public void setNameSort(String nameSort) {
		this.nameSort = nameSort;
	}

	public String getRarenum() {
		return rarenum;
	}

	public void setRarenum(String rarenum) {
		this.rarenum = rarenum;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getUpdateUserLocationDate() {
		return updateUserLocationDate;
	}

	public void setUpdateUserLocationDate(String updateUserLocationDate) {
		this.updateUserLocationDate = updateUserLocationDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		setHeahImage(img);
		this.img = img;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIfFraudulent() {
		return ifFraudulent;
	}

	public void setIfFraudulent(String ifFraudulent) {
		this.ifFraudulent = ifFraudulent;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getBackgroundImg() {
		return backgroundImg;
	}

	public void setBackgroundImg(String backgroundImg) {
		this.backgroundImg = backgroundImg;
	}

	public String getModTime() {
		return modTime;
	}

	public void setModTime(String modTime) {
		this.modTime = modTime;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSuperstar() {
		return superstar;
	}

	public void setSuperstar(String superstar) {
		this.superstar = superstar;
	}

	public String getShipType() {
		return shipType;
	}

	public void setShipType(String shipType) {
		this.shipType = shipType;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getHeadIcon() {
		if (getImg() != null && !"".equals(getImg()) && 0 != getImg().length()) {
			//return ImageService.getHeadImagesFromRuturnImg(getImg(), "");
		}
		return "";
	}

	public String getSuperremark() {
		return superremark;
	}

	public void setSuperremark(String superremark) {
		this.superremark = superremark;
	}

	public String getHeahImage() {
		return heahImage;
	}

	public void setHeahImage(String heahImage) {
		if(heahImage!=null&&!"".equals(heahImage)){
			//this.heahImage=ImageService.getHeadImagesFromRuturnImg(heahImage,"100");
		}else {
			this.heahImage = "";
		}
	}

	@Override
	public String toString() {
		return "User [ rarenum=" + rarenum + ", alias="
				+ alias + ", username=" + username + ", nickname=" + nickname
				+ ", distance=" + distance + ", remark=" + remark
				+ ", longitude=" + longitude + ", latitude=" + latitude
				+ ", updateUserLocationDate=" + updateUserLocationDate
				+ ", id=" + id + ", signature=" + signature + ", password="
				+ password + ", img=" + img + ", gender=" + gender
				+ ", createTime=" + createTime + ", constellation="
				+ constellation + ", phoneNumber=" + phoneNumber + ", email="
				+ email + ", birthdate=" + birthdate + ", hobby=" + hobby
				+ ", realname=" + realname + ", city=" + city
				+ ", ifFraudulent=" + ifFraudulent + ", deviceToken="
				+ deviceToken + ", backgroundImg=" + backgroundImg
				+ ", modTime=" + modTime + ", age=" + age + ", superstar="
				+ superstar + ", shipType=" + shipType + ", nameSort="
				+ nameSort + ", active=" + active + ", imgList=" + imgList
				+ "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(rarenum);
		dest.writeString(alias);
		dest.writeString(username);
		dest.writeString(nickname);
		dest.writeString(distance);
		dest.writeString(remark);
		dest.writeString(longitude);
		dest.writeString(latitude);
		dest.writeString(updateUserLocationDate);
		dest.writeString(id);
		dest.writeString(signature);
		dest.writeString(password);
		dest.writeString(img);
		dest.writeString(gender);
		dest.writeString(createTime);
		dest.writeString(constellation);
		dest.writeString(phoneNumber);
		dest.writeString(email);
		dest.writeString(birthdate);
		dest.writeString(hobby);
		dest.writeString(realname);
		dest.writeString(city);
		dest.writeString(ifFraudulent);
		dest.writeString(deviceToken);
		dest.writeString(backgroundImg);
		dest.writeString(modTime);
		dest.writeString(age);
		dest.writeString(superstar);
		dest.writeString(superremark);
		dest.writeString(shipType);
		dest.writeString(nameSort);
		dest.writeString(active);
	}

}
