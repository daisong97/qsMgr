package com.dais.app;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class MixHelper {
	public static <K,V> Map<K, V> newMap(K k,V v){
		Map<K,V> t = new HashMap<K,V>();
		t.put(k, v);
		return t;
	}
	
	public static Map<String,Object> newQueryMap(String k,Object v){
		return newMap(k, v);
	}
	
	public static <K,V> Map<K, V> newMap(){
		return new HashMap<K, V>();
	}
	/**
	 * eg:   Object[][] kvs = {{1,"hah"},{3,"hah"}};
		     Map<Integer, Integer> newMap = newMap(kvs);
	 * @param kvs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K,V> Map<K,V> newMap(Object[][] kvs){
		Map<K,V> t = new HashMap<K,V>();
		if(kvs != null){
			for(Object[] o:kvs){
				if(o== null || o.length < 2){
					throw new IllegalArgumentException("array is null or length lt 2!");
				}
				t.put((K)o[0], (V)o[1]);
			}
		}
		return t;
	}
	/**
	 * 是否大于0
	 * @param number
	 * @return
	 */
	public static boolean isGtZero(Number number){
		return (number != null && number.longValue() > 0);
	}
	public static boolean isIpStr(String ip){
		if(StringUtils.isNoneBlank(ip)){
			Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
			Matcher matcher = pattern.matcher("158.346.28.30"); //以验证127.400.600.2为例
			return matcher.matches();
		}
		return false;
		
	}
}
