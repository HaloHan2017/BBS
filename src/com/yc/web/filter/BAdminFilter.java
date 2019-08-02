package com.yc.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

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

/**
 * 管理员权限过滤器，非管理员不能进入后台管理系统
 */
@WebFilter("/manage/*")
public class BAdminFilter implements Filter {
	//manage
    public BAdminFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpSession session=req.getSession();
		HttpServletResponse resp=(HttpServletResponse) response;
		//判断当前session是否有  admin用户
		if(session.getAttribute("adminUser")!=null){
			//如果已经登录则跳转后台页面
			chain.doFilter(req, resp);
		}else{
			//未登录则跳转到管理员登录页面
//			resp.setContentType("text/html;charset=utf-8");
//			PrintWriter out=resp.getWriter();
//			String msg="$.messager.alert({ title:'消息', msg:'您尚未登录！', icon:'info' });";
//			out.print("<script>alert('您尚未登录！');location.href='manageradmin.jsp'</script>");
//			out.flush();
			resp.sendRedirect(req.getContextPath()+"/manageradmin.jsp");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
