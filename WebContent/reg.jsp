<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>论坛--注册</title>
<link rel="stylesheet" href="css/style.css" />
<%@ include file="links.jsp" %>
<script type="">
	
</script>
<body>

<div class="register-container">
	<h1>注册</h1>
	
	<div class="connect">
		<p>Link the world. Share to world.</p>
	</div>
	<font color="red">${msg }</font>
	<form action="${pageContext.request.contextPath }/user.s?op=reg" method="post" id="registerForm" enctype="multipart/form-data">
		<div id="css">
			<input id="name" type="text" name="uname" class="uname" placeholder="您的用户名" autocomplete="off" onblur="diff()"/>
				<br/><span id="uname" color="red;"></span>
			<input id="upass" type="password" name="upass" class="upass" placeholder="输入密码" oncontextmenu="return false" onpaste="return false" /><br/>
			<input id="upass1" type="password" name="upass1" class="upass1" placeholder="再次输入密码" oncontextmenu="return false" onpaste="return false" /><br/>
			<input id="phone" type="text" name="phone" class="phone_number" placeholder="输入手机号码" autocomplete="off"/><br/>
			<input id="email" type="email" name="email" class="email" placeholder="输入邮箱地址" oncontextmenu="return false" onpaste="return false" /><br/>
			<input type="text" name="vfcode" placeholder="验证码">
					<br/> <img id="code" alt="验证码" src="${pageContext.request.contextPath }/createVfCode.jsp" onclick="updateCode()" style="vertical-align:bottom;">
				        <a href="javascript:updateCode()">换一张</a>
		</div>
		<div>
			<h3>性别:
					男<input type="radio" name="gender" value="1" checked="checked" >
					女<input type="radio" name="gender" value="2"  />
			</h3> 
		</div>
					
		<div style="width:100%">
		<br/><br/>
		选择选择头像<br/>
		<!-- <input class="easyui-filebox" name="head" style="width:300px"> -->
		<image src="image/head/1.gif"/><input type="radio" name="head" value="1.gif" checked="checked">
				<img src="image/head/2.gif"/><input type="radio" name="head" value="2.gif" display="none">
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
		</div>
		选择本地头像
				<br/>
				<input type="file" name="myhead" style="width:300px"><br/>
				<button id="submit" type="submit">注 册</button>
	</form>
	<a href="login.jsp">
		<button type="button" class="register-tis">已经有账号？</button>
	</a>
	<a href="${pageContext.request.contextPath }/index.s">
		<button type="button" class="">匿名浏览</button>
	</a>

</div>


<script src="js/jquery.min.js"></script>
<script src="js/common.js"></script>
<!--背景图片自动更换-->
<script src="js/supersized.3.2.7.min.js"></script>
<script src="js/supersized-init.js"></script>
<!--表单验证-->
<script src="js/jquery.validate.min.js?var1.14.0"></script>
<!-- 用户名验证 -->
<script type="text/javascript">
	/* function diff(){
		var name = $("#name").val();
		$.ajax({
			url:"user.s?op=verifyName&uname="+name,
			type:"POST",
			dataType:"JSON",
			success:function(data){
				if(data!=null){
					$("#uname").html("此用户名存在");
				}else{
					$("#uname").html("");
				}
			}
		})
	} */
</script>
</body>
</html>
<!--
本代码由js代码收集并编辑整理;
尊重他人劳动成果;
转载请保留js代码链接 - www.jsdaima.com
-->