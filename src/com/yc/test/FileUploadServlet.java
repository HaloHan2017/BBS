package com.yc.test;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.yc.utils.MyUtils;

@WebServlet("/fileUpload.s")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletContext().getInitParameter("path");
		String virtualPath = request.getServletContext().getInitParameter("virtualPath");
		Map<String, Object> map = MyUtils.upload(request, "myhead", path,virtualPath);
		
		String name = (String) map.get("name");
		String newFileName = (String) map.get("mayhead");
		System.out.println("name="+name);
		System.out.println("uploadFileName="+newFileName);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
