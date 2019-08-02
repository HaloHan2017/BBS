<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/fileUpload.s" method="post" enctype="multipart/form-data">
		姓名：<input type="text" name="name" value=""/><br>
		头像：<input type="file" name="myhead" value=""/><br>
		<input type="submit" value="上传"/>
	</form> 
</body>
</html>