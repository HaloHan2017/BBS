package com.yc.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.bean.TblUser;
import com.yc.dao.UserDao;
import com.yc.service.TopicService;
import com.yc.utils.MailUtils;

/**
 * 违规发言次数》=3  自动禁言
 */
//@WebFilter(urlPatterns={"/post.jsp","/postreply.jsp"})
@WebFilter(urlPatterns={"/a.jsp"})
public class CPostFIlter implements Filter {

    public CPostFIlter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		//获得session对象，判断当前登录用户能不能进行发言
		HttpServletRequest request=(HttpServletRequest) req;
		HttpServletResponse response=(HttpServletResponse) resp;
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		
		HttpSession session = request.getSession();
		TblUser u = (TblUser) session.getAttribute("user");
		//要去数据库中查询，session里的为实时更新
		UserDao udao=new UserDao();
		List<TblUser> findUserById = udao.findUserById(u.getUid());
		TblUser user = findUserById.get(0);
		
		
		//获取user对象的属性值，用于标记只发送一次邮件
		//如果 违规发言次数》=3  提示已被禁言
		if(user.getSwcount()>=3){
			
			if(user.getSendEmial()==0){//未发送邮件
				//发送邮件
				String message="bbs系统提醒您：您由于近一个月违规发言次数达到3次，已被系统自动禁言，下月一号自动解禁，请注意您的行为举止，\n做一个文明网友！"+
				"  若有需要请联系本站管理员 QQ：1006653740；";
				try {
					MailUtils.sendMail("禁言通知",user.getEmail(), message);
					user.setSendEmial(1);//标志已发送，
					
					out.println("<script>alert('对不起，您已被禁言！');</script>");
					out.flush();
					
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
			
			//只弹出窗口提示，而不再发送邮件
			out.println("<script>alert('对不起，您已被禁言！');</script>");
			out.flush();
			
		}else{
			//未被禁言  放行
			chain.doFilter(request, response);
		}
			
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
