package com.ruyicai.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.ruyicai.util.PublicMethod;

import android.annotation.SuppressLint;


@SuppressLint("SimpleDateFormat")
public class MyDate {

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrNoSS(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateString = formatter.format(dateDate);
		return dateString;
	}
	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}
	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrSS(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	public static long getDatetimeIntervalUsingDay(Date date1, Date date2) {
		long interval = date1.getTime() - date2.getTime();
		return interval / (24 * 60 * 60 * 1000);
	}

	/***
	 * 得到时间 ：上午 11:34
	 * 
	 * @param date
	 * @return
	 */
	public static String GetHourExtraInfo(Date date) {
		String longDate = dateToStrNoSS(date);
		PublicMethod.outLog("MyDate", longDate);
		int hour = Integer.parseInt(longDate.substring(11, 13).trim());
		String hourAndMin = longDate.substring(11);
		if (hour >= 6 && hour < 11) {
			return "早上" + hourAndMin;
		} else if (hour >= 11 && hour < 13) {
			return "中午" + hourAndMin;
		} else if (hour >= 13 && hour < 18) {
			return "下午" + hourAndMin;
		} else if ((hour >= 18 && hour <= 23) || (hour >= 0 && hour < 6)) {
			return "晚上" + hourAndMin;
		} else {
			return null;
		}
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getIndexOfWeek(Date date) {
		String[] weeks = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week_index < 0) {
			week_index = 0;
		}
		return weeks[week_index];
	}

	/**
	 * 日期上加上年月日
	 * 
	 * @param date
	 * @return
	 */
	public static String AddYMDChina(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(date);
		String[] arrStr = strDate.split("-");
		return arrStr[0] + "年" + arrStr[1] + "月" + arrStr[2] + "日";
	}

	/**
	 * 日期上加上月日
	 * 
	 * @param date
	 * @return
	 */
	public static String AddMDChina(Date date) {
		String YMDEN = dateToStrLong(date);
		String[] arrStr = YMDEN.split("-");
		String[] arrStrDay = arrStr[2].split(" ");
		return arrStr[1] + "月" + arrStrDay[0] + "日" + arrStrDay[1];
	}

	public static String getStrDateWithoutHour(Date date) {
		if (date == null) {
			return "时间为空";
		}
		/** 当前时间 **/
		Date currentDate = new Date(System.currentTimeMillis());
		/** 两个时间差的天数 **/
		long ApartDays = getDatetimeIntervalUsingDay(currentDate, date);
		int isYeater=isYeaterday(date, currentDate);
		/** 得到消息的小时 包含上下午 **/
		String hourExtraInfo = GetHourExtraInfo(date);
		/** 当前时间 年 **/
		int yearCurrentDate = currentDate.getYear();
		/** 消息时间 年 **/
		int yearMsgDate = date.getYear();
		/** 周几 **/
		String indexOfWeek = getIndexOfWeek(date);
		if (ApartDays == 0) {// 今天
			if(isYeater==0){
				return "昨天"+hourExtraInfo;
			}
			return hourExtraInfo;
		} else if (ApartDays == 1) {// 昨天
			return ("昨天"+hourExtraInfo);
		} else if (ApartDays == 2) {// 前天
			return (indexOfWeek);
		} else if (yearCurrentDate == (yearMsgDate)) {// 今年
			return (AddMDChina(date));
		} else {// 今年之前
			return (AddYMDChina(date));
		}
	}
	/** 
     * @author LuoB. 
     * @param oldTime 较小的时间 
     * @param newTime 较大的时间 (如果为空   默认当前时间 ,表示和当前时间相比) 
     * @return -1 ：同一天.    0：昨天 .   1 ：至少是前天. 
     * @throws ParseException 转换异常 
     */  
	public static  int isYeaterday(Date oldTime,Date newTime){  
        if(newTime==null){  
            newTime=new Date();  
        }  
               //将下面的 理解成  yyyy-MM-dd 00：00：00 更好理解点  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        String todayStr = format.format(newTime);  
        Date today=null;
		try {
			today = format.parse(todayStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        //昨天 86400000=24*60*60*1000 一天  
        if((today.getTime()-oldTime.getTime())>0 && (today.getTime()-oldTime.getTime())<=86400000) {  
            return 0;  
        }  
        else if((today.getTime()-oldTime.getTime())<=0){ //至少是今天  
            return -1;  
        }  
        else{ //至少是前天  
            return 1;  
        }  
          
    } 
	public static String longToDate(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date(time);
		return sdf.format(date);
	}

	/**
	 * 显示我的动态列表中的时间
	 * 
	 * @param strDate
	 * @param current
	 * @return
	 */

	/**
	 * 1小时以内显示：1分钟前，28分钟前，59分钟前
	 * 
	 * 1小时以上，24小时以下显示：1小时前，11小时前，23小时前
	 * 
	 * 24小时（今天）以外，48小时（昨天）以内显示：昨天
	 * 
	 * 48小时以上显示：2天前，10天前，66天前
	 * 
	 * @param d
	 * @return
	 */
	public static String longToDateForMyDynamic(long strDate, long current) {
		String result = "";
		Date createDate = new Date(strDate);
		Date currentDate = new Date(current);
		java.text.SimpleDateFormat formatTime = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		java.text.SimpleDateFormat formatDay = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		String createTimeStr = formatTime.format(createDate);
		String currentTimeStr = formatTime.format(currentDate);

		String[] createTimeArray = createTimeStr.split(" ");
		String[] createHourAndMinuteArray = createTimeArray[1].split(":");
		String createHour = createHourAndMinuteArray[0];// 创建的小时
		String createMinute = createHourAndMinuteArray[1];// 创建的分钟

		String[] currentTimeArray = currentTimeStr.split(" ");
		String[] currentHourAndMinuteArray = currentTimeArray[1].split(":");
		String currentHour = currentHourAndMinuteArray[0];// 当前的小时
		String currentMinute = currentHourAndMinuteArray[1];// 当前的分钟

		String createTimeDayStr = formatDay.format(createDate);
		String currentTimeDayStr = formatDay.format(currentDate);

		String[] createDayArray = createTimeDayStr.split("-");
		String[] currentDayArray = currentTimeDayStr.split("-");
		if (!createDayArray[0].equals(currentDayArray[0])) {
			int year = (Integer.valueOf(currentDayArray[0]) - Integer
					.valueOf(createDayArray[0]));
			int month = Integer.valueOf(currentDayArray[1])
					- Integer.valueOf(createDayArray[1]);
			int gapMonth = year * 12 + month;
			if (gapMonth < 12) {
				if (gapMonth == 1) {
					int currentDay = Integer.valueOf(currentDayArray[2]);
					int createDay = Integer.valueOf(createDayArray[2]);
					int day = currentDay + 31 - createDay;
					result = day + "天前";
				} else {
					result = gapMonth + "个月前";
				}
			} else {
				result = gapMonth / 12 + "年前";
			}

		} else if (!createDayArray[1].equals(currentDayArray[1])) {
			int currentMonth = Integer.valueOf(currentDayArray[1]);
			int createMonth = Integer.valueOf(createDayArray[1]);

			if (currentMonth - createMonth == 1) {
				int d = Integer.valueOf(currentDayArray[2])
						+ getDayForMonth(Integer.valueOf(createDayArray[0]),
								createMonth)
						- Integer.valueOf(createDayArray[2]);
				result = d + "天前";
			} else {
				result = currentMonth - createMonth + "个月前";
			}

		} else if (!createDayArray[2].equals(currentDayArray[2])) {
			int currentDay = Integer.valueOf(currentDayArray[2]);
			int createDay = Integer.valueOf(createDayArray[2]);
			if (currentDay - createDay > 1) {
				result = currentDay - createDay + "天前";
			} else {// 相差一天
				if (Integer.valueOf(currentHour) - Integer.valueOf(createHour) > 0) {
					result = currentDay - createDay + "天前";
				} else if (Integer.valueOf(currentHour)
						- Integer.valueOf(createHour) + 24 >= 1) {
					result = Integer.valueOf(currentHour)
							- Integer.valueOf(createHour) + 24 + "小时前";
				} else if (Integer.valueOf(currentMinute)
						- Integer.valueOf(createMinute) + 24 >= 1) {
					result = Integer.valueOf(currentMinute)
							- Integer.valueOf(createMinute) + 24 + "分钟前";
				} else {
					result = "刚刚";
				}
			}
		} else {// 如果是同一天
			int gapMunite = (Integer.valueOf(currentHour) * 60 + Integer
					.valueOf(currentMinute))
					- ((Integer.valueOf(createHour) * 60 + Integer
							.valueOf(createMinute)));
			if (gapMunite == 0) {
				result = "刚刚";
			} else if (gapMunite < 60 && gapMunite > 0) {
				result = gapMunite + "分钟前";
			} else if (gapMunite >= 60) {
				result = gapMunite / 60 + "小时前";
			} else {
				result = "刚刚";
			}
		}
		return result;
	}

	public static int getDayForMonth(int year, int month) {
		int day;
		switch (month) {
		case 2:
			if (year % 400 == 0 || (year % 100 != 0) && (year % 4 == 0)) {
				day = 29;
			} else {
				day = 28;
			}
			break;
		case 4:
			day = 30;
			break;
		case 6:
			day = 30;
			break;
		case 7:
			day = 30;
			break;
		case 9:
			day = 30;
			break;
		case 11:
			day = 30;
			break;
		default:
			day = 31;
			break;

		}
		return day;
	}

	/**
	 * 
	 * 用户距离更新时间显示：（好友界面，附近的人，同服玩家）
	 */
	public static String nearByShowDate(String d) {
		Date date = dateFromLong(d);
		long differfem = differfen(date);
		long differxiaoshi = differxiaoshi(date);
		long differtian = differtian(date);
		String r = "";
		if (differfem == 0) {
			r = "1分钟前";
		} else {
			if (differxiaoshi == 0) {
				r = differfem + "分钟前";
			} else {
				if (differtian == 0) {
					r = differxiaoshi + "小时前";
				} else {
					r = differtian(date) + "天前";
				}
			}
		}
		return r;
	}

	/**
	 * 个人中心我的动态列表时间机制
	 * 
	 * @param d
	 * @return
	 */

	/**
	 * 1小时以内显示：1分钟前，28分钟前，59分钟前
	 * 
	 * 1小时以上，24小时以下显示：1小时前，11小时前，23小时前
	 * 
	 * 24小时（今天）以外，48小时（昨天）以内显示：昨天
	 * 
	 * 48小时以上显示：2天前，10天前，66天前
	 * 
	 * @param d
	 * @return
	 */
	public static String DynamicListShowDate(String d) {
		Date date = dateFromLong(d);
		long differfem = differfen(date);
		long differxiaoshi = differxiaoshi(date);
		long differtian = differtian(date);
		String r = "";
		if (differtian >= 2) {
			r = differtian + "天前";
		} else if (differtian < 2 && differtian > 1) {
			r = "昨天";
		} else if (differtian < 1 && differxiaoshi >= 1) {
			r = differxiaoshi + "小时前";
		} else if (differtian < 1 && differxiaoshi < 1 && differfem > 1) {
			r = differfem + "分钟前";
		} else if (differtian < 1 && differxiaoshi < 1 && differfem < 1) {
			r = "刚刚";
		}
		return r;
	}

	public static String showDate(String d) {
		Date date = dateFromLong(d);
		long differfem = differfen(date);
		long differxiaoshi = differxiaoshi(date);
		long differtian = differtian(date);
		long differyue = differyue(date);
		long differnian = differnian(date);
		String r = "";
		if (differfem == 0) {
			r = "1分钟前";
		} else {
			if (differxiaoshi == 0) {
				r = differfem + "分钟前";
			} else {
				if (differtian == 0) {
					r = differxiaoshi + "小时前";
				} else {
					if (differyue == 0) {
						r = differtian + "天前";
					} else {
						if (differnian == 0) {
							r = differyue + "个月前";
						} else {
							r = differnian + "年月前";
						}
					}
				}
			}
		}
		return r;
	}


	/**
	 * 相差的秒数 时间
	 * 
	 * @param date
	 * @return
	 */
	public static long differmiao(Date date) {
		Date currentDate = new Date(System.currentTimeMillis());
		return (currentDate.getTime() - date.getTime()) / 1000;
	}

	/**
	 * 相差的分钟数
	 * 
	 * @param date
	 * @return
	 */
	public static long differfen(Date date) {
		return differmiao(date) / 60;
	}

	/**
	 * 相差的小时数
	 * 
	 * @param date
	 * @return
	 */
	public static long differxiaoshi(Date date) {
		return differfen(date) / 60;
	}

	/**
	 * 相差的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long differtian(Date date) {
		return differxiaoshi(date) / 24;
	}

	/**
	 * 相差的月数
	 * 
	 * @param date
	 * @return
	 */
	public static long differyue(Date date) {
		return differtian(date) / 30;
	}

	/**
	 * 相差的年数
	 * 
	 * @param date
	 * @return
	 */
	public static long differnian(Date date) {
		return differyue(date) / 12;
	}
	/**
	 * 将毫秒的Date转为秒的Date
	 * @param date
	 * @return
	 */
	public static Date getDateFromLong(String time){
		if(time==null||"".equals(time)){
			return new Date();
		}
		long times=Long.valueOf(time);
		GregorianCalendar gc = new GregorianCalendar(); 
		gc.setTimeInMillis(times);
		return  gc.getTime();
	}
	
	public static Date getDate(Date date){
		 GregorianCalendar gc = new GregorianCalendar(); 
        gc.setTimeInMillis(date.getTime()/1000);
        return  gc.getTime();
	}
	public static Date getDate(long time){
		GregorianCalendar gc = new GregorianCalendar(); 
		gc.setTimeInMillis(time*1000);
		return  gc.getTime();
	}
	public static Date dateFromLong(String strDate) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Long time = Long.parseLong(strDate);
			String d = format.format(time);
			return format.parse(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String format(long ms) {// 将毫秒数换算成x天x时x分x秒x毫秒
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;
		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second* ss;
		
		if(day==0){
			if(hour==0){
				if(minute==0){
					if(second==0){
						if(milliSecond==0){
							return "0毫秒";
						}else {
							return milliSecond+"毫秒";
						}
					}else {
						return second+"秒";
					}
				}else {
					return minute+"分钟";
				}
			}else {
				return hour+"小时";
			}
		}else {
			return day+"天";
		}
	}
	
	public static boolean isNight(String date, String target) {
		try {
	        if (date == null || "".equals(date) || date.length() == 0) {
	            return false;
	        }
	        if (target == null || "".equals(target) || target.length() == 0){
	        	return false;
	        }
	        String[] strs = target.split("-");
	        String[] strStart = strs[0].split(",");
			if("0".equals(strStart[0])){
				return false;
			}
			String[] endStart = strs[1].split(",");
			
	        Calendar cal = Calendar.getInstance();
        
            cal.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(date));
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            if ((hour >= Integer.parseInt(strStart[1]) && minute >= Integer.parseInt(strStart[2])) && (hour <= Integer.parseInt(endStart[0]) && minute <= Integer.parseInt(endStart[1]))) { // 是否是晚上时间
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
	 }
}
