package com.ruyicai.data.db;

import java.util.HashMap;
import java.util.Map;

import com.palmdream.RuyicaiAndroid.R;



public class GyjMap {
	private static Map<String, Integer> worldCup = new HashMap<String, Integer>();
	private static Map<String, Integer> europeLeague = new HashMap<String, Integer>();
	static {
		/**
		 * 世界杯冠军
		 */
		worldCup.put("巴西", R.drawable.buy_zq_gyj_baxi);
		worldCup.put("德国", R.drawable.buy_zq_gyj_deguo);
		worldCup.put("阿根廷", R.drawable.buy_zq_gyj_agenting);
		worldCup.put("西班牙", R.drawable.buy_zq_gyj_xibanya);
		
		worldCup.put("比利时", R.drawable.buy_zq_gyj_bilishi);
		worldCup.put("荷兰", R.drawable.buy_zq_gyj_helan);
		worldCup.put("意大利", R.drawable.buy_zq_gyj_yidali);
		worldCup.put("法国", R.drawable.buy_zq_gyj_faguo);
		
		worldCup.put("葡萄牙", R.drawable.buy_zq_gyj_putouya);
		worldCup.put("哥伦比亚", R.drawable.buy_zq_gyj_gelunbiya);
		worldCup.put("乌拉圭", R.drawable.buy_zq_gyj_wulagui);
		worldCup.put("英格兰", R.drawable.buy_zq_gyj_yinggelan);
		
		worldCup.put("智利", R.drawable.buy_zq_gyj_zhili);
		worldCup.put("俄罗斯", R.drawable.buy_zq_gyj_eluosi);
		worldCup.put("科特迪瓦", R.drawable.buy_zq_gyj_ketediwa);
		worldCup.put("瑞士", R.drawable.buy_zq_gyj_ruishi);
		
		worldCup.put("日本", R.drawable.buy_zq_gyj_riben);
		worldCup.put("波黑", R.drawable.buy_zq_gyj_bohei);
		worldCup.put("克罗地亚", R.drawable.buy_zq_gyj_keluodiya);
		worldCup.put("厄瓜多尔", R.drawable.buy_zq_gyj_eguoduoer);
		
		worldCup.put("墨西哥", R.drawable.buy_zq_gyj_moxige);
		worldCup.put("美国", R.drawable.buy_zq_gyj_meiguo);
		worldCup.put("加纳", R.drawable.buy_zq_gyj_jiana);
		worldCup.put("尼日利亚", R.drawable.buy_zq_gyj_niriliya);
		
		worldCup.put("希腊", R.drawable.buy_zq_gyj_xila);
		worldCup.put("韩国", R.drawable.buy_zq_gyj_hanguo);
		worldCup.put("喀麦隆", R.drawable.buy_zq_gyj_kamailong);
		worldCup.put("澳大利亚", R.drawable.buy_zq_gyj_aodaliya);
		
		worldCup.put("哥斯达黎加", R.drawable.buy_zq_gyj_gesidalijia);
		worldCup.put("洪都拉斯", R.drawable.buy_zq_gyj_hongdulasi);
		worldCup.put("伊朗", R.drawable.buy_zq_gyj_yilang);
		worldCup.put("阿尔及利亚", R.drawable.buy_zq_gyj_aerjiliya);
		
		/**
		 * 欧冠冠军
		 */
		europeLeague.put("拜仁慕尼黑", R.drawable.buy_zq_gyj_bairenmonihei);
		europeLeague.put("皇家马德里", R.drawable.buy_zq_gyj_huangma);
		europeLeague.put("巴塞罗那", R.drawable.buy_zq_gyj_basailuona);
		europeLeague.put("多特蒙德", R.drawable.buy_zq_gyj_duotemengde);
		
		europeLeague.put("切尔西", R.drawable.buy_zq_gyj_qierxi);
		europeLeague.put("巴黎圣日尔曼", R.drawable.buy_zq_gyj_balishengrierman);
		europeLeague.put("马德里竞技", R.drawable.buy_zq_gyj_madelijingji);
		europeLeague.put("曼彻斯特联", R.drawable.buy_zq_gyj_manlian);
		
		europeLeague.put("曼彻斯特城", R.drawable.buy_zq_gyj_manshesitecheng);
		europeLeague.put("奥林匹亚科斯", R.drawable.buy_zq_gyj_aolinpiyakesi);
		europeLeague.put("阿森纳", R.drawable.buy_zq_gyj_asinna);
		europeLeague.put("AC米兰", R.drawable.buy_zq_gyj_acmilan);
		
		europeLeague.put("圣彼得堡泽尼特", R.drawable.buy_zq_gyj_shengbidebaozenite);
		europeLeague.put("加拉塔萨雷", R.drawable.buy_zq_gyj_jialatasalei);
		europeLeague.put("勒沃库森", R.drawable.buy_zq_gyj_lewokusen);
		europeLeague.put("沙尔克04", R.drawable.buy_zq_gyj_shaerke04);
		
	}
	
	public static Map<String, Integer> getWorldCupMap() {
		return worldCup;
	}
	
	public static Map<String, Integer> getEuropeLeagueMap() {
		return europeLeague;
	}
}
