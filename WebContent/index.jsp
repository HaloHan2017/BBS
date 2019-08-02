<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
	<head>
		<meta charset="UTF-8">
		<TITLE>论坛</TITLE>
		<Link rel="stylesheet" type="text/css" href="style/style.css" />
		<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
		<script type="text/javascript">
			var tid;
			var index=0;
			var num=new Array();
			num[0]=0;
			num[1]=0;
			num[2]=0;
			
			$(function(){
				$.ajax({
					url:"num.s?op=getnum",
					type:"POST",			
					dataType:"JSON",
					success:function(data){
						for(var i=0;i<3;i++){
							if(data[i]==null){
								num[i]=0;
							}else{
								if(data[i]==2){
									num[i]=data[i];
									var index = i+1;
									document.getElementById("img"+index).src="image/guanggao/4.jpg";
									document.getElementById("id"+index).removeAttribute("href");
									$.ajax({
										url:"num.s?op=onclick&num"+index+"="+num[i],
										type:"POST",			
										dataType:"JSON",
									})
								}else{
									num[i]=data[i];
								}
							}
						}	
					}
				})
				run();
			})
			function run(){
				tid = setInterval(function(){
					//获取所有li
					var lis = document.getElementsByTagName("li");
					for(var i in lis){
						lis[i].className="hide";
					}
					lis[index].className="show";
					if(index<lis.length-1){
						index++;
					}else{
						index=0;
					}
				}, 3000)
			}
			function stop(){
				clearInterval(tid);
			}
			function addnum(n){
				if(num[n-1]==2){
					document.getElementById("img"+n).src="image/guanggao/4.jpg";
					document.getElementById("id"+n).removeAttribute("href");
					$.ajax({
						url:"num.s?op=onclick&num"+n+"="+num[n-1],
						type:"POST",			
						dataType:"JSON",
					})
				}else{
					num[n-1]++;
					/* document.getElementById("num"+n).value=num[n-1]; */
					$.ajax({
						url:"num.s?op=onclick&num"+n+"="+num[n-1],
						type:"POST",			
						dataType:"JSON",
					})
				}
				
				
				
			}
		</script>
		
	</HEAD>
	<BODY>
	
		<!-- head.jsp -->
		<%@ include file="head.jsp" %>
		
		<!--主体        -->
		<DIV class="t" style="WIDTH:20%;HEIGHT:400px;float:left">
			<h2 style="text-align: center;">热帖榜</h2>
			<TABLE cellSpacing="0" cellPadding="0">
				<TR class="tr2" align="center">
					<TD style="WIDTH:5%">文章</TD>
					<TD style="WIDTH: 5%;">作者</TD>	
					<TD style="WIDTH: 5%">回帖</TD>
				</TR>
					
					<c:forEach items="${data }" var="topic">
				<TR class="tr3">
					<!--  主 题 列 表        -->
						<TD style="FONT-SIZE: 15px">
							<A href="${pageContext.request.contextPath }/topic.s?op=detail&topicid=${topic.topicid}
								&boardname=${param.boardname}">${topic.title }</A>
						</TD>
						<TD align="center">${topic.uname}</TD>
						<TD align="center">
							<c:if test="${topic.coun!=null }">${topic.coun}</c:if>
							<c:if test="${topic.coun==null }">0</c:if> 
						</TD>
				</TR>
				</c:forEach>
			</TABLE>
		</DIV>
		
		<!-- 广告位 -->
		<DIV class="t" style="WIDTH:60%;height:400px;float:left;">
			<ul onmouseout="run()" onmouseover="stop()" id="s">
				<li class="show"><a id="id1" href="https://www.baidu.com"><img id="img1" src="image/guanggao/1.jpg" style="width:95%;height:350px" onclick="addnum(1)"></a></li>
				<li class="hide"><a id="id2" href="https://pvp.qq.com/"><img id="img2" src="image/guanggao/2.jpg" style="width:95%;height:350px" onclick="addnum(2)"></a></li>
				<li class="hide"><a id="id3" href="http://tx3.163.com/"><img id="img3" src="image/guanggao/3.jpg" style="width:95%;height:350px" onclick="addnum(3)"></a></li>
			</ul>
		</DIV>
		<!-- 漂浮广告 -->
		<DIV id="demo" style="position:absolute" onload="move()">
		<p style="CURSOR:hand;color:red;font-weight:bold;position: relative;top:20px" onclick="clearInterval();demo.style.visibility = 'hidden'" >X</p>
			<a href="http://www.baidu.com" target="_blank">
			<img src="image/logo.gif" border="0">
			</a>
			
		</DIV>
		
		<script type="text/javascript">	
		 window.onload = function(){
	            var demo = document.getElementById('demo');
	            var sx = sy = 10;
	            var x = y = 0;
	 
	            function move(){
	                if(document.documentElement.clientWidth - demo.offsetWidth-10 < x || x < 0){
	                    sx = -sx;
	                }
	 
	                if(document.documentElement.clientHeight - demo.offsetHeight-10 < y || y < 0){
	                    sy = -sy;
	                }
	 
	                x = demo.offsetLeft + sx;
	                y = demo.offsetTop + sy;
	 
	                demo.style.left = x + 'px';
	                demo.style.top = y + 'px';
	            }
	 
	            var timer = setInterval(move, 150);
	 
	            demo.onmouseover = function(){
	            	$("#closeDemo").css("display","block");
	                clearInterval(timer);
	            }
	 
	            demo.onmouseout = function(){
	            	$("#closeDemo").css("display","none");
	                timer = setInterval(move, 150);
	            }

	        }
	    </script>

		<!-- 风云榜 -->
		<DIV class="t"  style="WIDTH:19%;height:400px;float:right">
			<h2 style="text-align: center;">风云榜</h2>
			<TABLE cellSpacing="0" cellPadding="0" width="20%">
				<TR class="tr2" align="center">
					<TD style="WIDTH:10%">头像</TD>
					<TD style="WIDTH: 5%;">姓名</TD>
					<TD style="WIDTH: 5%">发帖</TD>
				</TR>
			<c:forEach items="${people}" var="user">
			<TR class="tr3">
					<!--  主 题 列 表        -->
						<TD align="center" style="WIDTH: 10%">
							<c:if test="${fn:length(user.head) < 15 }">
								<image src="image/head/${user.head }" border="0"/>
							</c:if>
							<c:if test="${fn:length(user.head) >= 15 }">
								<image src="image/upload/head/${user.head }" border="0"/>
							</c:if>
						</TD>
						<TD align="center" style="WIDTH: 5%">${user.uname}</TD>
						<TD align="center" style="WIDTH: 5%">${user.coun}</TD>
			</TR>
			</c:forEach>
			</TABLE>
		</DIV>
		
		<!-- 信息列表 -->
		
		<DIV class="t" style="WIDTH:100%;">
			<TABLE cellSpacing="0" cellPadding="0" width="56%">
				<TR class="tr2" align="center">
					<TD colSpan="2">论坛</TD>
					<TD style="WIDTH: 10%;">主题</TD>
					<TD style="WIDTH: 20%">最后发表</TD>
				</TR>	
					<%
				//记录当前的版块
				String currBoardName=null;
			%>
			<c:forEach items="${indexData }" var="i">
				<%
					//获得当前记录的主版块名字
					Map<String,Object> i=(Map<String,Object>)pageContext.getAttribute("i");
					String pname=(String)i.get("pname");
				%>
				<%
					//对比当前记录的主版块名 和 当前显示的主版块名
					if(!pname.equals(currBoardName)){
						currBoardName=pname;
				%>
				<!--  主版块       -->
				<TR class="tr3">
					<TD colspan="4"><h2><b>${i.pname }</h2></b></TD>
				</TR> 
				<%
					}
				%>
				<!-- 子版块       -->
				<TR class="tr3">
					<TD width="5%">&nbsp;</TD>
					<TH align="left">
						<IMG src="image/board.gif">
						<A style="display: inline;" href="${pageContext.request.contextPath }/topic.s?op=list&boardid=${i.bid}&boardname=${i.cname}">${i.cname}</A>
					</TH>
					
					<TD align="center">
						<c:if test="${i.ctn!=null }">${i.ctn }</c:if>
						<c:if test="${i.ctn==null }">0</c:if>
					</TD>
					
					<TH>
						<SPAN>
							<A href="${pageContext.request.contextPath }/topic.s?op=detail&topicid=${i.topicid }">${i.title }</A>
						</SPAN>
						<BR/>
						<SPAN>${i.uname }</SPAN>
						<SPAN class="gray">
							<c:if test="${i.publishtime!=null }">
								[<fmt:formatDate value="${i.publishtime }" pattern="yyyy-MM-dd HH:mm:ss"/>]
							</c:if>
							<c:if test="${i.publishtime==null }"></c:if>
						</SPAN>
					</TH>
					</TR>
			</c:forEach>
			</TABLE>
		</DIV>
		<div >
				<input type="text" value="" id="num1">
				<input type="text" value="" id="num2">
				<input type="text" value="" id="num3">
		</div>
		
		<BR/>
	</BODY>
</HTML>