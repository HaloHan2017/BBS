<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.yc.bean.TblUser"%>
<!doctype html>
<html>
	<head>
		<meta charset="UTF-8">
		<TITLE>论坛--看贴</TITLE>
		<Link rel="stylesheet" type="text/css" href="style/style.css" />
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript"> 
			var uid='${user.uid}'; //获取当前登录用户的id
			var topicid='${topic.topicid}';
		</script>
		<script src="${pageContext.request.contextPath }/js/common_util.js"></script>
		<script type="text/javascript">
			$(function(){
				//查询帖子的点赞数
				$.ajax({
					url:"reply.s?op=glktimes&topicid=${param.topicid}",
					type:"POST",			
					dataType:"JSON",
					success:function(data){
						//var dataObj=eval("("+data+")");//
						if(data.code==1){
							$("#glktimes").html("点赞数:"+data.obj);
						}
					}
				});
				
				
				//查询回帖的点赞数
				/* 回帖点赞显示 */
				<c:forEach items="${replyidList }" var="replyid">
					$.ajax({
						url:"reply.s?op=replyLike&replyid="+${replyid},
						type:"POST",			
						dataType:"JSON",
						success:function(data){
							if(data.code==1){
								$("#"+${replyid}).html(data.obj+'人已赞');
							}
						}
					});
				</c:forEach>
				//查询是否已收藏
				$.ajax({
					url:"like.s?op=alllike&topicid=${param.topicid}",
					type:"POST",			
					dataType:"JSON",
					success:function(data){
						if(data.obj!=null){
							$("#like").text("已收藏");
						}else{
							$("#like").text("收藏");
						}
					}
				});
			});	
			//点赞
			function glk(topicid){
				$.ajax({
					url:"reply.s?op=glk&topicid="+topicid,
					type:"POST",
					dataType:"JSON",
					success:function(data){
						//var dataObj=eval("("+data+")");//
						if(data.code==1){
							$("#glktimes").html("点赞数:"+data.obj);
						}
					}
				});
			}
			//回帖点赞
			function replyglk(replyid){
				var el = document.getElementById("zan");
				$.ajax({
					url:"reply.s?op=replyglk&replyid="+replyid,
					type:"POST",
					dataType:"JSON",
					success:function(data){
						//var dataObj=eval("("+data+")");//
						if(data.code==1){
							$("#"+replyid).html(data.obj+'人已赞');
						}
					}
				});
			} 
			
			//添加收藏
			function addLike(topicid){
				$.ajax({
					url:"like.s?op=addlikes&topicid="+topicid,
					type:"POST",
					dataType:"JSON",
					success:function(data){
						if(data.obj!=null){
							$("#like").text("已收藏");
						}else{
							$("#like").text("收藏");
						}
					}
				});
			}
			
		</script>
		
	</head>
	
	<BODY>
		<!-- head.jsp -->
		<%@ include file="head.jsp" %>
		
		<!--  主体        -->
		<DIV><br/>
			<!--  导航        -->
			<%@ include file="nav.jsp" %>
			<br/>
			<!-- 回复、新帖        -->
			<DIV>
				<A onclick="canPost('postreply')" href="#"><IMG src="image/reply.gif"  border="0" id=td_post></A> 
				<!-- <A href="post.jsp"><IMG src="image/post.gif"   border="0" id=td_post></A> -->
				
				<c:if test="${user!=null}">
					<a href='javascript:glk( ${param.topicid }  )'>点赞</a> 丨
					<a href='javascript:addLike(${param.topicid})' id="like">收藏</a>  
				</c:if>
				<div class="h" id="glktimes">
					点赞数:0
				</div>
				
			</DIV>
			
			<!-- 本页主题的标题      -->
			<DIV>
				<TABLE cellSpacing="0" cellPadding="0" width="100%">
					<TR>
						<TH class="h">本页主题: ${topic.title}</TH>
					</TR>
					<TR class="tr2">
						<TD>&nbsp;</TD>
					</TR>
				</TABLE>
			</DIV>
			
			<!--  主题        -->
			<h4>主题 </h4>
			<DIV class="t">
				<TABLE style="BORDER-TOP-WIDTH: 0px; TABLE-LAYOUT: fixed" cellSpacing="0" cellPadding="0" width="100%">
					<TR class="tr1">
						<TH style="WIDTH: 20%">
							<B>${topic.uname }</B><BR/>
							<c:if test="${fn:length(topic.head) < 15 }">
								<image src="image/head/${topic.head }"/>
							</c:if>
							<c:if test="${fn:length(topic.head) >= 15 }">
								<image src="image/upload/head/${topic.head }"/>
							</c:if><BR/>
							注册:<fmt:formatDate value="${topic.regtime }" pattern="yyyy-MM-dd"/><BR/>
						</TH>
						<TH>
							<H4>${topic.title}</H4>
							<DIV>${topic.content}</DIV>
							<DIV class="tipad gray">
								发表：[<fmt:formatDate value="${topic.publishtime}" pattern="yyyy-MM-dd HH:mm:ss"/>] &nbsp;
								最后修改:[<fmt:formatDate value="${topic.modifytime}" pattern="yyyy-MM-dd HH:mm:ss"/>]
							</DIV>
						</TH>
					</TR>
				</TABLE>
			</DIV>
			<hr color="blue">
			<h4>回复 </h4>
	<c:if test="${replyList==null }">
		<h3>暂无回复</h3>
	</c:if>
	<c:if test="${replyList!=null }">
	
		<c:forEach items="${replyList }" var="reply" varStatus="status">
			<!--  回复        -->
			 
			<DIV class="t">
				<TABLE style="BORDER-TOP-WIDTH: 0px; TABLE-LAYOUT: fixed" cellSpacing="0" cellPadding="0" width="100%">
					<TR class="tr1">
						
						<TH style="WIDTH: 20%">
							<B>${reply.uname }</B><BR/><BR/>
							<c:if test="${fn:length(topic.head) < 15 }">
								<image src="image/head/${topic.head }"/>
							</c:if>
							<c:if test="${fn:length(topic.head) >= 15 }">
								<image src="image/upload/head/${topic.head }"/>
							</c:if><BR/>
							注册:<fmt:formatDate value="${reply.regtime }" pattern="yyyy-MM-dd"/><BR/>
						</TH>
						
						<TH>
							<H4>${reply.title }</H4> 
							
							<p id="${reply.replyid}" style="float:right"></p>
						<c:if test="${user!=null}">
							<%-- <p id="${status.index}" style="float:right"></p>
							<a id="zan" total="1" style="float:right" 
							href="javascript:replyglk(${reply.replyid},${status.index})">点赞</a> --%>
							
							<a id="zan" total="1" style="float:right" href="javascript:replyglk(${reply.replyid})" >
							<img  id="likephoto" src="image/like.png">
							</a>
						</c:if>
						<c:if test="${user==null}">
							<a id="zan" total="1" style="float:right" href="javascript:void(0);)" >
							<img  id="likephoto" src="image/like.png">
							</a>
						</c:if>
							
							
							<DIV>${reply.content }</DIV>
							<DIV class="tipad gray">
								发表：[<fmt:formatDate value="${reply.publishtime }" pattern="yyyy-MM-dd HH:mm:ss"/>] &nbsp;
								最后修改:[<fmt:formatDate value="${reply.modifytime }" pattern="yyyy-MM-dd HH:mm:ss"/>]
								
							<c:if test="${user.uid==reply.uid }">
								<A href="topic.s?op=deleteReply&replyid=${reply.replyid }&topicid=${reply.topicid}" onClick="return confirm('您确定删除吗?');">[删除]</A>
								<A href="">[修改]</A>
							</c:if>
							</DIV>
						</TH>
						
					</TR>
				</TABLE>
			</DIV>
		</c:forEach>
	</c:if>
		<!-- 声明        -->
		<BR>
		<CENTER class="gray">源辰信息</CENTER>
	</BODY>
</HTML>

