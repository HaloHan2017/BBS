package com.yc.service;

import java.util.List;
import java.util.Map;

import com.yc.bean.TblTopic;
import com.yc.bean.TblUser;
import com.yc.dao.UserDao;
import com.yc.utils.MyUtils;

public class UserService {
	private UserDao udao=new UserDao();
	
	//用户登录方法
	public TblUser login(TblUser u, String input_vfcode, String vfcode) throws ServiceException {
		//判断验证码是否正确
		if("".equals(input_vfcode.trim()) || !vfcode.equalsIgnoreCase(input_vfcode)){
			throw new ServiceException("验证码错误！");
		}
		//判断用户名或密码不能为空
		if("".equals(u.getUname().trim()) || "".equals(u.getUpass().trim())){
			throw new ServiceException("用户名或密码不能为空！");
		}
		TblUser user = udao.login(u);
		if(user==null){
			throw new ServiceException("用户名或密码错误！");
		}
		
		return user;
	}
	//用户注册方法
	public void reg(TblUser u, String upass1, String input_vfcode, String vfcode) throws ServiceException {
		//判断验证码是否正确
		if("".equals(input_vfcode.trim()) || !vfcode.equalsIgnoreCase(input_vfcode)){
			throw new ServiceException("验证码错误！");
		}
		//判断用户名或密码不能为空
		if("".equals(u.getUname().trim()) || "".equals(u.getUpass().trim())){
			throw new ServiceException("用户名或密码不能为空！");
		}
		//两次密码不一致，抛出业务异常
		if(!upass1.equals(u.getUpass())){
			throw new ServiceException("两次密码不一致！");
		}
		if(u.getEmail()==null || "".equals(u.getEmail().trim())){
			throw new ServiceException("邮箱不能为空！");
		}
		if(u.getPhone()==null || "".equals(u.getPhone().trim())){
			throw new ServiceException("手机号不能为空！");
		}
		udao.reg(u);
	}
	//查询所有用户
	public List<TblUser> queryAllUser() {
		return udao.queryAllUser();
	}
	//修改密码
	public void modifyPwd( String emailcode,String ecode, String email, String newpass) throws ServiceException {
		if("".equals(emailcode) || emailcode==null){
			throw new ServiceException("验证码已过期，请重新发送！");
		}
		if(!emailcode.equals(ecode.trim()) || ecode==null || "".equals(ecode.trim())){
			throw new ServiceException("验证码错误！");
		}
		if("".equals(email.trim()) || email==null){
			throw new ServiceException("邮箱不能为空！");
		}
		if("".equals(newpass.trim()) || newpass==null){
			throw new ServiceException("密码不能为空！");
		}
		udao.modifyPwd(email,newpass);
	}
	//查询所有 未被禁言的用户列表
	public List<TblUser> queryUserListByUnBan() {
		return udao.queryUserListByUnBan();
	}
	
	/**
	 * 修改密码
	 * @param user
	 * @param upass
	 * @param upass1
	 * @param upass2
	 */
	public void alter(TblUser user, String upass, String upass1, String upass2)throws  ServiceException{
		//判断输入是否为null
		if(upass == null || upass.isEmpty()){
			throw new ServiceException("请输入旧密码！");
		}
		if(upass1 == null || upass1.isEmpty()){
			throw new ServiceException("请输入新密码！");
		}
		if(upass2 == null || upass2.isEmpty()){
			throw new ServiceException("请输入确认密码！");
		}
		if(!upass1.equals(upass2)){
			throw new ServiceException("新密码与确认密码不一致！");
		}
		
		String oldPass = user.getUpass();    //现在登录的对象的密码
		//将旧密码upass加密  然后和oldPass进行比较
		upass = MyUtils.parseMD5(upass);     //得到的是加密后的密码92eb5ffee6ae2fec3ad71c777531578f
		if(!oldPass.equals(upass)){
			throw new ServiceException("原密码输入错误！");
		}
		
		upass1 = MyUtils.parseMD5(upass1); //加密
		
		udao.alter(user.getUid(),upass1);
	}
	//根据id查用户信息
	public List<Map<String, Object>> getUserInfoById(Integer uid) {
		return udao.getUserInfoById(uid);
	}
	//禁言
	public void banUser(int uid) throws ServiceException {
		int res=udao.banUser(uid);
		if(res<=0){
			throw new ServiceException("禁言失败！");
		}
	}
	//解除禁言
	public void UnBanUser(int uid) throws ServiceException {
		int res=udao.UnBanUser(uid);
		if(res<=0){
			throw new ServiceException("禁言失败！");
		}
	}
	//判断该用户是否 已被禁言
	public boolean getUserBanState(int uid) {
		return udao.getUserBanState(uid);
	}
	//查询该用户收藏的所有帖子
	public List<Map<String, Object>> getLikeTopicsByUid(Integer uid) {
		return udao.getLikeTopicsByUid(uid);
	}
	//查询该用户的违规发言次数  是否 >=3）
	public int queryUserSWCount(int uid) {
		int ctn=udao.queryUserSWCount(uid);
		return ctn;
	}
	//
	public List<TblUser> findUserById(int uid) {
		return udao.findUserById(uid);
	}

}
