<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<TITLE>论坛--注册</TITLE>
		<!-- links.jsp -->
		<%@ include file="links.jsp" %>
		<script type="text/javascript">
			function verify2Pass(){
				console.log("aaaa");
				//获取输入的密码  和  重复密码
				var upass=$("#upass").val();
				var upass1=$("#upass1").val();
				if(upass.trim()!="" && upass1.trim()!=""){
					if(upass.trim() != upass1.trim()){
						$("#vpass").html("两次密码不一致！");
					}
					$("#vpass").html("");
				}
			}
            function updateCode(){
                var img = document.getElementById("code");
                //相同的url请求，浏览器会在缓存里加载数据并不会往服务器重新发送,所以后面加一个随机数
                img.src="createVfCode.jsp?"+ Math.random();
            }
		</script>
	</HEAD>
	<BODY>
		<!-- head.jsp -->
		<%@ include file="head.jsp" %>
		<BR/>
		<!--      导航        -->
		<%@ include file="nav.jsp" %>
		<!--      用户注册表单        -->
		<DIV  class="t" style="MARGIN-TOP: 15px" align="center">
			<font color="red">${msg }</font>
			<FORM name="regForm" action="${pageContext.request.contextPath }/user.s?op=reg" method="post" id="form" enctype="multipart/form-data">
				<br/>&nbsp;用 户 名 &nbsp;
					<input class="easyui-textbox" data-options="iconCls:'icon-man'" name="uname" 
						required="required" style="width:13%;height:34px;padding:10px;" prompt="请输入用户名">
				<br/>&nbsp;密&nbsp;&nbsp;码 &nbsp;
					<input id="upass" class="easyui-passwordbox"  prompt="请输入密码" iconWidth="28" name="upass" 
						required="required" style="width:13%;height:34px;padding:10px">
				<br/>&nbsp;重复密码 &nbsp;
					<input id="upass1" class="easyui-passwordbox"  prompt="请再次输入密码" iconWidth="28" name="upass1" required="required"
                          style="width:13%;height:34px;padding:10px;" onblur="verify2Pass()">
                    <!-- 用于显示两次密码是否一致 -->	<span id="vpass" color="red;"></span>
				<br/>&nbsp;手机号码 &nbsp;
					<INPUT id="phone" class="easyui-textbox" tabIndex="3" type="text"  name="phone"
						style="width:13%;height:34px;padding:10px;" required="required" prompt="请输入手机号码">
				<br/>&nbsp;&nbsp;邮箱&nbsp;&nbsp;
					<INPUT id="email" class="easyui-textbox" tabIndex="3" type="email" name="email" 
						style="width:13%;height:34px;padding:10px;"required="required" prompt="请输入邮箱">	
				<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;验证码 &nbsp;&nbsp;<INPUT class="easyui-textbox" tabIndex="1"  type="text" id="vfcode" 
					 maxLength="20" size="20" name="vfcode" required="required" prompt="请输入验证码">
					 <img id="code" alt="验证码" src="${pageContext.request.contextPath }/createVfCode.jsp" onclick="updateCode()" style="vertical-align:bottom;">
				        <a href="javascript:updateCode()">换一张</a>
				<br/>性别 &nbsp;
					男<input type="radio" name="gender" value="1" checked="checked">
					女<input type="radio" name="gender" value="2"  />
				<br/><br/>请选择头像 <br/>
				<image src="image/head/1.gif"/><input type="radio" name="head" value="1.gif" checked="checked">
				<img src="image/head/2.gif"/><input type="radio" name="head" value="2.gif">
				<img src="image/head/3.gif"/><input type="radio" name="head" value="3.gif">
				<img src="image/head/4.gif"/><input type="radio" name="head" value="4.gif">
				<img src="image/head/5.gif"/><input type="radio" name="head" value="5.gif">
				<BR/>
				<img src="image/head/6.gif"/><input type="radio" name="head" value="6.gif">
				<img src="image/head/7.gif"/><input type="radio" name="head" value="7.gif">
				<img src="image/head/8.gif"/><input type="radio" name="head" value="8.gif">
				<img src="image/head/9.gif"/><input type="radio" name="head" value="9.gif">
				<img src="image/head/10.gif"/><input type="radio" name="head" value="10.gif">
				<BR/>
				<img src="image/head/11.gif"/><input type="radio" name="head" value="11.gif">
				<img src="image/head/12.gif"/><input type="radio" name="head" value="12.gif">
				<img src="image/head/13.gif"/><input type="radio" name="head" value="13.gif">
				<img src="image/head/14.gif"/><input type="radio" name="head" value="14.gif">
				<img src="image/head/15.gif"/><input type="radio" name="head" value="15.gif">
				<br/>
				<!-- 本地选择头像 -->
				选择本地头像
				<br/>
				<input class="easyui-filebox" name="myhead" style="width:300px">
				<br>
					<a onClick="form.submit();" href="#" class="easyui-linkbutton" iconCls="icon-ok" style="width:7%;height:32px">注册</a>
			</FORM>
		</DIV>
		<!-- 声明        -->
		<BR>
		<CENTER class="gray">版权所有 TYS</CENTER>
	</BODY>
</HTML>
