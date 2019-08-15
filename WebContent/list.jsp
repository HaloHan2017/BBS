<%@page import="com.yc.bean.TblUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<TITLE>论坛--帖子列表</TITLE>
		
		<Link rel="stylesheet" type="text/css" href="style/style.css" />
		<script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
		<script type="text/javascript"> 
			var uid='${user.uid}';
			var boardid='<%=request.getParameter("boardid")%>';
		</script>
		<script src="${pageContext.request.contextPath }/js/common_util.js"></script>
		
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
			<!-- 新帖       -->
			<DIV>	
				<A id="post" onclick="canPost('post')" href="javascript:void(0);"><IMG src="image/post.gif" name="td_post" border="0" ></A>
			</DIV>
			<!--  翻 页         -->
			<!-- <DIV>
				<a href="list.html">上一页</a>|
				<a href="list.html">下一页</a>
			</DIV> -->
		
			<DIV class="t">
				<TABLE cellSpacing="0" cellPadding="0" width="100%">		
					<TR>
						<TH class="h" style="WIDTH: 100%" colSpan="4"><SPAN>&nbsp;</SPAN></TH>
					</TR>
					<!-- 表 头           -->
					<TR class="tr2">
						<TD>&nbsp;</TD>
						<TD style="WIDTH: 80%" align="center">文章</TD>
						<TD style="WIDTH: 10%" align="center">作者</TD>
						<TD style="WIDTH: 10%" align="center">回复</TD>
					</TR>
			
				<c:forEach items="${topicList }" var="topic">
					<!--  主 题 列 表        -->
					
					<TR class="tr3">
						<TD><IMG src="image/topic.gif" border=0></TD>
						<TD style="FONT-SIZE: 15px">
							<A href="${pageContext.request.contextPath }/topic.s?op=detail&topicid=${topic.topicid}
								&boardname=${param.boardname}">${topic.title }</A>
						</TD>
						<TD align="center">${topic.uname}</TD>
						<TD align="center">
							<c:if test="${topic.ctn!=null }">${topic.ctn }</c:if>
							<c:if test="${topic.ctn==null }">0</c:if> 
							
						</TD>
					</TR>
				</c:forEach>	
				
				</TABLE>
			</DIV>
			<!--  翻 页          -->
			<!-- <DIV>
				<a href="list.html">上一页</a>|
				<a href="list.html">下一页</a>
			</DIV> -->
		</DIV>
		<!--  声 明          -->
		<BR/>
		<CENTER class="gray">版权所有 TYS</CENTER>
	</BODY>
</HTML>
    