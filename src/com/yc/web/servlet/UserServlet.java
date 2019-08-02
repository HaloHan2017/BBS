package com.yc.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.yc.bean.LikeModel;
import com.yc.bean.TblTopic;
import com.yc.bean.TblUser;
import com.yc.service.ServiceException;
import com.yc.service.UserService;
import com.yc.utils.DBUtil;
import com.yc.utils.MailUtils;
import com.yc.utils.MyUtils;

/**
 * 用户servlet
 */
@WebServlet("/user.s")
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private UserService us=new UserService();
      
	//登录方法
	public void login(HttpServletRequest request,HttpServletResponse response) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, IOException, ServletException{
		//1.从login.jsp获取表单输入的参数，封装成TblUser实体类
		TblUser u=(TblUser) super.parseParameterToT(request, TblUser.class);
		//获取输入的验证码
		String input_vfcode = request.getParameter("vfcode");
		//获取产生的正确验证码
		String vfcode = (String) request.getSession().getAttribute("vfcode");
		
		//2.调用userService层对象，去数据库里查该用户是否存在
		TblUser user;
		try {
			//登录成功
			user = us.login(u,input_vfcode,vfcode);
			
			//将user存到session里，再重定向到index.s
			request.getSession().setAttribute("user", user);
			
			//判断是否有回调的路径（）
			String callback_path = (String) request.getSession().getAttribute("callback-path");
			if(callback_path!=null){
				Map<String,String[]> mapq = (Map<String, String[]>) request.getSession().getAttribute("callback-map");
				callback_path+="?";
				//拼接地址
				for(Map.Entry<String,String[]> entry:mapq.entrySet()){
					String name=entry.getKey();
					String value = entry.getValue()[0];
					callback_path+=name+"="+value+"&";
				}
				//重定向到回调页面
				response.sendRedirect(request.getContextPath()+callback_path);
			}
			response.sendRedirect(request.getContextPath()+"/index.s");
		} catch (ServiceException e) {
			//登录失败
			e.printStackTrace();
			//转发到login.jsp,并设置登录错误信息
			request.setAttribute("msg",e.getMessage());
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
	
	
	//注册方法
	public void reg(HttpServletRequest request,HttpServletResponse response) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, ServletException, IOException{
		//调用MyUtil，过滤掉上传文件，将其复制到服务器指令目录
		String path = request.getServletContext().getInitParameter("path");
		String virtualPath = request.getServletContext().getInitParameter("virtualPath");
		Map<String, Object> map = MyUtils.upload(request, "myhead", path,virtualPath);
		//将map里的参数封装成TblUser
		TblUser u =new TblUser();
		u.setEmail((String) map.get("email"));
		u.setGender(Integer.parseInt((String)map.get("gender")));
		u.setPhone((String) map.get("phone"));
		u.setUname((String) map.get("uname"));
		u.setUpass((String) map.get("upass"));
		u.setLid(1);//默认等级为lv1
		u.setSwcount(0);//默认未违规发言
		String upass1 = (String) map.get("upass1");
		//获取头像的地址
		String myhead=(String) map.get("myhead");
		//用户选择个人头像
		if(myhead!=null){
			u.setHead(myhead);
		}else{
			//使用系统默认自带头像
			u.setHead((String) map.get("head"));
		}
		System.out.println(u.toString());
		//获取输入的验证码
		String input_vfcode = (String) map.get("vfcode");
		//获取产生的正确验证码
		String vfcode = (String) request.getSession().getAttribute("vfcode");
		//调用service层的注册方法，捕获异常
		try {
			
			us.reg(u,upass1,input_vfcode,vfcode);
			//重定向到login.jsp
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		} catch (ServiceException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/reg.jsp").forward(request, response);
		}
		
	}
	
	//退出登录方法
	public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HttpSession session=request.getSession();
		//从session里移除user（当前登录的用户）
		session.removeAttribute("user");
		//使session失效
		session.invalidate();
		//重定向到index.s
		response.sendRedirect(request.getContextPath()+"/index.s");
	}
	
	//ajax查询所有用户
	public void queryAllUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<TblUser> userList=us.queryAllUser();
		String json = JSON.toJSONString(userList);
		response.getWriter().append(json);
	}
	//ajax查询所有 未被禁言的用户列表
	public void queryUserListByUnBan(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<TblUser> userListUnBan=us.queryUserListByUnBan();
	
		String json = JSON.toJSONString(userListUnBan);
		response.getWriter().println(json);
	}
	
	
	//修改密码
	public void modifyPwd(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		//获得验证码  邮箱  新密码
		String ecode = request.getParameter("ecode");
		String email = request.getParameter("email");
		String newpass = request.getParameter("newpass");
		//获得session中的验证码
		String emailcode = (String) request.getSession().getAttribute("emailcode");
		
		//调用service层的修改密码方法
		try {
			us.modifyPwd(emailcode,ecode,email,newpass);
			//修改成功
			String str="{'code':'1','msg':'修改成功'}";
			MyUtils.outJsonString(response, str);
		} catch (Exception e) {
			e.printStackTrace();
			String str="{'code':'0','msg':'"+e.getMessage()+"'}";
			MyUtils.outJsonString(response, str);
		}
	}
	//忘记密码时，向用户邮箱发送验证码
	public void sendVCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String email = request.getParameter("email");
		//设置验证码
		String emailCode = MyUtils.createEmailCode(request.getSession());
		String message="bbs系统提醒您：您的验证码是  "+emailCode+" ，该验证码2分钟内有效！";
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		//发送邮件
		try {
			MailUtils.sendMail("找回密码",email, message);
			//发送成功
			out.println("{\"res\":1}");
			out.flush();
		} catch (MessagingException e) {
			e.printStackTrace();
			//发送失败
			out.println("{\"res\":0}");
			out.flush();
		}
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void alter(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		TblUser user = (TblUser)request.getSession().getAttribute("user");
		String upass = request.getParameter("upass");  //原密码
		String upass1 = request.getParameter("upass1");  //新密码
		String upass2 = request.getParameter("upass2");  //确认密码
		try {
			us.alter(user,upass,upass1,upass2);	//修改密码方法
			request.getSession().removeAttribute("user");  //把已经登录的用户清除掉
			request.setAttribute("msg", "密码修改成功，请重新登录！");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} catch (ServiceException e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/alter.jsp").forward(request, response);
		}
	}
	
	/**
	 * 跳转到修改密码页面
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void goAlter(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		//判断当前是否有用户登录
		TblUser user = (TblUser)request.getSession().getAttribute("user");
		if(user == null){
			request.setAttribute("msg", "用户名或密码错误！");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		response.sendRedirect(request.getContextPath()+"/alter.jsp");
	}
	
	/*
	 * 跳转到个人信息界面(能看到个人信息界面，则说明已登录)
	 * */
	public void goUser(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		TblUser user = (TblUser)request.getSession().getAttribute("user");
		//根据id查用户信息
		List<Map<String,Object>> userInfo=us.getUserInfoById(user.getUid());
		
		//查询该用户收藏的所有帖子
		List<Map<String, Object>> likeTopics= us.getLikeTopicsByUid(user.getUid());
		
		request.setAttribute("likeTopics",likeTopics);
		//存到request，转发到user.jsp
		request.setAttribute("userInfo", userInfo.get(0));//包含  主题数  回帖数
		request.getRequestDispatcher("/user.jsp").forward(request, response);
	}
	/*
	 * 禁言用户  
	 * */ 
	public void banUser(HttpServletRequest request,HttpServletResponse response) throws ServiceException, IOException{
		int uid = Integer.parseInt(request.getParameter("uid"));
		PrintWriter out = MyUtils.getPrintWriter(response);
		//判断该用户是否 已被禁言
		boolean flag=us.getUserBanState(uid);
		if(flag){
			//被禁言了，不能再禁言
			out.write("{'code':-1,'msg':'操作有误!该用户已被禁言！'}");
			out.flush();
			return;
		}
		//调用service的禁言方法
		String res="";
		try {
			us.banUser(uid);
			//禁言成功
			out.print("{'code':1,'msg':'禁言成功！'}");
		} catch (Exception e) {
			//禁言失败
			e.printStackTrace();
			out.print("{'code':0,'msg':'"+e.getMessage()+"'}");
			
		} 
	}
	

	/*
	 * 解除用户禁言
	 * */
	public void UnbanUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int uid = Integer.parseInt(request.getParameter("uid"));
		PrintWriter out = MyUtils.getPrintWriter(response);
		//判断该用户是否 已被解除禁言
		boolean flag=us.getUserBanState(uid);
		if(!flag){
			//已经解除禁言了，不能再操作
			out.print("{'code':-1,'msg':'操作有误!该用户已被解除禁言！'}");
			return;
		}
		//调用service的解除禁言方法
		String res="";
		try {
			us.UnBanUser(uid);
			//解除禁言成功
			res="success";
			out.print("{'code':1,'msg':'解除禁言成功！'}");
		} catch (Exception e) {
			//解除禁言失败
			e.printStackTrace();
			out.print("{'code':0,'msg':'"+e.getMessage()+"'}");
			
		}
	}
	
	/*
	 * 查询该用户的违规发言次数  是否 >=3）
	 * */
	public void queryUserSWCount(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String uid_str = request.getParameter("uid");
		if("".equals(uid_str) || uid_str==null){
			//没有用户进行登录
			MyUtils.outJsonString(response, "{'code':'-1','msg':'您还未登录！'}");
			return;
		}
		int uid = Integer.parseInt(uid_str);
		int ctn=us.queryUserSWCount(uid);
		//判断ctn是否大于3
		String res="";
		if(ctn>=3){
			//给用户邮箱发送  禁言通知邮件
		
			TblUser user = (TblUser) request.getSession().getAttribute("user");
			user.setSwcount(ctn);//将swcount设置到session中的user
			if(user.getSendEmial()==0){//未发送邮件
				//发送邮件
				String message="bbs系统提醒您：您由于近一个月违规发言次数达到3次，已被系统自动禁言，下月一号自动解禁，请注意您的行为举止，\n做一个文明网友！"+
				"  若有需要请联系本站管理员 QQ：1006653740；";
				try {
					MailUtils.sendMail("禁言通知",user.getEmail(), message);
					user.setSendEmial(1);//标志已发送，
					//将该user设置后的属性存到session
					request.getSession().setAttribute("user", user);
					
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
			res="{'code':'0','msg':'对不起，您已被禁言！'}";
		}else{
			res="{'code':'1'}";
		}
		//发送结果到前台
		MyUtils.outJsonString(response, res);
		
	}
}
