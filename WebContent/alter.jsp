<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
	<meta charset="utf-8">
	<TITLE>修改用户密码</TITLE>
	
	<!-- links.jsp -->
	<%@ include file="links.jsp" %>
	
	</HEAD>

	<BODY>
		<!-- head.jsp -->
		<%@ include file="head.jsp" %>
		<BR/>
		<!--      导航        -->
		<%@ include file="nav.jsp" %>
		<!--      用户登录表单        -->
		<DIV class="t" style="MARGIN-TOP: 15px" align="center">
			<font color="red">${msg }</font>
			<FORM action="${pageContext.request.contextPath }/user.s?op=alter" method="post" id="alterForm">
				<br/>请输入原密码 &nbsp;<INPUT class="easyui-textbox" tabIndex="1"  type="password"      maxLength="20" size="20" name="upass">
				<br/>请输入新密码 &nbsp;<INPUT class="easyui-textbox" tabIndex="2"  type="password"  maxLength="20" size="20" name="upass1">
				<br/>请再次输入新密码 &nbsp;<INPUT class="easyui-textbox" tabIndex="3"  type="password"  maxLength="20" size="20" name="upass2">
				<br/>&nbsp;&nbsp;&nbsp;
				<a onClick="alterForm.submit();" href="#" class="easyui-linkbutton" iconCls="icon-ok" style="width:7%;height:32px">确认修改</a>
			</FORM>
		</DIV>
		<!--      声明        -->
		<BR/>
		<CENTER class="gray">版权所有 TYS</CENTER>
	</BODY>
	
</HTML>
    