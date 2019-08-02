package com.yc.service;

import com.yc.bean.TblAdmin;
import com.yc.dao.AdminDao;
import com.yc.utils.Encrypt;

public class AdminService {
	private AdminDao adao=new AdminDao();
	//管理员登录方法
	public TblAdmin adminLogin(TblAdmin a,String input_vfcode, String vfcode) throws ServiceException {
		//判断验证码
		if(input_vfcode==null || "".equals(input_vfcode.trim())){
			throw new ServiceException("验证码不能为空！");
		}
		if(!input_vfcode.equals(vfcode)){
			throw new ServiceException("验证码错误！");
		}
		
		if(a.getAname()==null || "".equals(a.getAname().trim())){
			throw new ServiceException("用户名不能为空！");
		}
		if(a.getApass()==null || "".equals(a.getApass().trim())){
			throw new ServiceException("密码不能为空！");
		}
		//密码进行md5加密
		String md5 = Encrypt.md5(a.getApass());
		a.setApass(md5);
		//判断是否登录成功
		TblAdmin admin=adao.adminLogin(a);
		if(admin==null){
			throw new ServiceException("密码错误！");
		}
		return admin;
	}

}
