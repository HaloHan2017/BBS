package com.yc.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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

@WebFilter(urlPatterns = { "/post.jsp","/postreply.jsp" })
public class BRightFIlter implements Filter {

    public BRightFIlter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpSession session=req.getSession();
		
		//判断用户是否登录
		
		if(session.getAttribute("user")!=null){
			//如果已经登录则跳转目标页面
			chain.doFilter(request, response);
		}else{
			/*
			 * 	127.0.0.1/bbs/topic.s?op=list&boardid=2
			 *  path(请求地址)：topic.s
			 *  map (请求参数)：[op=list&boardid=2]
			 * */
			//获取请求参数  和  请求地址
			String path = req.getServletPath();
			Map<String, String[]> map = req.getParameterMap();
			
			//创建一个新的map，存map里的值（因为req.getParameterMap()是属于一个请求的额，有时效性）
			Map<String, String[]> newmap=new HashMap<String, String[]>();
			newmap.putAll(map);
			
			//保存请求地址和参数  ===》存session
			session.setAttribute("callback-path", path);
			session.setAttribute("callback-map", newmap);
			//判断是否有用户登录
			
			//未登录则跳转到登录页面
			HttpServletResponse resp=(HttpServletResponse) response;
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out=resp.getWriter();
			out.println("<script>alert('您尚未登录');location.href='login.jsp;'</script>");
			out.flush();
		}
			
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
