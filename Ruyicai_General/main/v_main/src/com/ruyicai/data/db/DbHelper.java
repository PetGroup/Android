package com.ruyicai.data.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.model.HttpUser;
import com.ruyicai.model.MyMessage;
import com.ruyicai.util.CommonsharedPreferences;
import com.ruyicai.util.StringUtils;
import com.tencent.qc.stat.common.User;

@Singleton
public class DbHelper {
	private SqlLiteHelper helper;


	private String userid;
	@Inject Context context;
	@Inject CommonsharedPreferences commonsharedPreferences;
	public  DbHelper() {
	}
	public void switchToUser(String userid){
		if(helper!=null){
			if(userid.equals(this.userid)){
				return;
			}else{
				getHelper().close();
			}
		}
		this.userid=userid;
		helper = new SqlLiteHelper(this, context,userid);
	}
	
	public SqlLiteHelper getHelper() {
		if(helper==null){
			helper = new SqlLiteHelper(this, context,userid);
		}
		return helper;
	}
	public SqlLiteHelper getHelper(Context context) {
		if(helper==null){
			helper = new SqlLiteHelper(this, context,userid);
		}
		return helper;
	}
	
	/**
	 * 保存用户信息
	 * @param userId
	 * @param nickName
	 * @param shipType
	 */
	public void SaveContactsUserInfos(String userId,String nickName,String shipType) {
//		User user=createUser(userId, nickName);
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		try {
//			db.beginTransaction();
//			setInsertUserInfoDb(db, user, shipType);
//			db.setTransactionSuccessful();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			db.endTransaction();
//		} 
	}
//	private User createUser(String userId,String nickName){
//		User user=new User();
//		user.setId(userId);
//		user.setNickname(nickName);
//		return user;
//	}

	/**
	 * 使用事务保存单个用户信息
	 * @param u
	 * @param shipType
	 */
	public void SaveContactsUserInfos(User u,String shipType) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
		try {
			db.beginTransaction();//开始事务
			setInsertUserInfoDb(db, u, shipType);
			db.setTransactionSuccessful();//调用此方法会在执行到endTransaction() 时提交当前事务，如果不调用此方法会回滚事务
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.endTransaction();//由事务的标志决定是提交事务，还是回滚事务
		} 
	}
	/**
	 * 使用事务保存用户信息
	 * @param users
	 * @param shipType
	 */
	public void SaveContactsUserInfos(List<User> users,String shipType) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
		try {
			db.beginTransaction();
			for(User u:users){
//				if(HttpUser.userId.equals(u.getId())){
//					setInsertUserInfoDb(db, u, "unknow");
//				}else {
//					setInsertUserInfoDb(db, u, shipType);
//				}
			} 
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.endTransaction();
		} 
	}
	/**
	 * 设置db
	 * @param db
	 * @param u
	 * @param shipType
	 */
	private void setInsertUserInfoDb(SQLiteDatabase db,User u,String shipType){
//		ContentValues userValues=getUserContentValues(u, shipType);
//		db.replace(context.getString(R.string.ContactsUserInfoTableName), null,userValues);
//		setInsertTitleInfoDb(db, u.getTitle());
	} 
	/**
	 * 设置头衔Db
	 * @param db
	 * @param title
	 */
//	private void setInsertTitleInfoDb(SQLiteDatabase db,Title title){
//		ContentValues titleValues=getTitleContentValues(title);
//		if(titleValues!=null){
//			db.replace(context.getString(R.string.TitleInfoTableName), null,titleValues);
//			ContentValues titleObjecdtValues=getTitleObjectContentValues(title.getTitleObj());
//			if(titleObjecdtValues!=null){
//				db.replace(context.getString(R.string.TitleObjectInfoTableName), null,titleObjecdtValues);
//			}
//		}
//	}
	
	/**
	 * 事务提交保存单个头衔
	 * @param titles
	 */
//	public void SaveTitleInfo(Title title){
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		try {
//			db.beginTransaction();
//			setInsertTitleInfoDb(db, title);
//			db.setTransactionSuccessful();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			db.endTransaction();
//		} 
//	}
	/**
	 * 事务提交保存头衔
	 * @param titles
	 */
//	public void SaveTitleInfo(List<Title> titles){
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		try {
//			db.beginTransaction();
//			for(Title title:titles){
//				setInsertTitleInfoDb(db, title);
//			} 
//			db.setTransactionSuccessful();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			db.endTransaction();
//		} 
//	}
	/**
	 * 保存单个角色信息
	 * @param gameRoseInfo
	 * @param userId
	 */
//	public void SaveTitleInfo(GameRoseInfo gameRoseInfo,String userId){
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		try {
//			db.beginTransaction();
//			setInsertRoleInfoDb(db, gameRoseInfo,userId);
//			db.setTransactionSuccessful();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			db.endTransaction();
//		} 
//	}
	/**
	 * 事务提交保存用户角色
	 * @param roleInfos
	 * @param userId
	 */
//	public synchronized void saveMyRoleInfo(List<GameRoseInfo> roleInfos,String userId) {
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		try {
//			db.beginTransaction();
//			for(GameRoseInfo gameRoseInfo:roleInfos){
//				setInsertRoleInfoDb(db, gameRoseInfo,userId);
//			} 
//			db.setTransactionSuccessful();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			db.endTransaction();
//		} 
//	}
//	private void setInsertRoleInfoDb(SQLiteDatabase db,GameRoseInfo gameRoseInfo,String userId){
//		ContentValues roleValues=getRoleContentValues(gameRoseInfo,userId);
//		if(roleValues!=null){
//			db.replace(context.getString(R.string.MyRoleInfoTableName), null,roleValues);
//		}
//	}
	
	/**
	 * userContentValues
	 * @param u
	 * @param shipType
	 * @return
	 */
//	private ContentValues getUserContentValues(User u,String shipType){
//		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.UserInfoTitleUserId),null ==u.getId()? "" : u.getId());
//		values.put(context.getString(R.string.UserInfoTitleUserName),null ==u.getUsername()? "" : u.getUsername());
//		values.put(context.getString(R.string.UserInfoTitleNickName),null == u.getNickname() ? "" : u.getNickname());
//		values.put(context.getString(R.string.UserInfoTitleShipType),null == shipType ? "" : shipType);
//		values.put(context.getString(R.string.UserInfoTitleUserAge),null == u.getAge() ? "" : u.getAge());
//		values.put(context.getString(R.string.UserInfoTitleUserBirthDate),null == u.getBirthdate() ? "" : u.getBirthdate());
//		values.put(context.getString(R.string.UserInfoTitleSignature),null == u.getSignature() ? "" : u.getSignature());
//		values.put(context.getString(R.string.UserInfoTitleUserDistance),null == u.getDistance()? "" : u.getDistance());
//		values.put(context.getString(R.string.UserInfoTitleUserGender),null ==u.getGender() ? "" : u.getGender());
//		values.put(context.getString(R.string.UserInfoTitleUserHobby),null ==u.getHobby()? "" : u.getHobby());
//		values.put(context.getString(R.string.UserInfoTitleUserImgId),null ==u.getImg()? "" : u.getImg());
//		values.put(context.getString(R.string.UserInfoTitleUserLatitude),null ==u.getLatitude()? "" : u.getLatitude());
//		values.put(context.getString(R.string.UserInfoTitleUserLongitude),null ==u.getLongitude()?"":u.getLongitude());
//		values.put(context.getString(R.string.UserInfoTitleUserCity),null ==u.getCity()?"":u.getCity());
//		values.put(context.getString(R.string.UserInfoTitleRarenum),null ==u.getRarenum()? "" : u.getRarenum());
//		values.put(context.getString(R.string.UserInfoTitleAlias),null ==u.getAlias()? "" : u.getAlias());
//		values.put(context.getString(R.string.UserInfoTitleRemark),null == u.getRemark() ? "" : u.getRemark());
//		values.put(context.getString(R.string.UserInfoTitleUpdateUserLocationDate),null == u.getUpdateUserLocationDate() ? "" : u.getUpdateUserLocationDate());
//		values.put(context.getString(R.string.UserInfoTitlePassword),null == u.getPassword() ? "" : u.getPassword());
//		values.put(context.getString(R.string.UserInfoTitleCreateTime),null == u.getCreateTime() ? "" : u.getCreateTime());
//		values.put(context.getString(R.string.UserInfoTitleConsteLlation),null == u.getConstellation() ? "" : u.getConstellation());
//		values.put(context.getString(R.string.UserInfoTitlePhoneNumber),null == u.getPhoneNumber() ? "" : u.getPhoneNumber());
//		values.put(context.getString(R.string.UserInfoTitleEmail),null == u.getEmail()? "" : u.getEmail());
//		values.put(context.getString(R.string.UserInfoTitleRealname),null ==u.getRealname() ? "" : u.getRealname());
//		values.put(context.getString(R.string.UserInfoTitleIfFraudulent),null ==u.getIfFraudulent()? "" : u.getIfFraudulent());
//		values.put(context.getString(R.string.UserInfoTitleDeviceToken),null ==u.getDeviceToken()? "" : u.getDeviceToken());
//		values.put(context.getString(R.string.UserInfoTitleBackGroundImg),null ==u.getBackgroundImg()? "" : u.getBackgroundImg());
//		values.put(context.getString(R.string.UserInfoTitleModTime),null ==u.getModTime()? "" : u.getModTime());
//		values.put(context.getString(R.string.UserInfoTitleSuperstar),null ==u.getSuperstar()? "" : u.getSuperstar());
//		values.put(context.getString(R.string.UserInfoTitleNameSort),null ==u.getNameSort()? "" : u.getNameSort());
//		values.put(context.getString(R.string.UserInfoTitleActive),null ==u.getActive()? "" : u.getActive());
//		values.put(context.getString(R.string.UserInfoTitleSuperremark),null ==u.getSuperremark()? "" : u.getSuperremark());
//		return values;
//	}
	/**
	 * @param title
	 * @return
	 */
//	private ContentValues getTitleContentValues(Title title){
//		if(title==null){
//			return null;
//		}
//		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.TitleInfoTitleId),null ==title.getId()? "" : title.getId());
//		values.put(context.getString(R.string.TitleInfoTitleRealm),null ==title.getRealm()? "" : title.getRealm());
//		values.put(context.getString(R.string.TitleInfoTitleUserId),null == title.getUserid() ? "" : title.getUserid());
//		values.put(context.getString(R.string.TitleInfoTitleCharaCterId),null == title.getCharacterid() ? "" : title.getCharacterid());
//		values.put(context.getString(R.string.TitleInfoTitleGameId),null == title.getGameid() ? "" : title.getGameid());
//		values.put(context.getString(R.string.TitleInfoTitleSortNum),null == title.getSortnum() ? "" : title.getSortnum());
//		values.put(context.getString(R.string.TitleInfoTitleTitleId),null == title.getTitleid() ? "" : title.getTitleid());
//		values.put(context.getString(R.string.TitleInfoTitleHasDate),null == title.getHasDate()? "" : title.getHasDate());
//		values.put(context.getString(R.string.TitleInfoTitleUserImg),null ==title.getUserimg() ? "" : title.getUserimg());
//		values.put(context.getString(R.string.TitleInfoTitleHide),null ==title.getHide()? "" : title.getHide());
//		values.put(context.getString(R.string.TitleInfoTitleCharaCterName),null ==title.getCharactername()? "" : title.getCharactername());
//		return values;
//	}
//	/**
//	 * @param titleObject
//	 * @return
//	 */
//	private ContentValues getTitleObjectContentValues(TitleObject titleObject){
//		if(titleObject==null){
//			return null;
//		}
//		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.TitleInfoTitleObjectId),null ==titleObject.getId()? "" : titleObject.getId());
//		values.put(context.getString(R.string.TitleInfoTitleObjectIcon),null ==titleObject.getIcon()? "" : titleObject.getIcon());
//		values.put(context.getString(R.string.TitleInfoTitleObjectRank),null == titleObject.getRank() ? "" : titleObject.getRank());
//		values.put(context.getString(R.string.TitleInfoTitleObjectImg),null == titleObject.getImg() ? "" : titleObject.getImg());
//		values.put(context.getString(R.string.TitleInfoTitleObjectTitle),null == titleObject.getTitle() ? "" : titleObject.getTitle());
//		values.put(context.getString(R.string.TitleInfoTitleObjectCreateDate),null == titleObject.getCreateDate() ? "" : titleObject.getCreateDate());
//		values.put(context.getString(R.string.TitleInfoTitleObjectRarenum),null == titleObject.getRarenum() ? "" : titleObject.getRarenum());
//		values.put(context.getString(R.string.TitleInfoTitleObjectRemark),null == titleObject.getRemark()? "" : titleObject.getRemark());
//		values.put(context.getString(R.string.TitleInfoTitleObjectGameId),null ==titleObject.getGameid() ? "" : titleObject.getGameid());
//		values.put(context.getString(R.string.TitleInfoTitleObjectSortNum),null ==titleObject.getSortnum()? "" : titleObject.getSortnum());
//		values.put(context.getString(R.string.TitleInfoTitleObjectRareMemo),null ==titleObject.getRarememo()? "" : titleObject.getRarememo());
//		values.put(context.getString(R.string.TitleInfoTitleObjectTitleKey),null ==titleObject.getTitlekey()? "" : titleObject.getTitlekey());
//		values.put(context.getString(R.string.TitleInfoTitleObjectEvolution),null ==titleObject.getEvolution()? "" : titleObject.getEvolution());
//		values.put(context.getString(R.string.TitleInfoTitleObjectSimpleTitle),null == titleObject.getSimpletitle() ? "" : titleObject.getSimpletitle());
//		values.put(context.getString(R.string.TitleInfoTitleObjectRankvalType),null == titleObject.getRankvaltype() ? "" : titleObject.getRankvaltype());
//		values.put(context.getString(R.string.TitleInfoTitleObjectRankType),null == titleObject.getRanktype() ? "" : titleObject.getRanktype());
//		values.put(context.getString(R.string.TitleInfoTitleObjectTitleType),null == titleObject.getTitletype() ? "" : titleObject.getTitletype());
//		values.put(context.getString(R.string.TitleInfoTitleObjectRemarkDetail),null == titleObject.getRemarkDetail() ? "" : titleObject.getRemarkDetail());
//		return values;
//	}
//	
//	/**
//	 * @param gameRoseInfo
//	 * @param userId
//	 * @return
//	 */
//
//	public ContentValues getRoleContentValues(GameRoseInfo roleInfo,String userId){
//		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.RoleInfoId),null ==roleInfo.getId()? "" : roleInfo.getId());
//		values.put(context.getString(R.string.RoleInfoName),null ==roleInfo.getName()? "" : roleInfo.getName());
//		values.put(context.getString(R.string.RoleInfoContent),null ==roleInfo.getContent()? "" : roleInfo.getContent());
//		values.put(context.getString(R.string.RoleInfoLevel),null ==roleInfo.getLevel()? "" : roleInfo.getLevel());
//		values.put(context.getString(R.string.RoleInfoLastModified),null ==roleInfo.getLastModified()? "" : roleInfo.getLastModified());
//		values.put(context.getString(R.string.RoleInfoRealm),null ==roleInfo.getRealm()? "" : roleInfo.getRealm());
//		values.put(context.getString(R.string.RoleInfoAuth),null == roleInfo.getAuth() ? "" : roleInfo.getAuth());
//		values.put(context.getString(R.string.RoleInfoLastClazz),null == roleInfo.getClazz() ? "" : roleInfo.getClazz());
//		values.put(context.getString(R.string.RoleInfoFilepath),null == roleInfo.getFilepath() ? "" : roleInfo.getFilepath());
//		values.put(context.getString(R.string.RoleInfoiscatch),null == roleInfo.getIscatch() ? "" : roleInfo.getIscatch());
//		values.put(context.getString(R.string.RoleInfoFailedmsg),null == roleInfo.getFailedmsg() ? "" : roleInfo.getFailedmsg());
//		values.put(context.getString(R.string.RoleInfoGender),null == roleInfo.getGender()? "" : roleInfo.getGender());
//		values.put(context.getString(R.string.RoleInfoGuild),null ==roleInfo.getGuild() ? "" : roleInfo.getGuild());
//		values.put(context.getString(R.string.RoleInfoGuildRealm),null ==roleInfo.getGuildRealm()? "" : roleInfo.getGuildRealm());
//		values.put(context.getString(R.string.RoleInfoTotalHonorableKills),null ==roleInfo.getTotalHonorableKills()? "" : roleInfo.getTotalHonorableKills());
//		values.put(context.getString(R.string.RoleInfoMountsnum),null == roleInfo.getMountsnum() ? "" : roleInfo.getMountsnum());
//		values.put(context.getString(R.string.RoleInfoOldplay),null == roleInfo.getOldplay() ? "" : roleInfo.getOldplay());
//		values.put(context.getString(R.string.RoleInfoPveScore),null == roleInfo.getPveScore() ? "" : roleInfo.getPveScore());
//		values.put(context.getString(R.string.RoleInfoFailednum),null == roleInfo.getFailednum()? "" : roleInfo.getFailednum());
//		values.put(context.getString(R.string.RoleInfoBattlegroup),null ==roleInfo.getBattlegroup() ? "" : roleInfo.getBattlegroup());
//		values.put(context.getString(R.string.RoleInfoRace),null ==roleInfo.getRace()? "" : roleInfo.getRace());
//		values.put(context.getString(R.string.RoleInfoRaceObj),null ==roleInfo.getRaceObj()? "" : roleInfo.getRaceObj());
//		values.put(context.getString(R.string.RoleInfoUserId),null ==userId? "" : userId);
//		return values;
//	}
//	/**
//	 * 查询关系
//	 * @param userId
//	 * @return
//	 */
//	public String getFriendShipType(String userId) {
//		String shipType="";
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		Cursor cur = db.query(context.getString(R.string.ContactsUserInfoTableName),new String[]{context.getString(R.string.UserInfoTitleShipType)}, context.getString(R.string.UserInfoTitleUserId)+ " = ?",
//				new String[] {userId},null,null,null);
//		if(cur != null && cur.moveToFirst()) {
//			shipType=cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleShipType)));
//		}
//		cur.close();
//		return shipType;
//	}
//	/**
//	 * 更新Title
//	 * @param titleId
//	 * @param hide
//	 */
//	public void updateTitleInfo(String titleId, String hide) {
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.TitleInfoTitleHide), hide);
//		db.update(context.getString(R.string.TitleInfoTableName),values, context.getString(R.string.TitleInfoTitleId) + "=?", 
//				new String[] { String.valueOf(titleId) });
//	}
//	public long saveSayHelloUser(String userId){
//		long count = 0;
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.userId),null ==userId? "" : userId);
//		count = db.replace(context.getString(R.string.sayHelloListTable), null,values);
//		return count;
//	}
//	public int selectSayHelloUser(String userId){
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		Cursor cur = db.query(context.getString(R.string.sayHelloListTable),null, context.getString(R.string.userId)
//				+ " = ?", new String[] { userId },null,null,null);
//		int count=cur.getCount();
//		return count;
//	}
//	public List<String> searchSayHelloUsers(){
//		List<String> sayHellos=new ArrayList<String>();
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		Cursor cur = db.query(context.getString(R.string.sayHelloListTable),null,null, null,null,null,null);
//		for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext()){
//			sayHellos.add(cur.getString(cur.getColumnIndex(context.getString(R.string.userId))));
//		}
//		return sayHellos;
//	}
//	
//	/**
//	 * 获取联系人的信息 
//	 * @param shiptype 好友类型  1好友，2关注，3粉丝
//	 * @return
//	 */
//	public List<User> GetContactsUser(String shiptype) {
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		ArrayList<User> listUsers = new ArrayList<User>();
//		User u;
//		Cursor cur=null;
//		if(shiptype!=null&&!"".equals(shiptype)){
//			cur = db.query(context.getString(R.string.ContactsUserInfoTableName),null, context.getString(R.string.UserInfoTitleShipType)
//						+ " = ?", new String[] { shiptype },null,null,null);
//		}else {
//			cur = db.query(context.getString(R.string.ContactsUserInfoTableName), null,null, null, null, null, null);
//		}
//		try {
//			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//				u = new User();
//				u.setId(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserId))));
//				u.setUsername(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserName))));
//				u.setNickname(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleNickName))));
//				u.setAge(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserAge))));
//				u.setBirthdate(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserBirthDate))));
//				u.setSignature(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleSignature))));
//				u.setDistance(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserDistance))));
//				u.setGender(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserGender))));
//				u.setHobby(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserHobby))));
//				u.setImg(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserImgId))));
//				u.setLatitude(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserLatitude))));
//				u.setLongitude(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserLongitude))));
//				u.setCity(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserCity))));
//				u.setRarenum(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleRarenum))));
//				u.setAlias(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleAlias))));
//				u.setRemark(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleRemark))));
//				u.setActive(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleActive))));
//				String updateDate=cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUpdateUserLocationDate)));
//				if(updateDate==null||"".equals(updateDate)){
//					u.setUpdateUserLocationDate("10000000");
//				}else {
//					u.setUpdateUserLocationDate(updateDate);
//				}
//				u.setPassword(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitlePassword))));
//				u.setCreateTime(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleCreateTime))));
//				u.setConstellation(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleConsteLlation))));
//				u.setPhoneNumber(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitlePhoneNumber))));
//				u.setEmail(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleEmail))));
//				u.setRealname(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleRealname))));
//				u.setIfFraudulent(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleIfFraudulent))));
//				u.setDeviceToken(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleDeviceToken))));
//				u.setBackgroundImg(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleBackGroundImg))));
//				u.setModTime(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleModTime))));
//				u.setSuperstar(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleSuperstar))));
//				u.setNameSort(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleNameSort))));
//				u.setTitle(this.GetTitle(u.getId()));
//				listUsers.add(u);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			cur.close();
//		}
//		return listUsers;
//	}
//	/**
//	 *  获取Title信息
//	 * @param userId UserId
//	 * @return
//	 */
//	public Title GetTitle(String userId) {
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		Title t = new Title();
//		Cursor cur = db.query(context.getString(R.string.TitleInfoTableName),null, context.getString(R.string.UserInfoTitleUserId)
//						+ " = ?", new String[] { userId },null,null,null);
//		try {
//			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//				t.setId(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleId))));
//				t.setRealm(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleRealm))));
//				t.setUserid(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleUserId))));
//				t.setCharacterid(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleCharaCterId))));
//				t.setGameid(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleGameId))));
//				t.setSortnum(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleSortNum))));
//				t.setTitleid(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleTitleId))));
//				t.setHasDate(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleHasDate))));
//				t.setUserimg(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleUserImg))));
//				t.setHide(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleHide))));
//				t.setCharactername(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleCharaCterName))));
//				t.setTitleObj(this.GetTitleObject(t.getTitleid()));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally{
//			cur.close();
//		}
//		return t;
//	}
//	/**
//	 *  获取Title信息
//	 * @param userId UserId
//	 * @return
//	 */
//	public String getActive(String userId) {
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		String active="";
//		Cursor cur = db.query(context.getString(R.string.ContactsUserInfoTableName),null, context.getString(R.string.UserInfoTitleUserId)
//				+ " = ?", new String[] { userId },null,null,null);
//		for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//			active=cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleActive)));
//		}
//		cur.close();
//		return active;
//	}
//	
//	/**
//	 * 获取TitleObject信息
//	 * @param titleId TitleId
//	 * @return
//	 */
//
//	public TitleObject GetTitleObject(String titleId) {
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		TitleObject to = new TitleObject();
//		Cursor cur = db.query(context.getString(R.string.TitleObjectInfoTableName),null, context.getString(R.string.TitleInfoTitleObjectId)
//						+ " = ?", new String[] { titleId },null,null,null);
//		for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//			to.setId(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectId))));
//			to.setIcon(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectIcon))));
//			to.setRank(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectRank))));
//			to.setImg(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectImg))));
//			to.setTitle(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectTitle))));
//			to.setCreateDate(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectCreateDate))));
//			to.setRarenum(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectRarenum))));
//			to.setRemark(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectRemark))));
//			to.setGameid(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectGameId))));
//			to.setSortnum(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectSortNum))));
//			to.setRarememo(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectRareMemo))));
//			to.setTitlekey(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectTitleKey))));
//			to.setEvolution(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectEvolution))));
//			to.setSimpletitle(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectSimpleTitle))));
//			to.setRankvaltype(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectRankvalType))));
//			to.setRanktype(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectRankType))));
//			to.setTitletype(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectTitleType))));
//			to.setRemarkDetail(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleObjectRemarkDetail))));
//		}
//		cur.close();
//		return to;
//	}
//	/**
//	 * 根据shiptype查找对应好友关系的个数
//	 * @param shiptype
//	 * @return
//	 */
//	public int getContactsNumByShipType(String shiptype) {
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		Cursor cur = db.query(context.getString(R.string.ContactsUserInfoTableName),null, context.getString(R.string.UserInfoTitleShipType)
//				+ " = ?", new String[] { shiptype },null,null,null);
//		int num = cur.getCount();
//		cur.close();
//		return num;
//	}
//	/** 
//	 * 获取角色信息
//	 * @return
//	 */
//	public ArrayList<GameRoseInfo> getMyRoleList(String userId) {
//		ArrayList<GameRoseInfo> list = new ArrayList<GameRoseInfo>();
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		Cursor cur = db.query(context.getString(R.string.MyRoleInfoTableName),null, context.getString(R.string.RoleInfoUserId)+" =?",new String[]{userId},null,null,null);
//		
//		if (cur != null) {
//			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//				GameRoseInfo rose = new GameRoseInfo();
//				rose.setId(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoId))));
//				rose.setName(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoName))));
//				rose.setContent(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoContent))));
//				rose.setLevel(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoLevel))));
//				rose.setLastModified(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoLastModified))));
//				rose.setRealm(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoRealm))));
//				rose.setAuth(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoAuth))));
//				rose.setClazz(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoLastClazz))));
//				rose.setFilepath(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoFilepath))));
//				rose.setIscatch(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoiscatch))));
//				rose.setFailedmsg(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoFailedmsg))));
//				rose.setGender(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoGender))));
//				rose.setGuild(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoGuild))));
//				rose.setGuildRealm(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoGuildRealm))));
//				rose.setTotalHonorableKills(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoTotalHonorableKills))));
//				rose.setMountsnum(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoMountsnum))));
//				rose.setOldplay(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoOldplay))));
//				rose.setPveScore(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoPveScore))));
//				rose.setFailednum(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoFailednum))));
//				rose.setBattlegroup(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoBattlegroup))));
//				rose.setRace(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoRace))));
//				rose.setRaceObj(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoRaceObj))));
//				rose.setUserId(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoUserId))));
//				list.add(rose);
//			}
//			cur.close();
//		}
//		return list;
//	}
//	
//	
//	/**
//	 *  获取我的Title信息
//	 * @param userId UserId
//	 * @return
//	 */
//	public ArrayList<Title> getMyTitleList(String userId,String hideOrShow) {
//		ArrayList<Title> list = new ArrayList<Title>();
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		Cursor cur = db.query(context.getString(R.string.TitleInfoTableName),null, context.getString(R.string.TitleInfoTitleUserId)
//				+ " = ? and "+context.getString(R.string.TitleInfoTitleHide)+" = ?" , new String[] { userId,hideOrShow},null,null,null,null);
//		if (cur != null) {
//			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//				Title title = new Title();
//				title.setId(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleId))));
//				title.setRealm(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleRealm))));
//				title.setUserid(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleUserId))));
//				title.setCharacterid(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleCharaCterId))));
//				title.setGameid(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleGameId))));
//				title.setSortnum(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleSortNum))));
//				title.setTitleid(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleTitleId))));
//				title.setHasDate(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleHasDate))));
//				title.setUserimg(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleUserImg))));
//				title.setHide(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleHide))));
//				title.setCharactername(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleCharaCterName))));
//				title.setTitleObj(this.GetTitleObject(title.getTitleid()));
//				list.add(title);
//			}
//			cur.close();
//		}
//		return list;
//	}
//	/**
//	 * 保存我的动态数据
//	 * @param dynamicInfo
//	 */
//	public synchronized void saveMyDynamicInfo(MyDynamicInfoListBean dynamicInfo) {
//		saveMyDynamicInfo(dynamicInfo.getUserid(), dynamicInfo.getId(), dynamicInfo.getUserimg(),
//				dynamicInfo.getMsg(), dynamicInfo.getTitle(), dynamicInfo.getCreateDate(),
//				dynamicInfo.getUsername(), dynamicInfo.getNickname(), 
//				dynamicInfo.getShowtitle());
//	}
//	
//	private synchronized long saveMyDynamicInfo(String userId, String id,String img, 
//			String msg, String title,String createDate,String userName, 
//			String nickName, String showTitle) {
//		long count = 0;
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.UserInfoTitleUserId),null ==userId? "" : userId);
//		values.put(context.getString(R.string.RoleInfoId),null ==id? "" : id);
//		values.put(context.getString(R.string.UserImg),null ==img? "" : img);
//		values.put(context.getString(R.string.DynamicInfoMsg),null ==msg? "" : msg);
//		values.put(context.getString(R.string.DynamicInfoTitle),null ==title? "" : title);
//		values.put(context.getString(R.string.UserCreateTime),null ==createDate? "" : createDate);
//		values.put(context.getString(R.string.UserUsername),null == userName ? "" : userName);
//		values.put(context.getString(R.string.UserNickname),null == nickName ? "" : nickName);
//		values.put(context.getString(R.string.DynamicInfoShowTitle),null == showTitle ? "" : showTitle);
//		count = db.replace(context.getString(R.string.my_dynamic_table_name), null,values);
//		return count;
//	}
//	/**
//	 * 获取我的动态数据
//	 * @return
//	 */
//	public MyDynamicInfoListBean getMyDynamicInfo(String userId) {
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		MyDynamicInfoListBean to = new MyDynamicInfoListBean();
//		Cursor cur = db.query(context.getString(R.string.my_dynamic_table_name),null, context.getString(R.string.UserInfoTitleUserId)
//				+ " = ? ", new String[] { userId},null,null,null);
//		if (cur != null) {
//			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//				to.setUserid(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserId))));
//				to.setId(cur.getString(cur.getColumnIndex(context.getString(R.string.RoleInfoId))));
//				to.setUserimg(cur.getString(cur.getColumnIndex(context.getString(R.string.UserImg))));
//				to.setMsg(cur.getString(cur.getColumnIndex(context.getString(R.string.DynamicInfoMsg))));
//				to.setTitle(cur.getString(cur.getColumnIndex(context.getString(R.string.DynamicInfoTitle))));
//				to.setCreateDate(cur.getString(cur.getColumnIndex(context.getString(R.string.UserCreateTime))));
//				to.setUsername(cur.getString(cur.getColumnIndex(context.getString(R.string.UserUsername))));
//				to.setNickname(cur.getString(cur.getColumnIndex(context.getString(R.string.UserNickname))));
//				to.setShowtitle(cur.getString(cur.getColumnIndex(context.getString(R.string.DynamicInfoShowTitle))));
//			}
//			cur.close();
//		}
//		return to;
//	}
//	/**
//	 * 更新我的动态头像
//	 * @param userId
//	 * @param imageId
//	 * @return
//	 */
//	public int updateDynamicInfo(String userId,String imageId) {
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.UserImg),null ==imageId? "" : imageId);
//		int a = db.update(context.getString(R.string.my_dynamic_table_name),
//				values, context.getString(R.string.UserInfoTitleUserId) + " =?", 
//				new String[] { String.valueOf(userId) });
//		return a;
//	}
//	/**
//	 * 删除DynamicInfo
//	 * @param userId
//	 * @return
//	 */
//	public int deleteDynamicInfo(String userId) {
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		int result = db.delete(context.getString(R.string.my_dynamic_table_name),
//				context.getString(R.string.UserInfoTitleUserId)+" = ?", new String[] { userId});
//		return result;
//	}
//	/**
//	 * 保存我的赞的个数和粉丝个数
//	 * @param userId
//	 * @param zan
//	 * @param fans
//	 * @return
//	 */
//	public synchronized long saveMyZanAndFansInfo(String userId, String zan,String fans,String fansAndFriendNum) {
//		long count = 0;
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.UserId),null ==userId? "" : userId);
//		values.put(context.getString(R.string.UserInfoZan),null ==zan? "" : zan);
//		values.put(context.getString(R.string.UserInfoFans),null ==fans? "" : fans);
//		values.put(context.getString(R.string.UserInfofansFriendNum),null ==fansAndFriendNum? "" : fansAndFriendNum);
//		count = db.replace(context.getString(R.string.my_zan_fans_table_name), null,values);
//		return count;
//	}
//	public int updateMyFansInfo(String userId,String fans) {
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.UserInfoFans),null ==fans? "" : fans);
//		int a = db.update(context.getString(R.string.my_zan_fans_table_name),
//				values, context.getString(R.string.UserId) + "=?", 
//				new String[] { String.valueOf(userId) });
//		return a;
//	}
//	public ZanAndFansBean getMyZanAndFansInfo(String userId) {
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		ZanAndFansBean bean = new ZanAndFansBean();
//		Cursor cur = db.query(context.getString(R.string.my_zan_fans_table_name),null, context.getString(R.string.UserId)+" =?", new String[]{userId},null,null,null);
//		for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//			bean.setUserId(cur.getString(cur.getColumnIndex(context.getString(R.string.UserId))));
//			bean.setZannum(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoZan))));
//			bean.setFansnum(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoFans))));
//			bean.setFansAndFriendnum(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfofansFriendNum))));
//		}
//		cur.close();
//		return bean;
//	}
//	public void saveDestUser(DestUser destUser,String userId) {
//		saveDestUser(destUser.getUserid(), userId, destUser.getUsername(), destUser.getNickname(), 
//					destUser.getAlias(), destUser.getSuperstar(), destUser.getUserimg());
//	}
//	public long saveDestUser(String destUserId, String userId, String userName, String nickName, 
//			String alias, String superstar, String userImg) {
//		long count = 0;
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.DestUserId),null ==destUserId? "" : destUserId);
//		values.put(context.getString(R.string.UserInfoTitleUserId),null ==userId? "" : userId);
//		values.put(context.getString(R.string.UserUsername),null ==userName? "" : userName);
//		values.put(context.getString(R.string.UserNickname),null ==nickName? "" : nickName);
//		values.put(context.getString(R.string.UserInfoTitleAlias),null ==alias? "" : alias);
//		values.put(context.getString(R.string.UserInfoTitleSuperstar),null ==superstar? "" : superstar);
//		values.put(context.getString(R.string.TitleInfoTitleUserImg),null ==userImg? "" : userImg);
//		count = db.replace(context.getString(R.string.my_dynamic_destuser_table_name), null,values);
//		return count;
//	}
//	
//	public DestUser getDestUser(String userId) {
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		DestUser bean =null;
//		Cursor cur = db.query(context.getString(R.string.my_dynamic_destuser_table_name),null, context.getString(R.string.UserInfoTitleUserId)+" =?", new String[]{userId},null,null,null);
//		for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//			bean= new DestUser();
//			bean.setUserid(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserId))));
//			bean.setUsername(cur.getString(cur.getColumnIndex(context.getString(R.string.UserUsername))));
//			bean.setNickname(cur.getString(cur.getColumnIndex(context.getString(R.string.UserNickname))));
//			bean.setAlias(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleAlias))));
//			bean.setSuperstar(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleSuperstar))));
//			bean.setUserimg(cur.getString(cur.getColumnIndex(context.getString(R.string.TitleInfoTitleUserImg))));
//		}
//		cur.close();	
//		return bean;
//	}
//	/**
//	 * 删除DestUser
//	 * @param userId
//	 * @return
//	 */
//	public int deleteDestUser(String userId) {
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		int result = db.delete(context.getString(R.string.my_dynamic_destuser_table_name),
//				context.getString(R.string.UserInfoTitleUserId)+" = ?", new String[] { userId});
//		return result;
//	}
//	public int deleteMyRoleByRoleId(String roleId) {
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		int result = db.delete(context.getString(R.string.MyRoleInfoTableName),
//				context.getString(R.string.RoleInfoId)+" = ?", new String[] { roleId + "" });
//		return result;
//	}
//	public int deleteMyRoleByUserId(String userId) {
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		int result = db.delete(context.getString(R.string.MyRoleInfoTableName),
//				context.getString(R.string.RoleInfoUserId)+" = ?", new String[] { userId});
//		return result;
//	}
//	
//	/**
//	 * 更新消息
//	 * @param UserName
//	 * @param NickName
//	 * @param ImgId
//	 * @return
//	 */
//	public int UpsaveMsg(String UserName,String NickName,String ImgId) {
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.MsgInfoTitleNickName), NickName);
//		values.put(context.getString(R.string.MsgInfoTitleImg), ImgId);
//		int a = db.update(context.getString(R.string.MsgInfoTableName),
//				values, context.getString(R.string.MsgInfoTitleUserName) + "=?", 
//				new String[] { String.valueOf(UserName) });
//		return a;
//	}
//	/**
//	 * 更新消息状态
//	 * @param packetId
//	 * @param newStatus
//	 * @return
//	 */
//	public int upDateMsgStatus(String packetId,int newStatus) {
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.MsgInfoTitleMsgStatus), newStatus);
//		int a = db.update(context.getString(R.string.MsgInfoTableName),
//				values, context.getString(R.string.MsgInfoTitlePacketId) + "=?", 
//				new String[] { packetId });
//		return a;
//	}
//	/**
//	 * 
//	 * @param packetId
//	 * @param UserName
//	 * @param MsgContent
//	 * @param date
//	 * @param isLeft
//	 * @param isRead
//	 * @param msgType
//	 * @param msgTitle
//	 * @param payload
//	 * @param WithWho
//	 * @param Status
//	 * @param receiveDate
//	 * @return
//	 */
//	public long saveMsg(String packetId,String UserName, String MsgContent, String date, String isLeft, 
//			String isRead,String msgType,String msgTitle,String payload,String WithWho,int Status,String msgTag) {
//		SQLiteDatabase db = getHelper().getWritableDatabase();
//		String myUserName = HttpUser.Username;
//		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.MsgInfoTitlePacketId),null == packetId ? "" : packetId);
//		values.put(context.getString(R.string.MsgInfoTitleUserName),null == UserName ? "" : UserName);
//		values.put(context.getString(R.string.MsgInfoTitleMsgContent),null == MsgContent? "" : MsgContent);
//		values.put(context.getString(R.string.MsgInfoTitleDate),null == date? "" : date);
//		values.put(context.getString(R.string.MsgInfoTitleIsLeft), isLeft);
//		values.put(context.getString(R.string.MsgInfoTitleIsRead), isRead);
//		values.put(context.getString(R.string.MsgInfoTitleMyUserName),null == myUserName? "" : myUserName);
//		values.put(context.getString(R.string.MsgInfoTitleMsgType), null == msgType? "" : msgType);
//		values.put(context.getString(R.string.MsgInfoTitleMsgTitle), null == msgTitle? "" : msgTitle);
//		values.put(context.getString(R.string.MsgInfoTitleMsgPayLoad), null == payload? "" : payload);
//		values.put(context.getString(R.string.MsgInfoTitleMsgWithWho), null == WithWho? "" : WithWho);
//		values.put(context.getString(R.string.MsgInfoTitleMsgStatus),Status);
//		System.out.println("pickId--"+packetId+"--ReceiveTime--"+String.valueOf(new Date().getTime()));
//		values.put(context.getString(R.string.MsgInfoTitleMsgReceiveTime),String.valueOf(new Date().getTime()));
//		values.put(context.getString(R.string.MsgInfoTitleMsgTag), null == msgTag? "" : msgTag);
//		long count = db.replace(context.getString(R.string.MsgInfoTableName),null, values);
//		return count;
//	}
//    /**
//	 * 根据对面用户名查找所有的聊天记录
//	 * @param userName
//	 * @return
//	 */
//	public ArrayList<MyMessage> selectAllMsgByUserNameAndMyName(String userName,int page,int pageNum) {
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		ArrayList<MyMessage> listData = new ArrayList<MyMessage>();
//		MyMessage msg;
//		Cursor cur = db.query(context.getString(R.string.MsgInfoTableName),null,
//						context.getString(R.string.MsgInfoTitleMsgWithWho) + " = ?  and ("
//						+ context.getString(R.string.MsgInfoTitleMsgType) + " = ? or "+ context.getString(R.string.MsgInfoTitleMsgType) + " = ?)", 
//						new String[] { userName,Constants.NORMALCHAT,Constants.SYSTEM},
//						null,null,"mainId desc limit "+ String.valueOf(page)+","+ String.valueOf(pageNum));
//		for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//			msg = new MyMessage();
//			msg.setId(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitlePacketId))));
////			msg.setMsgTime(MyDate.getDateFromLong(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleDate)))));
//			msg.setMsgTime(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleDate))));
//			msg.setPayLoad(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgPayLoad))));
//			msg.setBody(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgContent))));
//			msg.setFrom(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleUserName))));
//			msg.setToWho(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgWithWho))));
//			msg.setStatus(MessageStatus.getMessageStatusForValue(cur.getInt(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgStatus)))));
//			msg.setMsgtype(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgType))));
//			msg.setReceiveTime(MyDate.getDateFromLong(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgReceiveTime)))));
//			listData.add(msg);
//		}
//		cur.close();
//		Collections.sort(listData, new Comparator<MyMessage>() {
//			@Override
//			public int compare(MyMessage lhs, MyMessage rhs) {
//				if(lhs.getReceiveTime()==null||rhs.getReceiveTime()==null){
//					return 1;
//				}
//				return String.valueOf(lhs.getReceiveTime().getTime()).compareTo(String.valueOf(rhs.getReceiveTime().getTime()));
//			}
//		});
//		return listData;
//	}
//	/**
//	 * 根据MsgType查询新闻
//	 * @return
//	 */
//	public ArrayList<DailyNewsInfo> selectNewsMsgByMsgType(String MsgType, int page,int pageNum) {
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		ArrayList<DailyNewsInfo> listData = new ArrayList<DailyNewsInfo>();
//		DailyNewsInfo data = null;
//		Cursor cur = db.query(context.getString(R.string.MsgInfoTableName),null,context.getString(R.string.MsgInfoTitleMsgType) + " = ? ", 
//						new String[] { MsgType},
//						null,null,"mainId desc limit "+ String.valueOf(page)+","+ String.valueOf(pageNum));
//		for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//			String payload = cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgPayLoad)));
//			data = JsonUtils.resultData(payload, DailyNewsInfo.class);
//			listData.add(data);
//		}
//		cur.close();
//		return listData;
//	}
//	 /**
//	  * 根据packetId查消息
//	  * @param packetId
//	  * @return
//	  */
		public MyMessage selectMsgByPacketId(String packetId) {
			return null;
//			SQLiteDatabase db = getHelper().getReadableDatabase();
//			MyMessage msg = new MyMessage();
//			Cursor cur = db.query(context.getString(R.string.MsgInfoTableName),null,
//					context.getString(R.string.MsgInfoTitlePacketId) + " = ? and "+ context.getString(R.string.MsgInfoTitleMsgType) + " = ?", 
//							new String[] { packetId,Constants.NORMALCHAT}, null,null, null);
//			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//				msg.setId(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitlePacketId))));
//				msg.setMsgTime(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleDate))));
////				msg.setMsgTime(MyDate.getDateFromLong(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleDate)))));
//				msg.setPayLoad(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgPayLoad))));
//				msg.setBody(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgContent))));
//				msg.setFrom(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleUserName))));
//				msg.setToWho(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgWithWho))));
//				msg.setMsgtype(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgType))));
//				msg.setReceiveTime(MyDate.getDateFromLong(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgReceiveTime)))));
//				msg.setStatus(MessageStatus.getMessageStatusForValue(cur.getInt(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgStatus)))));
//			}
//			cur.close();
//			return msg;
		}

//	public ArrayList<MyMessage> selectMsgListByMsgTypes(String msgType) {
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		ArrayList<MyMessage> listData = new ArrayList<MyMessage>();
//		Cursor cur = db.query(context.getString(R.string.MsgInfoTableName),null, context.getString(R.string.MsgInfoTitleMsgType)+ " = ?" , new String[] { msgType},null,null,null);
//		for (cur.moveToLast(); !cur.isBeforeFirst(); cur.moveToPrevious()) {
//			MyMessage myMessage=getMyMessage(cur);
//			listData.add(myMessage);
//		}
//		cur.close();
//		return listData;
//	}
//	public ArrayList<MyMessage> selectSayHelloAndDeletePersons() {
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		ArrayList<MyMessage> listData = new ArrayList<MyMessage>();
//		Cursor cur = db.query(context.getString(R.string.MsgInfoTableName),null, context.getString(R.string.MsgInfoTitleMsgType)
//				+ " = ? or " +context.getString(R.string.MsgInfoTitleMsgType) + " = ? " , new String[] { Constants.SAYHELLO,Constants.DELETE_PERSON},null,null,null);
//		for (cur.moveToLast(); !cur.isBeforeFirst(); cur.moveToPrevious()) {
//			MyMessage message=getMyMessage(cur);
//			listData.add(message);
//		}
//		cur.close();
//		return listData;
//	}
//	/**
//	 * 关注，取消关注消息
//	 * @return
//	 */
//	public MyMessage selectLastAttentionOrDeletePersonMsgs() {
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		MyMessage myMessage=null;
//		Cursor cur = db.query(context.getString(R.string.MsgInfoTableName),null, context.getString(R.string.MsgInfoTitleMsgType)
//				+ " = ? or " +context.getString(R.string.MsgInfoTitleMsgType) + " = ? " , new String[] 
//						{ Constants.SAYHELLO,Constants.DELETE_PERSON},null,null,null);
//		if(cur.moveToLast()) {
//			myMessage=getMyMessage(cur);
//		}
//		cur.close();
//		return myMessage;
//	}
//	/**
//	 * 每日新闻消息
//	 * @return
//	 */
//	public MyMessage selectLastDailyNewsMsgs() {
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		MyMessage myMessage=null;
//		Cursor cur = db.query(context.getString(R.string.MsgInfoTableName),null, context.getString(R.string.MsgInfoTitleMsgType)+ " = ? ",
//				new String[] { Constants.DAILY_NEWS},null,null,null);
//		if(cur.moveToLast()) {
//			myMessage=getMyMessage(cur);
//		}
//		cur.close();
//		return myMessage;
//	}
//	
//	/**
//	 * 头衔，战斗力，角色消息
//	 * @return
//	 */
//	public MyMessage selectLastCharacterPveScoreOrTitleMsgs() {
//		SQLiteDatabase db = getHelper().getReadableDatabase();
//		MyMessage myMessage=null;
//		Cursor cur = db.query(context.getString(R.string.MsgInfoTableName),null, context.getString(R.string.MsgInfoTitleMsgType)
//				+ " = ? or " +context.getString(R.string.MsgInfoTitleMsgType) + " = ? or " 
//				+ context.getString(R.string.MsgInfoTitleMsgType) + " = ? " ,
//				new String[] { Constants.CHARACTER,Constants.TITLE,Constants.PVESCORE},null,null,null);
//		if(cur.moveToLast()) {
//			myMessage=getMyMessage(cur);
//		}
//		cur.close();
//		return myMessage;
//	}
	/**
	 * 聊天消息
	 * @param msgType
	 * @return
	 */
	public ArrayList<MyMessage> selectLastMsgByMsgType(String msgType) {
		SQLiteDatabase db = getHelper().getReadableDatabase();
		ArrayList<MyMessage> listData = new ArrayList<MyMessage>();
//		Cursor cur = db.query(context.getString(R.string.MsgInfoTableName),null, context.getString(R.string.MsgInfoTitleMsgType)
//				+ " = ?", new String[] {msgType},context.getString(R.string.MsgInfoTitleMsgWithWho),null,context.getString(R.string.MsgInfoTitleMsgReceiveTime));
//		for (cur.moveToNext(); !cur.isAfterLast(); cur.moveToNext()) {
//			MyMessage myMessage=getMyMessageChating(cur);
//			listData.add(myMessage);
//		}
//		cur.close();
		return listData;
	}
	/**
	 * 最后一条打招呼的消息
	 * @param msgTag
	 * @return
	 */
	public MyMessage selectLastSayHelloMsgByMsgTag(String msgTag) {
		SQLiteDatabase db = getHelper().getReadableDatabase();
		MyMessage myMessage=null;
//		Cursor cur = db.query(context.getString(R.string.MsgInfoTableName),null, context.getString(R.string.MsgInfoTitleMsgTag)
//				+ " = ? and "+context.getString(R.string.MsgInfoTitleMsgType)+" = ?", new String[] { msgTag,Constants.NORMALCHAT },context.getString(R.string.MsgInfoTitleMsgWithWho)+"," 
//				+ context.getString(R.string.MsgInfoTitleMsgType),null,
//				context.getString(R.string.MsgInfoTitleMsgReceiveTime));
//		if (cur.moveToLast()) {
//			myMessage=getMyMessageChating(cur);
//		}
//		cur.close();
		return myMessage;
	}
	/**
	 * 未读的聊天消息
	 * @param msgTag
	 * @return
	 */
	public ArrayList<MyMessage> selectNormalChatMsgByMsgTag(String msgTag) {
		SQLiteDatabase db = getHelper().getReadableDatabase();
		ArrayList<MyMessage> listData = new ArrayList<MyMessage>();
//		Cursor cur = db.query(context.getString(R.string.MsgInfoTableName),null, context.getString(R.string.MsgInfoTitleMsgTag)
//				+ " = ? and "+context.getString(R.string.MsgInfoTitleMsgType)+" = ?", 
//				new String[] { msgTag ,Constants.NORMALCHAT},context.getString(R.string.MsgInfoTitleMsgWithWho),null,context.getString(R.string.MsgInfoTitleMsgReceiveTime));
//		for (cur.moveToLast(); !cur.isBeforeFirst(); cur.moveToPrevious()) {
//			MyMessage myMessage=getMyMessageChating(cur);
//			listData.add(myMessage);
//		}
//		cur.close();
		return listData;
	}
//	public int selectUserCount(String msgTag) {
		SQLiteDatabase db = getHelper().getReadableDatabase();
//		Cursor cur = db.query(context.getString(R.string.MsgInfoTableName),null, context.getString(R.string.MsgInfoTitleMsgTag)
//				+ " = ? and "+context.getString(R.string.MsgInfoTitleMsgType)+" = ?", 
//				new String[] { msgTag ,Constants.NORMALCHAT},context.getString(R.string.MsgInfoTitleMsgWithWho),null,context.getString(R.string.MsgInfoTitleMsgReceiveTime));
//		int userCount=cur.getCount();
//		cur.close();
//		return userCount;
//	}
	/**
	 * 推荐消息
	 * @return
	 */
	public MyMessage selectLastRecommendMsgs() {
		SQLiteDatabase db = getHelper().getReadableDatabase();
		MyMessage myMessage=null;
//		Cursor cur = db.query(context.getString(R.string.MsgInfoTableName),null, context.getString(R.string.MsgInfoTitleMsgType)+ " = ? ",
//				new String[] { Constants.RECOMMEND_FRIEND},null,null,null);
//		if(cur.moveToLast()) {
//			myMessage=getMyMessage(cur);
//		}
//		cur.close();
		return myMessage;
	}
	
	/**
	 * 查询最后一条好友动态
	 * @return
	 */
	public MyMessage selectLastFriendDynamicMsg() {
		SQLiteDatabase db = getHelper().getReadableDatabase();
		MyMessage myMessage=null;
//		Cursor cur = db.query(context.getString(R.string.MsgInfoTableName),null, context.getString(R.string.MsgInfoTitleMsgType)+ " = ? ",
//				new String[] { Constants.FRIEND_DYNAMIC_MSG},null,null,null);
//		if(cur.moveToLast()) {
//			myMessage=getMyMessage(cur);
//		}
//		cur.close();
		return myMessage;
	}
	
	private MyMessage getMyMessage(Cursor cur){
		return null;
//		MyMessage myMessage=new MyMessage();
//		myMessage.setBody(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgContent))));
//		myMessage.setFrom(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleUserName))));
//		myMessage.setId(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitlePacketId))));
//		myMessage.setMsgTime(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleDate))));
////		myMessage.setMsgTime(MyDate.getDateFromLong(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleDate)))));
//		myMessage.setMsgtype(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgType))));
//		myMessage.setPayLoad(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgPayLoad))));
//		myMessage.setReceiveTime(MyDate.getDateFromLong(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgReceiveTime)))));
//		myMessage.setMsgTag(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgTag))));
//		return myMessage;
	}
	private MyMessage getMyMessageChating(Cursor cur){
		MyMessage myMessage=new MyMessage();
//		myMessage.setBody(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgContent))));
//		myMessage.setFrom(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgWithWho))));
//		myMessage.setId(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitlePacketId))));
//		myMessage.setMsgTime(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleDate))));
////		myMessage.setMsgTime(MyDate.getDateFromLong(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleDate)))));
//		myMessage.setMsgtype(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgType))));
//		myMessage.setPayLoad(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgPayLoad))));
//		myMessage.setReceiveTime(MyDate.getDateFromLong(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgReceiveTime)))));
//		myMessage.setMsgTag(cur.getString(cur.getColumnIndex(context.getString(R.string.MsgInfoTitleMsgTag))));
//		return myMessage;
		return myMessage;
	}
	
	/**
	 * 查找未读消息的个数
	 * 
	 * @return
	 */
	public int searchNotReadMsgNum() {
		SQLiteDatabase db = getHelper().getReadableDatabase();
//		Cursor cur = db.query(context.getString(R.string.MsgInfoTableName),null,
//				context.getString(R.string.MsgInfoTitleIsRead) + " = ?  and "+context.getString(R.string.MsgInfoTitleMsgType)+" <> ? and " +
//				       context.getString(R.string.MsgInfoTitleMsgType) + " <> ?", new String[] { "false",Constants.MY_DYNAMIC_MSG,Constants.FRIEND_DYNAMIC_MSG }, null,
//				null, null);
//		if (cur != null) {
//			int max = cur.getCount();
//			cur.close();
//			return max;
//		}
		
		return 0;
	}
	
	/**
	 * 根据用户名查找未读消息的个数
	 * 
	 * @param userName
	 * @return
	 */
	public int searchNotReadMsgNumByUserName(String userName,String msgType) {
		SQLiteDatabase db = getHelper().getReadableDatabase();
//		Cursor cur = db.query(
//				context.getString(R.string.MsgInfoTableName),null,
//				context.getString(R.string.MsgInfoTitleIsRead) + " = ?  and "
//				+ context.getString(R.string.MsgInfoTitleUserName)+ " = ? and "
//						+ context.getString(R.string.MsgInfoTitleMsgType)+ " = ? ",
//				new String[] { "false", userName, msgType}, null, null,
//				null);
//		if (cur != null) {
//			int max = cur.getCount();
//			cur.close();
//			return max;
//		}
		
		return 0;
	}
	/**
	 * 根据msgType查找未读消息的个数
	 * 
	 * @return
	 */
	public int searchNotReadAttentionMsgNumByUserName(String msgType) {
		SQLiteDatabase db = getHelper().getReadableDatabase();
//		Cursor cur = db.query(
//				context.getString(R.string.MsgInfoTableName),null,
//				context.getString(R.string.MsgInfoTitleIsRead) + " = ?  and "
//						+ context.getString(R.string.MsgInfoTitleMsgType)
//						+ " = ? ",
//				new String[] { "false",msgType}, null, null,
//				null);
//		if (cur != null) {
//			int max = cur.getCount();
//			cur.close();
//			return max;
//		}
		
		return 0;
	}
	public int searchNotReadAttentionMsgNumBymsgTag(String msgTag) {
		SQLiteDatabase db = getHelper().getReadableDatabase();
//		Cursor cur = db.query(
//				context.getString(R.string.MsgInfoTableName),null,
//				context.getString(R.string.MsgInfoTitleIsRead) + " = ?  and "
//						+ context.getString(R.string.MsgInfoTitleMsgTag)
//						+ " = ? ",
//						new String[] { "false",msgTag}, null, null,
//						null);
//		if (cur != null) {
//			int max = cur.getCount();
//			cur.close();
//			return max;
//		}
		
		return 0;
	}
	
	/**
	 * 根据packetId查找消息个数
	 * @param packetId
	 * @return
	 */
	public int searchMsgNumByPacketId(String packetId) {
		SQLiteDatabase db = getHelper().getReadableDatabase();
//		Cursor cur = db.query(
//				context.getString(R.string.MsgInfoTableName),null,
//				context.getString(R.string.MsgInfoTitlePacketId) + " = ?  ",
//				new String[] {packetId}, null, null,
//				null);
//		if (cur != null) {
//			int max = cur.getCount();
//			cur.close();
//			return max;
//		}
		
		return 0;
	}
	
	/**
	 * 根据用户名，将打招呼变为已同意通过
	 */
	public int changeShipType(String userId,String type) {
		// TODO 判断关系是否存在，不存在要插入
		SQLiteDatabase db = getHelper().getWritableDatabase();
		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.UserInfoTitleShipType), type);
//		int cus=db.update(context.getString(R.string.ContactsUserInfoTableName), values,
//				context.getString(R.string.UserInfoTitleUserId) 
//				+ " = ? ",
//				new String[] { userId });
//		return cus;
		return 0;
	}
	public int changeActivite(String userId,String activate) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.UserInfoTitleActive), activate);
//		int cus=db.update(context.getString(R.string.ContactsUserInfoTableName), values,
//				context.getString(R.string.UserInfoTitleUserId) 
//				+ " = ? ",
//				new String[] { userId });
//		return cus;
		return 0;
	}
	/**
	 * 根据用户Id修改备注
	 */
	public int changeAlias(String userId,String aliasName,String nameShort) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.UserInfoTitleAlias), aliasName);
//		values.put(context.getString(R.string.UserInfoTitleNameSort), nameShort);
//		int cus=db.update(context.getString(R.string.ContactsUserInfoTableName), values,
//				context.getString(R.string.UserInfoTitleUserId) 
//				+ " = ? ",
//				new String[] { userId });
//		return cus;
		return 0;
	}
	/**
	 * 根据用户名消息变为已读
	 */
	public void changeAllMsgToisRead(String userName) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.MsgInfoTitleIsRead), "true");
//		db.update(context.getString(R.string.MsgInfoTableName),values,context.getString(R.string.MsgInfoTitleUserName)+ " = ? ", new String[] {userName });
	}
	public void changeMsgToRead(String... msgTypes){
		for(String msgType:msgTypes){
			changeAllMsgToisReadByMsgType(msgType);
		}
	}
	
	/**
	 * 根据msgType消息变为已读
	 */
	public void changeAllMsgToisReadByMsgType(String msgType) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.MsgInfoTitleIsRead), "true");
//		db.update(context.getString(R.string.MsgInfoTableName),values,context.getString(R.string.MsgInfoTitleMsgType)+ " = ?", new String[] {msgType});
	}
	/**
	 * 根据msgType消息变为已读
	 */
	public void changeAllMsgToisReadByMsgType(String userName,String msgType) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.MsgInfoTitleIsRead), "true");
//		db.update(context.getString(R.string.MsgInfoTableName),values,
//						context.getString(R.string.MsgInfoTitleUserName)+ " = ? and " 
//						+ context.getString(R.string.MsgInfoTitleMsgType)+ " = ? ",
//						new String[] { userName, msgType });
	}
	/**
	 * 根据msgType消息变为已读
	 */
	public void changeMsgToisReadByPacketId(String packetId) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.MsgInfoTitleIsRead), "true");
//		db.update(context.getString(R.string.MsgInfoTableName),values,
//				context.getString(R.string.MsgInfoTitlePacketId)+ " = ?",new String[] { packetId});
	}
	/**
	 * 根据msgTag改变已读状态
	 * @param msgTag
	 */
	public void changeMsgToisReadByMsgTag(String msgTag) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.MsgInfoTitleIsRead), "true");
//		db.update(context.getString(R.string.MsgInfoTableName),values,
//				context.getString(R.string.MsgInfoTitleMsgTag)+ " = ?",new String[] { msgTag});
	}
	public void changeMsgTagByUserId(String userId,String msgTag) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.MsgInfoTitleMsgTag), msgTag);
//		db.update(context.getString(R.string.MsgInfoTableName),values,
//				context.getString(R.string.MsgInfoTitleMsgWithWho)+ " = ?",new String[] { userId});
	}
	public void changeMsgToisReadByMainId(String packetId) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
		ContentValues values = new ContentValues();
//		values.put(context.getString(R.string.MsgInfoTitleIsRead), "true");
//		db.update(context.getString(R.string.MsgInfoTableName),values,context.getString(R.string.MsgInfoTitlePacketId)+" = ?", new String[] {packetId });
	}
	
	public void clearMsgByMsgType(String... msgTypes){
		for(String msgType:msgTypes){
			this.clearMsgByMsgType(msgType);
		}
	}
	/**
	 * 根据msgType把相应类型的消息清空
	 * @param msgType
	 * @return
	 */
	public int clearMsgByMsgType(String msgType) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
//		int result = db.delete(context.getString(R.string.MsgInfoTableName),context.getString(R.string.MsgInfoTitleMsgType)+" = ?", new String[] { msgType });
//		return result;
		return 0;
	}
	public int deleteMsgByWithWhoAndMsgType(String userName,String msgType) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
//		int result = db.delete(context.getString(R.string.MsgInfoTableName),context.getString(R.string.MsgInfoTitleMsgWithWho)+" = ? and "+context.getString(R.string.MsgInfoTitleMsgType)+" = ?", new String[] {userName,msgType });
//		return result;
		return 0;
	}
	/**
	 * 根据userName 和 msgType删除单条消息
	 * @param userName
	 * @param msgType
	 * @return
	 */
	public int deleteMsgByUserNameAndMsgType(String userName,String msgType) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
//		int result = db.delete(context.getString(R.string.MsgInfoTableName),
//				context.getString(R.string.MsgInfoTitleUserName)+" = ? and "+
//				context.getString(R.string.MsgInfoTitleMsgType)+" = ?", new String[] {userName,msgType });
//		return result;
		return 0;
	}
	public int deleteMsgBymsgTag(String msgTag) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
//		int result = db.delete(context.getString(R.string.MsgInfoTableName),
//				context.getString(R.string.MsgInfoTitleMsgTag)+" = ?", new String[] {msgTag});
//		return result;
		return 0;
	}
	/**
	 * 根据userNamed删除单条消息
	 * @param userName
	 * @return
	 */
	public int deleteMsgByUserName(String userName) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
//		int result = db.delete(context.getString(R.string.MsgInfoTableName),context.getString(R.string.MsgInfoTitleUserName)+ " = ? and " 
//		+ context.getString(R.string.MsgInfoTitleMsgType)+ " <> ? ", new String[] {userName,Constants.NORMALCHAT});
//		return result;
		return 0;
	}
	public int deleteMsgByUserId(String userName) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
//		int result = db.delete(context.getString(R.string.MsgInfoTableName),context.getString(R.string.MsgInfoTitleMsgWithWho)+ " = ?", new String[] {userName});
//		return result;
		return 0;
	}
	/**
	 * 根据mainId删除单条消息
	 * @param userName
	 * @return
	 */
	public int deleteMsgByUserNameAndMainId(String packetId) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
//		int result = db.delete(context.getString(R.string.MsgInfoTableName),context.getString(R.string.MsgInfoTitlePacketId)+" = ? ", new String[] {packetId});
//		return result;
		return 0;
	}
	
	/**
	 * 删除单角色消息
	 * @param userName
	 * @return
	 */
	public int deleteRoleMsg() {
		SQLiteDatabase db = getHelper().getWritableDatabase();
//		String sql = context.getString(R.string.MsgInfoTitleMsgType)
//		+ " = ? or " +context.getString(R.string.MsgInfoTitleMsgType) + " = ? or " 
//		+ context.getString(R.string.MsgInfoTitleMsgType) + " = ? ";
//		int result = db.delete(context.getString(R.string.MsgInfoTableName),sql, 
//				new String[] { Constants.CHARACTER,Constants.TITLE,Constants.PVESCORE});
//		return result;
		return 0;
	}
	/**
	 * 获取联系人的信息（自己的或者其他用户的）
	 * */
	public synchronized List<User> getFrienderDetailInfo(String userId) {
		SQLiteDatabase db = getHelper().getReadableDatabase();
		ArrayList<User> listUsers = new ArrayList<User>();
		User u;
//		Cursor cur = db.query(context.getString(R.string.ContactsUserInfoTableName),null, context.getString(R.string.UserInfoTitleUserId)
//				+ " = ?", new String[] { userId },null,null,null);
//		for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//			u = new User();
//			u.setShipType(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleShipType))));
//			u.setId(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserId))));
//			u.setUsername(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserName))));
//			u.setNickname(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleNickName))));
//			u.setAge(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserAge))));
//			u.setBirthdate(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserBirthDate))));
//			u.setSignature(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleSignature))));
//			u.setDistance(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserDistance))));
//			u.setGender(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserGender))));
//			u.setHobby(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserHobby))));
//			u.setImg(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserImgId))));
//			u.setLatitude(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserLatitude))));
//			u.setLongitude(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserLongitude))));
//			u.setCity(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserCity))));
//			u.setRarenum(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleRarenum))));
//			u.setAlias(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleAlias))));
//			u.setRemark(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleRemark))));
//			u.setUpdateUserLocationDate(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUpdateUserLocationDate))));
//			u.setPassword(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitlePassword))));
//			u.setCreateTime(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleCreateTime))));
//			u.setConstellation(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleConsteLlation))));
//			u.setPhoneNumber(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitlePhoneNumber))));
//			u.setEmail(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleEmail))));
//			u.setRealname(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleRealname))));
//			u.setIfFraudulent(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleIfFraudulent))));
//			u.setDeviceToken(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleDeviceToken))));
//			u.setBackgroundImg(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleBackGroundImg))));
//			u.setModTime(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleModTime))));
//			u.setSuperstar(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleSuperstar))));
//			u.setNameSort(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleNameSort))));
//			u.setActive(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleActive))));
//			u.setSuperremark(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleSuperremark))));
//			u.setTitle(this.GetTitle(u.getId()));
//			listUsers.add(u);
//		}
//		cur.close();
		return listUsers;
	}
	/**
	 * 获取联系人简单的信息（自己的或者其他用户的）
	 * */
	public synchronized User getFrienderSimpleDetailInfo(String userId) {
		SQLiteDatabase db = getHelper().getReadableDatabase();
		User u = null;
//		Cursor cur = db.query(context.getString(R.string.ContactsUserInfoTableName),
//				new String[]{context.getString(R.string.UserInfoTitleUserId),context.getString(R.string.UserInfoTitleAlias),
//			context.getString(R.string.UserInfoTitleNickName),context.getString(R.string.UserInfoTitleUserImgId),context.getString(R.string.UserInfoTitleUserGender)},
//				context.getString(R.string.UserInfoTitleUserId)
//				+ " = ?", new String[] { userId },null,null,null);
//		for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//			u = new User();
//			u.setId(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserId))));
//			u.setAlias(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleAlias))));
//			u.setNickname(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleNickName))));
//			u.setImg(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserImgId))));
//			u.setGender(cur.getString(cur.getColumnIndex(context.getString(R.string.UserInfoTitleUserGender))));
//		}
//		cur.close();
		return u;
	}
	/**
	 * 判断改用户是否存在
	 * */
	public synchronized boolean isFrienderInfoExist(String userId) {
		SQLiteDatabase db = getHelper().getReadableDatabase();
		boolean isExist = false;
//		Cursor cur = db.query(context.getString(R.string.ContactsUserInfoTableName),
//				new String[]{context.getString(R.string.UserInfoTitleUserId)},
//			context.getString(R.string.UserInfoTitleUserId)
//			+ " = ?", new String[] { userId },null,null,null);
//		if(cur.moveToFirst()){
//			isExist = true;
//		}
//		cur.close();
		return isExist;
	}
	/**
	 * 是否是联系人的关系
	 * */
	public int isFriendOrAttention(String userId,String shipType) {
		SQLiteDatabase db = getHelper().getReadableDatabase();
//		Cursor cur = db.query(context.getString(R.string.ContactsUserInfoTableName),null, context.getString(R.string.UserInfoTitleUserId)
//				+ " = ? and "+context.getString(R.string.UserInfoTitleShipType)+" = ? ", new String[] { userId,shipType},null,null,null);
//		int count = cur.getCount();
//		if(!cur.isClosed()){
//			cur.close();
//		}
//		return count;
		return 0;
	}
	public int deleteOneMsgByMsgID(String Id) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
//		int result = db.delete(context.getString(R.string.MsgInfoTableName),context.getString(R.string.MsgInfoTitlePacketId)+" = ?", new String[] { Id + "" });
//		
//		return result;
		return 0;
	}
	public void cleanTitleInfo() {
		SQLiteDatabase db = getHelper().getWritableDatabase();
//		db.delete(context.getString(R.string.TitleInfoTableName), null,null);
	}
	public void cleanRoleInfo() {
		SQLiteDatabase db = getHelper().getWritableDatabase();
//		db.delete(context.getString(R.string.MyRoleInfoTableName), null,null);
	}
	/**
	 * type清除相应的头衔
	 * @param msgType
	 * @return
	 */
	public void cleanTitleInfoByType(String type) {
		SQLiteDatabase db = getHelper().getWritableDatabase();
//		db.delete(context.getString(R.string.MyRoleInfoTableName),context.getString(R.string.TitleInfoTitleHide)+" = ?", new String[] { type });
	}
	/**、
	 * 清除数据表数据
	 */
	public void CleanContacts() {
		SQLiteDatabase db = getHelper().getWritableDatabase();
//		db.delete(context.getString(R.string.ContactsUserInfoTableName), null,null);
//		db.delete(context.getString(R.string.TitleInfoTableName), null,null);
//		db.delete(context.getString(R.string.TitleObjectInfoTableName), null,null);
//		db.delete(context.getString(R.string.MyRoleInfoTableName), null,null);
//		db.delete(context.getString(R.string.my_dynamic_table_name), null,null);
//		db.delete(context.getString(R.string.my_zan_fans_table_name), null,null);
//		db.delete(context.getString(R.string.my_dynamic_destuser_table_name), null,null);
	}
	public void saveMsg(MyMessage myMessage) {
//		this.saveMsg(myMessage.getId(),myMessage.getFrom(), myMessage.getBody(),getMstTime(myMessage.getMsgTime()), "false", "true", Constants.NORMALCHAT,"",myMessage.getPayLoad(),myMessage.getToWho(),myMessage.getStatus().getValue(),"1");
	}
	private String getMstTime(String msgTime){
		if(!StringUtils.isNotEmty(msgTime)){
			return String.valueOf(new Date().getTime());
		}
		return msgTime;
	}
}
