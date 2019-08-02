<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			<table class="easyui-datagrid" style="width:100%;height:100%" pagination="true"
			    data-options="url:'${pageContext.request.contextPath }/topic.s?op=queryAllTopic',fitColumns:true,singleSelect:true">
			    <thead>
					<tr>
						<th data-options="field:'topicid',width:50">编号</th>
						<th data-options="field:'title',width:100">标题</th>
						<th data-options="field:'content',width:100">内容</th>
						<th data-options="field:'publishtime',width:100,formatter:TimeDisplay">发布时间</th>
						<th data-options="field:'modifytime',width:100,formatter:TimeDisplay" >修改时间</th>
						<th data-options="field:'uid',width:30" >用户编号</th>
						<th data-options="field:'boardid',width:30" >版块编号</th>
					</tr>
			    </thead>
			</table>
		</div>
		
		<script type="text/javascript">
		function TimeDisplay(value,row,index){
			var date=new Date(value);
			return dateFtt("yyyy-MM-dd hh:mm:ss",date);
		/* 	var year=date.getFullYear();
			var month=date.getMonth()+1;
			var day=date.getDate();
			return year+"-"+month+"-"+day; */
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