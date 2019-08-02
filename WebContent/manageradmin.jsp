<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<!DOCTYPE html>
<html lang="en" class="no-js">

    <head>

        <meta charset="utf-8">
        <title>管理员登录(Login)</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- CSS -->
        <link rel="stylesheet" href="assets/css/reset.css">
        <link rel="stylesheet" href="assets/css/supersized.css">
        <link rel="stylesheet" href="assets/css/style.css">

        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
            <script src="assets/js/html5.js"></script>
        <![endif]-->
		<script type="text/javascript">
            function updateCode(){
                var img = document.getElementById("code");
                //相同的url请求，浏览器会在缓存里加载数据并不会往服务器重新发送,所以后面加一个随机数
                img.src="createVfCode.jsp?"+ Math.random();
            }
        </script>
    </head>

    <body>

        <div class="page-container">
            <h1>登录(Login)</h1><br>
            <span color="red" font-size="16px">${msg }</span>
            <form action="${pageContext.request.contextPath }/admin.s?op=adminLogin" method="post">
                <input type="text" name="aname" class="username" placeholder="请输入您的用户名！">
                <input type="password" name="apass" class="password" placeholder="请输入您的用户密码！">
                <input type="Captcha" class="Captcha" name="vfcode" placeholder="请输入验证码！">
                <div style="margin-top:25px;">
                	<img style="width:80px;height:40px;" alt="" src="${pageContext.request.contextPath }/createVfCode.jsp"></div>
                	 <a href="javascript:updateCode()" color="#000">换一张</a>
                <button type="submit" class="submit_button">登录</button>
                <div class="error"><span>+</span></div>
            </form>
            <!-- <div class="connect">
                <p>快捷</p>
                <p>
                    <a class="facebook" href=""></a>
                    <a class="twitter" href=""></a>
                </p>
            </div> -->
        </div>
		
        <!-- Javascript -->
        <script src="assets/js/jquery-1.8.2.min.js" ></script>
        <script src="assets/js/supersized.3.2.7.min.js" ></script>
        <script src="assets/js/supersized-init.js" ></script>
        <script src="assets/js/scripts.js" ></script>

    </body>
<div style="text-align:center;">
</div>
</html>

