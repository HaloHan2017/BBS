<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<TITLE>论坛--帖子列表</TITLE>
		
		<Link rel="stylesheet" type="text/css" href="style/style.css" />
		<style type="text/css">
			
		</style>
	</HEAD>
	
	<BODY>
		<!-- head.jsp -->
		<%@ include file="head.jsp" %>

		<!-- 主体        -->
		<DIV>
			<!-- 导航        -->
			<br/>
			<%@ include file="nav.jsp" %>
			<br/>
		
			<DIV class="t">
				<TABLE cellSpacing="0" cellPadding="0" width="100%">		
					<TR>
						<TH class="h" style="WIDTH: 100%" colSpan="6"><SPAN></SPAN></TH>
					</TR>
					<!-- 表 头           -->
					<TR class="tr2">
						<TD style="WIDTH: 5%" align="center">编号</TD>
						<TD style="WIDTH: 5%" align="center">等级</TD>
						<TD style="WIDTH: 20%" align="center">姓名</TD>
						<TD style="WIDTH: 20%" align="center">头像</TD>
						<TD style="WIDTH: 20%" align="center">注册时间</TD>
						<TD style="WIDTH: 5%" align="center">性别</TD>
						<TD style="WIDTH: 10%" align="center">发帖数</TD>
						<TD style="WIDTH: 10%" align="center">回帖数</TD>
					</TR>
					
					<TR class="tr2">
						<TD style="WIDTH: 5%" align="center">${user.uid }</TD>
						<TD style="WIDTH: 5%" align="center">${user.lid }</TD>
						<TD style="WIDTH: 20%" align="center">${user.uname }</TD>
						<TD style="WIDTH: 20%" align="center">
							<c:if test="${fn:length(user.head) < 15 }">
								<image src="image/head/${user.head }"/>
							</c:if>
							<c:if test="${fn:length(user.head) >= 15 }">
								<image src="image/upload/head/${user.head }"/>
							</c:if>
						</TD>
						<TD style="WIDTH: 20%" align="center">
							<fmt:formatDate value="${user.regtime }" pattern="yyyy-MM-dd"/>
						</TD>
						<TD style="WIDTH: 5%" align="center">
							<c:if test="${user.gender==1 }">男</c:if>
							<c:if test="${user.gender==2 }">女</c:if>
						</TD>
						<TD style="WIDTH: 10%" align="center">${userInfo.topicnum }</TD>
						<TD style="WIDTH: 10%" align="center">${userInfo.replynum }</TD>
					</TR>
				</TABLE>
			</DIV>
		</DIV>
		<div>
			<h3>收藏的帖子</h3>
			<TABLE cellSpacing="0" cellPadding="0" width="100%">		
					<TR>
						<TH class="h" style="WIDTH: 100%" colSpan="4"><SPAN>&nbsp;</SPAN></TH>
					</TR>
					<!-- 表 头           -->
					<TR class="tr2">
						<TD>&nbsp;</TD>
						<TD style="WIDTH: 60%" align="center">文章</TD>
						<TD style="WIDTH: 40%" align="center">添加时间</TD>
					</TR>
				<c:forEach items="${likeTopics }" var="like">
					<!--  主 题 列 表        -->
					
					<TR class="tr3">
						<TD><IMG src="image/topic.gif" border=0></TD>
						<TD  align="center" style="FONT-SIZE: 15px">
							<A href="${pageContext.request.contextPath }/topic.s?op=detail&topicid=${like.topicid}
								&boardname=${like.boardname}">${like.title }</A>
						</TD>
						<TD align="center">
							[<fmt:formatDate value="${like.addtime }" pattern="yyyy-MM-dd"/>]
						</TD>
					</TR>
				</c:forEach>
				
			</TABLE>
		</div>
		<!--  声 明          -->
		<BR/>
		<CENTER class="gray">源辰信息</CENTER>
	</BODY>
</HTML>