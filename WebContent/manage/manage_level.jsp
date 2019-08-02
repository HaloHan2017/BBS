<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html  style="height:100%">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>BBS后台管理</title>
		<!-- links.jsp -->
		<%@ include file="../links.jsp" %>
		<style type="text/css">
			#fm{
				margin:0;
				padding:10px 30px;
			}
			.ftitle{
				font-size:14px;
				font-weight:bold;
				padding:5px 0;
				margin-bottom:10px;
				border-bottom:1px solid #ccc;
			}
			.fitem{
				margin-bottom:5px;
			}
			.fitem label{
				display:inline-block;
				width:80px;
			}
			.fitem input{
				width:160px;
			}
		</style>
	</head>
	<body id="cc" class="easyui-layout" style="width:100%;height:100%;">
		<!-- 主面板  -->
	    <div id="mainPanel" data-options="region:'center',title:'主操作区'" 
	    	style="padding:5px;background:#eee;height:100%;" >
			<table id="dg" class="easyui-datagrid" style="width:100%;height:100%" toolbar="#toolbar" rownumbers="true" 
				data-options="url:'${pageContext.request.contextPath }/level.s?op=queryAllLevel',fitColumns:true,singleSelect:true">
			    <thead>
					<tr>
						<th data-options="field:'lid',width:50" field="lid">编号</th>
						<th data-options="field:'lname',width:50" field="lname">等级</th>
						<th data-options="field:'ltime',width:100,formatter:LTimeDisplay" field="ltime">注册时长</th>
						<th data-options="field:'ltopicnum',width:100" field="ltopicnum">主题数量</th>
						<th data-options="field:'lreplynum',width:100" field="lreplynum">回复数量</th>
					</tr>
			    </thead>
			</table>
		</div>
		<div id="toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addLevel()">添加等级</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editLevel()">修改等级</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyLevel()">删除等级</a>
		</div>
		
		<!-- 弹出添加对话框 -->
		<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons">
		<div class="ftitle">添加等级信息</div>
		
		<!-- 表单（添加，删除） -->
		<form id="fm" method="post" >
			<div class="fitem">
				<label>等级id:</label>
				<input name="lid" readonly="readonly">
			</div>
			<div class="fitem">
				<label>等级名:</label>
				<input name="lname" required="true">
			</div>
			<div class="fitem">
				<label>注册时长:</label>
				<select id="times" class="easyui-combobox" name="ltime" style="width:200px;">
				    <option value="0">默认</option>
				    <option value="1296000">15天</option>
				    <option value="2592000">30天</option>
				    <option value="5184000">60天</option>
				    <option value="7776000">90天</option>
				    <option value="15552000">180天</option>
				    <option value="31104000">一年</option>
				    <option value="62208000">两年</option>
				    <option value="155520000">五年</option>
				</select>
			</div>
			<div class="fitem">
				<label>主题数量:</label>
				<input name="ltopicnum" >
			</div>
			<div class="fitem">
				<label>回复数量:</label>
				<input name="lreplynum" >
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveLevel()">确定</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
		
		<script type="text/javascript">
			var url;
			//添加等级（弹出对话框）
			function addLevel(){
				$('#dlg').dialog('open').dialog('setTitle','添加等级');
				//$('#fm').form('clear');
				$("input[name='lid']").remove();
				$("input[name='lname']").val('');
				$("input[name='ltime']").val('');
				$("input[name='ltopicnum']").val('');
				$("input[name='lreplynum']").val('');
				url = '${pageContext.request.contextPath }/level.s?op=addLevel';
			}
			//修改等级（弹出对话框）
			function editLevel(){
				var row = $('#dg').datagrid('getSelected');
				if (row){
					$('#dlg').dialog('open').dialog('setTitle','修改等级');
					$('#fm').form('load',row);
					url = '${pageContext.request.contextPath }/level.s?op=mofifyLevel';
				}
			}
			//添加等级（调用方法提交）
			function saveLevel(){
				$('#fm').form('submit',{
					url: url,
					onSubmit: function(){
						return $(this).form('validate');
					},
					success: function(data){
						var dataObj = eval('('+data+')');
						/* alert(dataObj);
						if (dataObj.result!=1){
							$.messager.alert('错误提示',dataObj.msg);
						} else {
							$('#dlg').dialog('close');		// close the dialog
							$('#dg').datagrid('reload');	// reload the user data
						} */
						$('#dlg').dialog('close');		// close the dialog
						$('#dg').datagrid('reload');	// reload the user data
					}
				});
			}
			//删除等级
			function destroyLevel(){
				var row = $('#dg').datagrid('getSelected');
				if (row){
					$.messager.confirm('删除等级','确定删除该等级信息？',function(r){
						if (r){
							$.post('${pageContext.request.contextPath }/level.s?op=deleteLevel',{lid:row.lid},function(result){
								//重新加载
								$('#dlg').dialog('close');	
								$('#dg').datagrid('reload');	// reload the user data
							},'json');
						}
					});
				}
			}
			//时间转换
			function LTimeDisplay(value,row,index){
				var time=value/(24*60*60);
				if(time==0){
					return "默认";
				}else if(time==15){
					return "15天";
				}else if(time==30){
					return "30天";
				}else if(time==60){
					return "60天";
				}else if(time==90){
					return "90天";
				}else if(time==180){
					return "180天";
				}else if(time==360){
					return "一年";
				}else if(time==720){
					return "两年";
				}else if(time==1800){
					return "五年";
				}
			}
			
		</script>
	</body>
</html>