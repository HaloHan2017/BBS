<%@page import="javax.imageio.ImageIO"%>
<%@page import="com.yc.utils.MyUtils"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	//获取随机生成的验证码图形缓冲对象
	BufferedImage bi = MyUtils.createImageCode(session);
	//把图形缓冲对象以jpg格式的形式输出到指定流
	ImageIO.write(bi,"jpg",response.getOutputStream());
	//清空流
	out.clear();
	//更新PageContext中Page范围内Out对象
	out = pageContext.pushBody();
%>