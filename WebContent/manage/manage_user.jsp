<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html  style="height:100%">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>BBS后台管理</title>
		<!-- links.jsp -->
		<%@ include file="../links.jsp" %>
		
	</head>
	<body id="cc" class="easyui-layout" style="width:100%;height:100%;">
		<div id="mainPanel" data-options="region:'center',title:'主操作区'" 
	    	style="padding:5px;background:#eee;height:100%;" >
			<table id="dg" class="easyui-datagrid" style="width:100%;height:100%" toolbar="#toolbar" pagination="false"
			    data-options="url:'${pageContext.request.contextPath }/user.s?op=queryAllUser',fitColumns:true,singleSelect:true">
			    <thead>
			    	<tr>
						<th data-options="field:'uid',width:50">编号</th>
						<th data-options="field:'uname',width:50">姓名</th>
						<th data-options="field:'gender',width:50" formatter="GenderDisplay">性别</th>
						<th data-options="field:'head',width:50" formatter="HeadDisplay">头像</th>
						<th data-options="field:'regtime',width:50" formatter="RegtimeDisplay">注册时间</th>
						<th data-options="field:'phone',width:50">手机</th>
						<th data-options="field:'email',width:50">邮箱</th>
						<th data-options="field:'swcount',width:50">违规发言次数</th>
					</tr>
			    </thead>
			</table>
			
			<!-- 工具条 -->
			<div id="toolbar">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-no" plain="true" onclick="BanUser()">禁言</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="UnbanUser()">解除禁言</a>
			</div>
			
		</div>
		
		
		<script type="text/javascript">
			//禁言用户
			function BanUser(){
				var row = $('#dg').datagrid('getSelected');
				if (row){
					$.messager.confirm('禁言','确定禁言该用户?',function(r){
						if (r){
							$.post('${pageContext.request.contextPath }/user.s?op=banUser',
									{uid:row.uid}, function(data){
										var dataObj=eval("("+data+")");
										/* if(dataObj.code!=1){
											//禁言失败
											$.messager.alert('错误提示',dataObj.msg);
										}else{
											//禁言成功
											$.messager.alert('提示',dataObj.msg);
											$('#dg').datagrid('reload');	// reload the user data
										} */
										$('#dg').datagrid('reload');
							},'json');
						}
					});
				}
			}
			//解除禁言
			function UnbanUser(){
				var row = $('#dg').datagrid('getSelected');
				if (row){
					$.messager.confirm('解除禁言','确定解除该用户的禁言?',function(r){
						if (r){
							$.post('${pageContext.request.contextPath }/user.s?op=UnbanUser',
									{uid:row.uid},function(data){
										var dataObj=eval("("+data+")");
										/* if(dataObj.code!=1){
											//禁言失败
											$.messager.alert('错误提示',dataObj.msg);
										}else{
											//禁言成功
											$.messager.alert('提示',dataObj.msg);
											$('#dg').datagrid('reload');	// reload the user data
										} */
										$('#dg').datagrid('reload');
							},'json');
						}
					});
				}
			}
			
			var path="${pageContext.request.contextPath }";
			//性别字段转换函数  0--》女
			function GenderDisplay(value,row,index){
				if(value=='1'){
					return '男';
				}else{
					return '女';
				}
			}
			function HeadDisplay(value,row,index){
				if(value.length>=15){
					return '<img src="'+path+'/image/upload/head/'+value+'"/>';
				}
				return '<img src="'+path+'/image/head/'+value+'"/>';
			}
			function RegtimeDisplay(value,row,index){
				var date=new Date(value);
				return dateFtt("yyyy-MM-dd hh:mm:ss",date);
			}
			function dateFtt(fmt,date)   
			{ //author: meizz   
			  var o = {   
			    "M+" : date.getMonth()+1,                 //月份   
			    "d+" : date.getDate(),                    //日   
			    "h+" : date.getHours(),                   //小时   
			    "m+" : date.getMinutes(),                 //分   
			    "s+" : date.getSeconds(),                 //秒   
			    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
			    "S"  : date.getMilliseconds()             //毫秒   
			  };   
			  if(/(y+)/.test(fmt))   
			    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
			  for(var k in o)   
			    if(new RegExp("("+ k +")").test(fmt))   
			  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
			  return fmt;   
			}
		</script>
	</body>
</html>