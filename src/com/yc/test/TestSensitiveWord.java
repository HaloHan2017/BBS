package com.yc.test;

import java.util.Scanner;

import com.yc.utils.SensitiveWordsUtils;




public class TestSensitiveWord {
	public static void main(String[] args){
	    String s="SB,Sb,sB,sb  尼玛的，傻逼吧，我他妈的，sb，操你大爷的，fuck";
	    System.out.println(SensitiveWordsUtils.isContainsSensitiveWords(s));
	    
	    System.out.println("替换后的字符串为:\n"); 
	    System.out.println(SensitiveWordsUtils.filterSensitiveWords(s));
	    
	    
	}
}
