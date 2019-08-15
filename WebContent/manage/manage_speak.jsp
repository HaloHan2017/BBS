<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html  style="height:100%">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>BBS后台管理</title>
		<!-- links.jsp -->
		<%@ include file="../links.jsp" %>
		
	</head>
	<body id="cc" class="easyui-layout" style="width:100%;height:100%;">
	    <!-- <div data-options="region:'north'" style="height:100px;"> </div>  -->
			
	    <div id="mainPanel" data-options="region:'center',title:'主操作区'" 
	    	style="padding:5px;background:#eee;height:100%;" >
	    	<%-- <a href="${pageContext.request.contextPath }/sensitive.s?op=">添加敏感词</a> | 
	    	<a href="${pageContext.request.contextPath }/sensitive.s?op=">删除敏感词</a>	<br/> --%>
			<table id="dgSW" class="easyui-datagrid" style="width:100%;height:100%" toolbar="#toolbarSW" pagination="true"
			    data-options="url:'${pageContext.request.contextPath }/sensitive.s?op=queryAllSensitiveWord',fitColumns:true,singleSelect:true">
			    <thead>
					<tr>
						<th data-options="field:'sid',width:100" field="sid">敏感词编号</th>
					  	<th data-options="field:'sname',width:100" field="sname">敏感词</th> 
					  	<th data-options="field:'saddtime',width:100" field="saddtime" formatter="AddtimeDisplay">添加时间</th>
				  	</tr>
			    </thead>
			</table>
			
			<!-- 工具条 -->
			<div id="toolbarSW">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addSW()">添加敏感词</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="delSW()">删除敏感词</a>
				<!-- 查询工具 -->
				<!-- <input id="sname" style="line-height:26px;border:1px solid #ccc">
				<a href="#" iconCls="icon-search" class="easyui-linkbutton" plain="true" onclick="doSearch()">查找</a>
			 -->
			</div>
			
			<!-- 弹出添加面板 -->
			<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
					closed="true" buttons="#dlg-buttons">
				<div class="ftitle">添加敏感词</div>
				<form id="fm" method="post" novalidate>
					<div class="fitem">
						<input id="word" name="sname" class="easyui-textbox" required="true">
					</div>
				</form>
			</div>
			<div id="dlg-buttons">
				<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveSW()" style="width:90px">添加</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
			</div>
		</div>
		
		<script type="text/javascript">
			
			//查询敏感词  在表格中进行显示
			function doSearch(){
				if(!"".equals($('#sname').val().trim())){
					//输入不为空
					$('#dgSW').datagrid('reload');
				}
			} 
			var url;
			//添加面板
			function addSW(){
				$('#dlg').dialog('open').dialog('setTitle','添加敏感词');
				$('#fm').form('clear');
				url = '${pageContext.request.contextPath }/sensitive.s?op=addSW';
			}
			
			//确定添加敏感词
			function saveSW(){
				$('#fm').form('submit',{
					url: url,
					onSubmit: function(){
						return $(this).form('validate');
					},
					success: function(result){
						console.info(result);
						result = eval('('+result+')');
						console.info(result);
						if (result.code=='0'){
							$.messager.alert('错误提示',result.msg,'error');
							/* $.messager.show({
								title: '错误提示',
								msg: result.msg
							}); */
						} else if(result.code=='1') {
							$.messager.alert('提示',result.msg,'info');
							$('#dlg').dialog('close');		// close the dialog
							$('#dgSW').datagrid('reload');	// reload the user data
						}
					}
				});
			}
			function delSW(){
				var row = $('#dgSW').datagrid('getSelected');
				if (row){
					$.messager.confirm('删除敏感词','确认删除该敏感词?',function(r){
						if (r){
							$.post('${pageContext.request.contextPath }/sensitive.s?op=delSW',{sid:row.sid,sname:row.sname},function(result){
								result = eval('('+result+')');
								if (result.code=='1'){
									$.messager.alert('提示',result.msg,'info');
									$('#dgSW').datagrid('reload');	// reload the user data
								} else {
									$.messager.alert('错误提示',result.msg,'error');
									/* $.messager.show({	// show error message
										title: '错误提示',
										msg: result.msg
									}); */
								}
							},'json');
						}
					});
				}
			}
			
			function AddtimeDisplay(value,row,index){
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