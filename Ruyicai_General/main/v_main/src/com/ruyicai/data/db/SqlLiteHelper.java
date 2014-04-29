package com.ruyicai.data.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SqlLiteHelper extends SQLiteOpenHelper {
		private final DbHelper dbHelper;
		
		private static final int DB_VERSION = 6;
		
		public SqlLiteHelper(DbHelper dbHelper, Context context,String userid) {
			super(context, "ruyicai"+userid, null, DB_VERSION);
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

//			
//
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
//			
//			/**
//			 * 消息列表 最后条数据表
//			 */
//			String sqlLastMsgList = "CREATE TABLE   "
//					+ this.dbHelper.context.getString(R.string.lastMsgListTable) + "("
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
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgCount)
//					+ " integer default 0,"
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleTag)
//					+ " nvarchar(50) )";
//			
//			/**
//			 * 与我相关表
//			 */
//			String sqlWithMeDynMsgList = "CREATE TABLE   "
//					+ this.dbHelper.context.getString(R.string.withMeMsgListTable) + "("
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
//					+ " nvarchar(50) ,"
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgCount)
//					+ " integer default 0,"
//					+ this.dbHelper.context.getString(R.string.MsgInfoTitleTag)
//					+ " nvarchar(50) )";
//			
//
//			
//			db.execSQL(sqlMsg);
//
//			//db.execSQL(sqlLastMsgList);
//			//db.execSQL(sqlWithMeDynMsgList);
//		}
//
//		@Override
//		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//			if(oldVersion==1){
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
//			}
//			if(oldVersion<3){
//				String removeSayHelloMessage = "delete from "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTableName)
//						+ " where MsgType='sayHello'";
//				db.execSQL(removeSayHelloMessage);
//			}
//			if(oldVersion < 5){
//				/**
//				 * 消息列表 最后条数据表
//				 */
//				String sqlLastMsgList = "CREATE TABLE   "
//						+ this.dbHelper.context.getString(R.string.lastMsgListTable) + "("
//						+ "mainId" + " integer  primary key autoincrement,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitlePacketId)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleUserName)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleNickName)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgContent)
//						+ " nvarchar(500) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleImg)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleIsRead)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleDate) 
//						+ " Date ,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMyUserName)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleIsLeft)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgType)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleContentID)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleContentType)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleContent)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitlePicID)
//						+ " nvarchar(50) ,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgTitle)
//						+ " nvarchar(50) ,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgPayLoad)
//						+ " nvarchar(50) ,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgWithWho)
//						+ " nvarchar(50) ,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgStatus)
//						+ " integer ,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgReceiveTime)
//						+ " nvarchar(50) ,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgTag)
//						+ " nvarchar(50) ,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgCount)
//						+ " integer default 0,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleTag)
//						+ " nvarchar(50) )";
//				db.execSQL(sqlLastMsgList);
//				
//				dbHelper.moveToNewTable(db);
//			}
//			if(oldVersion < 6){
//				/**
//				 * 与我相关表
//				 */
//				String sqlWithMeDynMsgList = "CREATE TABLE   "
//						+ this.dbHelper.context.getString(R.string.withMeMsgListTable) + "("
//						+ "mainId" + " integer  primary key autoincrement,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitlePacketId)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleUserName)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleNickName)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgContent)
//						+ " nvarchar(500) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleImg)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleIsRead)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleDate) 
//						+ " Date ,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMyUserName)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleIsLeft)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgType)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleContentID)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleContentType)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleContent)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitlePicID)
//						+ " nvarchar(50) ,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgTitle)
//						+ " nvarchar(50) ,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgPayLoad)
//						+ " nvarchar(50) ,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgWithWho)
//						+ " nvarchar(50) ,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgStatus)
//						+ " integer ,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgReceiveTime)
//						+ " nvarchar(50) ,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgTag)
//						+ " nvarchar(50) ,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleMsgCount)
//						+ " integer default 0,"
//						+ this.dbHelper.context.getString(R.string.MsgInfoTitleTag)
//						+ " nvarchar(50) )";
//				
//				/**
//				 * 离线评论和赞表
//				 */
//				String sqlOffLinePLAndZan = "CREATE TABLE   "
//						+ this.dbHelper.context.getString(R.string.PingLunTable) 
//					    +" ( " + this.dbHelper.context.getString(R.string.id) + " integer  primary key autoincrement,"
//						+ this.dbHelper.context.getString(R.string.plmessageid)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.plmsg)
//						+ " nvarchar(500) , "
//						+ this.dbHelper.context.getString(R.string.destUserid)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.destCommentId)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.userId)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.pltype)
//						+ " nvarchar(50) , "
//						+ this.dbHelper.context.getString(R.string.status)
//						+ " nvarchar(50) )";
//				db.execSQL(sqlWithMeDynMsgList);
//				db.execSQL(sqlOffLinePLAndZan);
//			}
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
	}