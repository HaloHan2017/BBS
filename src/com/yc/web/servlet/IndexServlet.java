package com.yc.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.dao.BoardDao;
import com.yc.dao.HotDao;

/**
 * 首页数据展示
 */
@WebServlet("/index.s")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BoardDao bdao=new BoardDao();
	private HotDao hd = new HotDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//调用dao层对象方法，查找获得首页数据
		List<Map<String, Object>> indexData = bdao.showIndexData();
		
		List<Map<String,Object>> topicList = hd.findIndex();
		request.setAttribute("data",topicList);
		List<Map<String,Object>> user = hd.findUser();
		request.setAttribute("people", user);
		
		//设置的request域
		request.setAttribute("indexData", indexData);
		//转发到首页（一定不能用重定向）
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
