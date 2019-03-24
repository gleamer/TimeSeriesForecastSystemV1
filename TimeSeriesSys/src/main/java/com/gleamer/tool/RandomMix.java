package com.gleamer.tool;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomMix {



	/**
	 * 获取随机字母数字组合
	 * @param length 字符串长度
	 * @return 随机字母数字组String
	 */
	public static String getRandomCharAndNumr(Integer length) {  
	    String str = "";  
	    Random random = new Random();  
	    for (int i = 0; i < length; i++) {  
	        boolean b1 = random.nextBoolean();  //返回下一个伪随机数，它是取自此随机数生成器序列的均匀分布的boolean值。
	        boolean b2 = random.nextBoolean();
	        boolean b3 = random.nextBoolean();
	        if (b1) { // true  字符串  
	            // int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母  
	            str += (char) (65 + random.nextInt(26));// 取得大写字母  //返回下一个伪随机数，它是此随机数生成器的序列中均匀分布的 int 值。0-25
	        }
	        else if(b2){
	            	str +=(char)(97+random.nextInt(26));
	            }
	        else if(b3){
	            	str += String.valueOf(random.nextInt(10)); //0-9
	            }
	        else { //false   数字  
	            str += String.valueOf(random.nextInt(10));  
	        }  
	    }  
	    return str;  
	}  
	  
	/** 
	 * 验证随机字母数字组合是否纯数字与纯字母 
	 *  
	 * @param str 
	 * @return true 是 ， false 否 
	 */  
	public static boolean isRandomUsable(String str) {  
	    // String regExp =  
	    // "^[A-Za-z]+(([0-9]+[A-Za-z0-9]+)|([A-Za-z0-9]+[0-9]+))|[0-9]+(([A-Za-z]+[A-Za-z0-9]+)|([A-Za-z0-9]+[A-Za-z]+))$";  
	    String regExp = "^([0-9]+)|([A-Za-z]+)$";  
	    Pattern pat = Pattern.compile(regExp);  
	    Matcher mat = pat.matcher(str);  
	    return mat.matches();  
	}  
}
