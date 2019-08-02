<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<DIV>
		<IMG src="image/logo.gif">
	</DIV>
	<!-- 用户信息、登录、注册  -->
	
	<DIV class="h">
		<c:if test="${user==null }">
			您尚未　<a href="login.jsp">登录</a>
			&nbsp;|&nbsp;<A href="reg.jsp">注册</A> |
		</c:if>
		<c:if test="${user!=null }">
			欢迎您： ${user.uname } | 
			<span>等级：Lv${user.lid }</span> |
			<A href="${pageContext.request.contextPath }/user.s?op=goUser">个人信息</A> |
			<A href="${pageContext.request.contextPath }/user.s?op=goAlter">修改密码</A> |
			<A href="${pageContext.request.contextPath }/user.s?op=logout">退出登录</A> |
		</c:if>
	</DIV>