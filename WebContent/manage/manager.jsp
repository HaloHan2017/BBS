<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html  style="height:100%">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>BBS后台管理</title>
		<!-- links.jsp -->
		<%@ include file="../links.jsp" %>
		<script type="text/javascript">
			function openUserTab(){
				//判断用户管理选项卡是否已经打开
				var tab=$("#mainPanel").tabs('getTab',"用户列表");
				if(tab==null){
					$("#mainPanel").tabs('add',
						{
							title:"用户列表",
							selected:true,
							closable:true,
							href:'manage_user.jsp'
						}		
					);
				}else{
					$("#mainPanel").tabs('select',"用户列表");
				}
			}
			//用户禁言 解禁用户
			function BanUserTab(){
				//判断用户管理选项卡是否已经打开
				var tab=$("#mainPanel").tabs('getTab',"用户发言");
				if(tab==null){
					$("#mainPanel").tabs('add',
						{
							title:"用户发言",
							selected:true,
							closable:true,
							href:'manage_banuser.jsp'
						}		
					);
				}else{
					$("#mainPanel").tabs('select',"用户发言");
				}
			}
			//---------------------------------------
			function openTopicTab(){
				//判断用户管理选项卡是否已经打开
				var tab=$("#mainPanel").tabs('getTab',"主题管理");
				if(tab==null){
					$("#mainPanel").tabs('add',
						{
							title:"主题管理",
							selected:true,
							closable:true,
							href:'manage_topic.jsp'
						}		
					);
				}else{
					$("#mainPanel").tabs('select',"主题管理");
				}
			}
			//-----------------------------------------
			
			function openLevelTab(){
				//判断用户管理选项卡是否已经打开
				var tab=$("#mainPanel").tabs('getTab',"等级列表");
				if(tab==null){
					$("#mainPanel").tabs('add',
						{
							title:"等级列表",
							selected:true,
							closable:true,
							href:'manage_level.jsp'
						}		
					);
				}else{
					$("#mainPanel").tabs('select',"等级列表");
				}
			}
			
			//-----------------------------------------
			function openSpeakTab(){
				//判断用户管理选项卡是否已经打开
				var tab=$("#mainPanel").tabs('getTab',"敏感词列表");
				if(tab==null){
					$("#mainPanel").tabs('add',
						{
							title:"敏感词列表",
							selected:true,
							closable:true,
							href:'manage_speak.jsp'
						}		
					);
				}else{
					$("#mainPanel").tabs('select',"敏感词列表");
				}
			}
			function addSWTab(){
				//判断用户管理选项卡是否已经打开
				var tab=$("#mainPanel").tabs('getTab',"添加敏感词");
				if(tab==null){
					$("#mainPanel").tabs('add',
						{
							title:"添加敏感词",
							selected:true,
							closable:true,
							href:'manage_addSW.jsp'
						}		
					);
				}else{
					$("#mainPanel").tabs('select',"添加敏感词");
				}
			}
		</script>
	</head>
	<body id="cc" class="easyui-layout" style="width:100%;height:100%;">
	    <!-- 顶部 -->
	    <div data-options="region:'north'" style="height:100px;">
	    	<img src="../image/yclogo.png">
	    </div>
    	
    	<!-- 左侧导航栏  手风琴  -->
    	<div id="navMenu" class="easyui-accordion" data-options="region:'west',title:'导航菜单',split:true" style="width:200px;height:30%;">
		    <div title="用户管理" data-options="iconCls:''" style="padding:10px;">
				<a onClick="openUserTab()" href="#" class="easyui-linkbutton" iconCls="" style="width:100%;height:32px">用户列表</a>
		    </div>
		    <div title="主题管理" data-options="iconCls:''" style="padding:10px;">
				<a onClick="openTopicTab();" href="#" class="easyui-linkbutton" iconCls="" style="width:100%;height:32px">主题列表</a>
		    </div>
		    <div title="等级管理" data-options="iconCls:''" style="padding:10px;">
		    	<a onClick="openLevelTab();" href="#" class="easyui-linkbutton" iconCls="" style="width:100%;height:32px">等级列表</a>
		    </div>
		    <div title="敏感词管理" data-options="iconCls:''" style="padding:10px;">
		    	<a onClick="openSpeakTab();" href="#" class="easyui-linkbutton" iconCls="" style="width:100%;height:32px">敏感词列表</a>
		    </div>
		</div>
			
		<!--主操作区  -->
	    <div id="mainPanel" data-options="region:'center',title:'主操作区'" style="padding:5px;background:#eee;" class="easyui-tabs" >
			
		</div>
		
		<!-- footer -->
		<div data-options="region:'south'"  style="font-family:黑体;text-align:center;height:30px;vertical-align:middle;">
	    	Copyright ©2019 YC-62
	    </div>
	</body>
</html>