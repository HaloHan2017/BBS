<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title></title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" href="css/style.css" />
<%@ include file="links.jsp" %>
	 <script type="text/javascript">
            function updateCode(){
                var img = document.getElementById("code");
                //相同的url请求，浏览器会在缓存里加载数据并不会往服务器重新发送,所以后面加一个随机数
                img.src="createVfCode.jsp?"+ Math.random();
            }
     </script>

<body>

<div class="login-container">
	<h1>登录</h1>
	<font color="red">${msg }</font>
	<form action="${pageContext.request.contextPath }/user.s?op=login" method="post" id="loginForm">
		<div id="css">
			<input type="text" name="uname" class="uname" placeholder="用户名" autocomplete="off"/><br/>
			<input type="password" name="upass" class="upass" placeholder="密码" oncontextmenu="return false" onpaste="return false" /><br/>
			<input type="text" name="vfcode" placeholder="验证码"><br/>
					 <img id="code" alt="验证码" src="${pageContext.request.contextPath }/createVfCode.jsp" onclick="updateCode()" style="vertical-align:bottom;">
				        <a href="javascript:updateCode()">换一张</a>
			<br/><a href="${pageContext.request.contextPath }/forgetpwd.jsp"  >忘记密码</a>
		</div>
		<button id="submit" type="submit">登 录</button>
	</form>
	

	<a href="reg.jsp">
		<button type="button" class="register-tis">还有没有账号？</button>
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

</body>
</html>