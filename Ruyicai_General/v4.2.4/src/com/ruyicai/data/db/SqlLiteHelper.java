package com.ruyicai.data.db;

import com.palmdream.RuyicaiAndroid.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlLiteHelper extends SQLiteOpenHelper {
		private final DbHelper dbHelper;
		
		private static final int DB_VERSION = 4;
		
		public SqlLiteHelper(DbHelper dbHelper, Context context,String userid) {
			super(context, "GameChat"+userid, null, DB_VERSION);
			this.dbHelper = dbHelper;
		}
		@Override
		public synchronized SQLiteDatabase getReadableDatabase() {
			return super.getReadableDatabase();
		}
		@Override
		public synchronized SQLiteDatabase getWritableDatabase() {
			return super.getWritableDatabase();
		}
		
		@Override
		public synchronized void close() {
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
//			/**Contancts表*/
//			String sqlContactsUSer = "CREATE TABLE  "
//					+ this.dbHelper.context.getString(R.string.ContactsUserInfoTableName)
//					+ " ( " + this.dbHelper.context.getString(R.string.UserInfoTitleUserId)
//					+ " nvarchar(50) PRIMARY KEY, "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleUserName)
//					+ " nvarchar(50) not null , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleNickName)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleShipType)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleUserAge)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleUserBirthDate)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleSignature)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleUserDistance)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleUserGender)
//					+ " nvarchar(10) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleUserHobby)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleUserImgId)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleUserLatitude)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleUserLongitude)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleUserCity)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleRarenum)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleAlias)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleRemark)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleUpdateUserLocationDate)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitlePassword)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleCreateTime)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleConsteLlation)
//					+ " nvarchar(10) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitlePhoneNumber)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleEmail)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleRealname)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleIfFraudulent)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleDeviceToken)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleBackGroundImg)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleModTime)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleSuperstar)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleNameSort)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleActive)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleSuperremark)
//					+ " nvarchar(50) )";
//			
//			/**Title表*/
//			String sqlTitle = "CREATE TABLE  "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTableName)
//					+ " ( " + this.dbHelper.context.getString(R.string.TitleInfoTitleId)
//					+ " nvarchar(50) PRIMARY KEY not null , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleRealm)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleUserId)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleCharaCterId)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleGameId)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleSortNum)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleTitleId)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleHasDate)
//					+ " nvarchar(10) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleUserImg)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleHide)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleCharaCterName)
//					+ " nvarchar(50) )";
//			
//			/**TitleObject表*/
//			String sqlTitleObject = "CREATE TABLE  "
//					+ this.dbHelper.context.getString(R.string.TitleObjectInfoTableName)
//					+ " ( " + this.dbHelper.context.getString(R.string.TitleInfoTitleObjectId)
//					+ " nvarchar(50) PRIMARY KEY not null , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleObjectIcon)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleObjectRank)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleObjectImg)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleObjectTitle)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleObjectCreateDate)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleObjectRarenum)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleObjectRemark)
//					+ " nvarchar(10) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleObjectGameId)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleObjectSortNum)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleObjectRareMemo)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleObjectTitleKey)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleObjectEvolution)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleObjectSimpleTitle)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleObjectRankvalType)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleObjectRankType)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleObjectTitleType)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleObjectRemarkDetail)
//					+ " nvarchar(50) )";
//			/**消息表*/
//			String sqlMsg = "CREATE TABLE   "
//					+ this.dbHelper.context.getString(R.string.MsgInfoTableName) + "("
//					+ "mainId" + " integer  primary key autoincrement,"
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitlePacketId)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleUserName)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleNickName)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgContent)
//					+ " nvarchar(500) , "
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleImg)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleIsRead)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleDate) 
//					+ " Date ,"
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleMyUserName)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleIsLeft)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgType)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleContentID)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleContentType)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleContent)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitlePicID)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgTitle)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgPayLoad)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgWithWho)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgStatus)
//					+ " integer ,"
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgReceiveTime)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgTag)
//					+ " nvarchar(50) )";
//			/**My Role表*/
//			String sqlMyRole = "CREATE TABLE  "
//					+ this.dbHelper.context.getString(R.string.MyRoleInfoTableName)
//					+ " ( " + this.dbHelper.context.getString(R.string.RoleInfoId)
//					+ " nvarchar(50) PRIMARY KEY not null , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoName)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoContent)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoLevel)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoLastModified)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoRealm)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoAuth)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoLastClazz)
//					+ " nvarchar(10) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoFilepath)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoiscatch)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoFailedmsg)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoGender)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoGuild)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoGuildRealm)
//					+ " nvarchar(10) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoTotalHonorableKills)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoMountsnum)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoOldplay)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoPveScore)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoFailednum)
//					+ " nvarchar(10) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoBattlegroup)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoRaceObj)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoRace)
//					+ " nvarchar(50) , "
//					+ this.dbHelper.context.getString(R.string.RoleInfoUserId)
//					+ " nvarchar(50) )";
//			/**我的动态表*/
//			String sqlMyDynamic = "CREATE TABLE  "
//					+ this.dbHelper.context.getString(R.string.my_dynamic_table_name)
//					+ " ( " + this.dbHelper.context.getString(R.string.UserInfoTitleUserId)
//					+ " nvarchar(50) PRIMARY KEY not null ,"
//					+ this.dbHelper.context.getString(R.string.UserUsername)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.RoleInfoId)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.UserImg)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.UserNickname)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.UserCreateTime)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.DynamicInfoTitle)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.DynamicInfoMsg)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.DynamicInfoShowTitle)
//					+ " nvarchar(50) )";
//			/**我的赞表*/
//			String sqlMyZanAndFans = "CREATE TABLE  "
//					+ this.dbHelper.context.getString(R.string.my_zan_fans_table_name)
//					+ " ( " + this.dbHelper.context.getString(R.string.UserId)
//					+ " nvarchar(50) PRIMARY KEY not null ,"
//					+ this.dbHelper.context.getString(R.string.UserInfoZan)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.UserInfoFans)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.UserInfofansFriendNum)
//					+ " nvarchar(50) )";
//			/**我的动态*/
//			String sqlMyDynamicDestUser = "CREATE TABLE  "
//					+ this.dbHelper.context.getString(R.string.my_dynamic_destuser_table_name)
//					+ " ( " + this.dbHelper.context.getString(R.string.DestUserId)
//					+ " nvarchar(50) PRIMARY KEY not null ,"
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleUserId)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.UserUsername)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.UserNickname)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleAlias)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.UserInfoTitleSuperstar)
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.TitleInfoTitleUserImg)
//					+ " nvarchar(50) )";
//			String sqlSayHello = "CREATE TABLE  "
//					+ this.dbHelper.context.getString(R.string.sayHelloListTable)
//					+ " ( "+ this.dbHelper.context.getString(R.string.userId)
//					+ " nvarchar(50)  PRIMARY KEY)";
//			
//			db.execSQL(sqlMsg);
//			db.execSQL(sqlContactsUSer);
//			db.execSQL(sqlTitle);
//			db.execSQL(sqlTitleObject);
//			db.execSQL(sqlMyRole);
//			db.execSQL(sqlMyDynamic);
//			db.execSQL(sqlMyZanAndFans);
//			db.execSQL(sqlMyDynamicDestUser);
//			db.execSQL(sqlSayHello);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if(oldVersion==1){
//				this.dbHelper.commonsharedPreferences.saveDbOldVersion(1);
//				String sqladd = "alter table "+this.dbHelper.context.getString(R.string.MsgInfoTableName)+" add "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgTag)+ " nvarchar(50) default 1";
//				String sqlSayHello1 = "drop TABLE  if exists "
//						+ this.dbHelper.context.getString(R.string.sayHelloListTable);
//				String sqlSayHello = "CREATE TABLE  "
//						+ this.dbHelper.context.getString(R.string.sayHelloListTable)
//						+ " ( "+ this.dbHelper.context.getString(R.string.userId)
//						+ " nvarchar(50)  PRIMARY KEY)";
//				db.execSQL(sqladd);
//				db.execSQL(sqlSayHello1);
//				db.execSQL(sqlSayHello);
			}
			if(oldVersion<3){
//				String removeSayHelloMessage = "delete from "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTableName)
//						+ " where MsgType='sayHello'";
//				db.execSQL(removeSayHelloMessage);
			}
		}
	}