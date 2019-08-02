package com.yc.web.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import com.yc.bean.LikeModel;

/**
 * Servlet implementation class onclicknum
 */
@WebServlet("/num.s")
public class onclicknum extends BaseServlet {
	
	public void onclick(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext application = this.getServletContext();
		if(request.getParameter("num1")==null){
			if(request.getParameter("num2")==null){
				String num = request.getParameter("num3");
				application.setAttribute("num3", num);
			}else{
				String num = request.getParameter("num2");
				application.setAttribute("num2", num);
			}
		}else{
			String num = request.getParameter("num1");
			application.setAttribute("num1", num);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void getnum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext application = this.getServletContext();
		String num1 = (String) application.getAttribute("num1");
		String num2 = (String) application.getAttribute("num2");
		String num3 = (String) application.getAttribute("num3");
		String[] num = new String[3];
		num[0]=num1;
		num[1]=num2;
		num[2]=num3;
		super.outJsonString(num, response);
		
	}

}
