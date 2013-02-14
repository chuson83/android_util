package com.chikuwastudio.photospotsearch.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

	private SharedPreferences pref;
	private Context context;

	/**
	 * コンストラクタ
	 */
	public Preference(String prefName, Context context) {
		this.context = context;
		pref = this.context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
	}

	/**
	 * 文字列書き込み
	 * @param key
	 * @param value
	 */
	public void writeStr(String key,String value){
		SharedPreferences.Editor edit = pref.edit();
		edit.putString(key, value);
		edit.commit();
	}

	/**
	 * ハッシュマップで来た時の書き込み
	 * @param map
	 */
	public void writeStr(HashMap<String, String> map){
		SharedPreferences.Editor edit = pref.edit();
		Set<String> keys = map.keySet();
	    List<String> keyLists = new ArrayList<String>();
	    for(String key : keys){
	      keyLists.add(key);
	    }
	    for(int i=0; i<keyLists.size(); i++){
	    	String key = keyLists.get(i);
	    	String value = map.get(keyLists.get(i));
	    	edit.putString(key, value);
	    }
	    edit.commit();
	}

	/**
	 * 文字列読み込み
	 * @param key
	 * @return
	 */
	public String readStr(String key){
		String value = pref.getString(key, "");
		return value;
	}

	/**
	 * プリファレンスを削除する
	 */
	public void clearPref(){
		SharedPreferences.Editor edit = pref.edit();
		edit.clear();
		edit.commit();
	}

}
