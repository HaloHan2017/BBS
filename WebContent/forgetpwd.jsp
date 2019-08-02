<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>忘记密码</title>
		<!-- links.jsp -->
	<%@ include file="links.jsp" %>
	<script type="text/javascript">
	
		function modifyPwd(){
			if($("#newpass").val().trim()==""){
				alert('新密码不能为空');
				return;
			}
			$.ajax({
				url:"${pageContext.request.contextPath }/user.s?op=modifyPwd", //处理页面的路径
				data:$("#form").serialize(), //要提交的数据是一个JSON
				type:"POST", //提交方式
				dataType:"JSON", //返回数据的类型
				//TEXT字符串 JSON返回JSON XML返回XML
				success:function(data){ //回调函数 ,成功时返回的数据存在形参data里
					var dataObj=eval("("+data+")");//
					if(dataObj.code=="1"){
						alert('修改成功,将跳转到登录界面！');
						location.href="${pageContext.request.contextPath }/login.jsp";
					}else{
						alert(dataObj.msg);
					}
				}
			});
		}
		//向用户的邮箱发送验证码
		function goSendVCode(){
			var email=$("#email").val();
			if(email.trim()==""){
				alert("邮箱不能为空！");
				return;
			}
			$.ajax({
				url:"${pageContext.request.contextPath }/user.s?op=sendVCode", //处理页面的路径
				data:{"email":email}, //要提交的数据是一个JSON
				type:"POST", //提交方式
				dataType:"JSON", //返回数据的类型
				//TEXT字符串 JSON返回JSON XML返回XML
				success:function(data){ //回调函数 ,成功时返回的数据存在形参data里
					if(data.res=="1"){
						alert("发送成功！验证码时效为：120秒，请赶快去您的邮箱查看");
					}else{
						alert("发送失败！");
					}
				}
			});
		}
	</script>
	</head>
	<body>
		<!-- head.jsp -->
		<%@ include file="head.jsp" %>
		<BR/>
		<!-- 导航      -->
		<%@ include file="nav.jsp" %>
		
		<DIV class="t" style="MARGIN-TOP: 15px" align="center">
			<h2>找回密码</h2>
			<FORM name="loginForm" action="" method="post" id="form">
				<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮箱 &nbsp;<INPUT class="easyui-textbox" tabIndex="2"  type="email"  maxLength="20" size="20" id="email" name="email" required="required">
					<a id="send" href="#" onClick="goSendVCode()">发送验证码</a>
				<br/>验证码 &nbsp;<INPUT id="ecode" class="easyui-textbox" tabIndex="2"  type="text"  maxLength="20" size="20"  name="ecode"  required="required" >
				<br/>新密码 &nbsp;<INPUT id="newpass" class="easyui-textbox" tabIndex="2"  type="password"  maxLength="20" size="20"  name="newpass"  required="required">
				<br>&nbsp;&nbsp;&nbsp;&nbsp;<a onClick="modifyPwd()" href="#" class="easyui-linkbutton" iconCls="icon-ok" style="width:7%;height:32px">确认</a>
			</FORM>
		</DIV>
		<!-- 声明      -->
		<BR/>
		<CENTER class="gray">源辰信息</CENTER>
	
	</body>
</html>