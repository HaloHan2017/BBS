package com.yc.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.bean.TblAdmin;
import com.yc.service.AdminService;
import com.yc.service.ServiceException;
import com.yc.utils.Encrypt;

@WebServlet("/admin.s")
public class AdminServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	private AdminService as=new AdminService();
	//管理员登录
	public void adminLogin(HttpServletRequest request,HttpServletResponse response) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, IOException, ServletException{
		//获取参数  封装成admin实体
		TblAdmin a = (TblAdmin) super.parseParameterToT(request, TblAdmin.class);
		//获取验证码   和  输入的验证码
		String input_vfcode = request.getParameter("vfcode");
		String vfcode = (String) request.getSession().getAttribute("vfcode");
		//调用ss的登录方法
		try {
			TblAdmin admin = as.adminLogin(a,input_vfcode,vfcode);
			request.getSession().setAttribute("adminUser", admin);
			//重定向到 /manage/manager.jsp
			response.sendRedirect(request.getContextPath()+"/manage/manager.jsp");
		} catch (ServiceException e) {	//登录失败
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/manageradmin.jsp").forward(request, response);
		}
	}
}
