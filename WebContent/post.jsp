<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<Link rel="stylesheet" type="text/css" href="style/style.css" />
		<script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
		<script src="js/ckeditor/ckeditor.js"></script>
		<script src="${pageContext.request.contextPath }/js/common_util.js"></script>
		<script type="text/javascript">
			function goPostTopic(){
				for ( instance in CKEDITOR.instances )
				    CKEDITOR.instances[instance].updateElement();
				var data = $('#form').serialize();
				postTopic('post',data);
			}
		
		</script>
	</head>
	<body>
		<!-- head.jsp -->
		<%@ include file="head.jsp" %>
		<br/>
		<!-- 导航        -->
		<%@ include file="nav.jsp" %>
		
		<DIV class="t" style="MARGIN-TOP: 15px" align="center">
			<font color="red">${msg }</font><br/><br/>
			<center>
				发表话题
				<hr width=80% />
				<FORM action="${pageContext.request.contextPath }/topic.s?op=post" method="post" id="form">
					<input type="hidden" name="uid" value="${user.uid }" />
					<input type="hidden" name="boardid" value="${param.boardid }" />
					<br />
					标题:  
					<input type="text"  name="title" maxLength="50" value="${param.title }"/>
					<br />
					内容: 
					<textarea name="content" id="content" rows="10" cols="80" >
		             	${param.content }
		            </textarea>
					<br />
					<INPUT onclick="goPostTopic()" id="but" class="btn" tabIndex="6" type="button" value="发表">
				</FORM>
		
			</center>
		
		</DIV>
	</body>
	<script>
        CKEDITOR.replace( 'content' );
    </script>
</html>