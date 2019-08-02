package com.yc.utils;

import java.util.List;
import java.util.Map;

import com.yc.bean.TblBoard;

public class TestDBUtil {
	
	public static void main(String[] args){
		String f="abc.txt";
		String newFileName = MyUtils.getNewFileName(f);
		System.out.println(newFileName);
	}
}
