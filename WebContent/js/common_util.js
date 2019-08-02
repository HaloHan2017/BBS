//点击发帖时   ajax判断能否是否被系统禁言（查询该用户的违规发言次数  是否 >=3）			
function canPost(op){
	$.post(
		"user.s?op=queryUserSWCount",
		{'uid':uid},
		function(data){
			var dataObj=eval("("+data+")");//
			if (dataObj.code!='1'){
				alert(dataObj.msg);
				if(dataObj.code=='-1'){
					//如果是用户没有登录，则跳转到login
					location.href="login.jsp";
				}
				return;
			} else {
				//可以发布帖子,根据op判断是跳到那个界面
				var path="";
				if(op=="post"){
					path="post.jsp?boardid="+boardid;
				}else{
					path="postreply.jsp?topicid="+topicid;
				}
				//var boardid='<%=request.getParameter("boardid")%>';
				location.href=path;
			}
		},'json'
	); 
}

//发帖和回帖    op区分两个操作
/*
 * 发帖和回帖  前台传递的参数
 * op  uid  boardid(回帖是topicid)  title  content
 * data 即表单参数序列化的数据
 * */

//$(function () {
//	 $('#but').click(function(){
//	 //需要手动更新CKEDITOR字段
//	 for ( instance in CKEDITOR.instances ) 
//	     CKEDITOR.instances[instance].updateElement(); 
//	 return true;
//});
function postTopic(op,data){

	$.post(
		"topic.s?op="+op,
		data,
		function(data){
			var dataObj=eval("("+data+")");//
			if (dataObj.code==0){
				//发布失败
				alert(dataObj.res);
			} else {
				//发布成功，若包含敏感词，则提示
				if(dataObj.res!="" && dataObj.res!=null){
					alert(dataObj.res);
				}
				var path="";
				//跳转到对应界面
				if(op=="post"){
					//跳转到topic.s?op=list 
					path="topic.s?op=list&boardid="+dataObj.boardid+"&boardname="+dataObj.boardname;
				}else if(op=="postreply"){
					//跳转到topic.s?op=detail
					path="topic.s?op=detail&topicid="+dataObj.topicid;
				}
				location.href=path;
			}
		},'json'
	); 
}