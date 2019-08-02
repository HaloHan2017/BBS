package com.yc.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.yc.bean.TblReply;
import com.yc.bean.TblTopic;
import com.yc.bean.TblUser;
import com.yc.service.ServiceException;
import com.yc.service.TopicService;
import com.yc.utils.MyUtils;

/**
 * 主题servlet
 * 方法需要改成public，反射技术需要
 */
@WebServlet("/topic.s")
public class TopicServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    
	private TopicService ts=new TopicService();
	
	//查询所有topic列表（根据boardid）
	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取版块id
		String boardid_str = request.getParameter("boardid");
		String boardname = request.getParameter("boardname");
		Integer boardid= boardid = Integer.parseInt(boardid_str);
		//查询该版块的所有帖子
		List<Map<String, Object>> topicList=ts.findAllTopicList(boardid);
		//统计该主题下的回复数量
//		int replyCount=getTotalReplyCount();
		//将查询到的帖子数据，存到页面
		request.setAttribute("topicList", topicList);
		request.getSession().setAttribute("boardname", boardname);//添加版块名字（在导航栏显示,要存到session中）
		request.getSession().setAttribute("boardid", boardid);
		
		//request.setAttribute("boardid", boardid);
		//跳转界面
		request.getRequestDispatcher("/list.jsp").forward(request, response);
	}
	
	//添加新帖子
	public void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//把请求的参数  封装成一个TblTopic对象
		TblTopic topic=MyUtils.mapToJavaBean(request.getParameterMap(), TblTopic.class);
		String boardname = (String) request.getSession().getAttribute("boardname");
		//调用service层的业务方法
		try {	//创建成功
			String flag = ts.createNewTopic(topic);	
			StringBuffer str=new StringBuffer("");
			//发送结果到前台（要把boardid 和boardname 带过去）
			str.append("{'code':1,'boardid':"+topic.getBoardid()+",'boardname':'"+boardname+"'");
			if(flag.equals("yes")){//有敏感词，
				str.append(",'res':'系统检测到您的用词不文明，请注意您的言行举止！'}");
			}else{
				str.append("}");
			}
			MyUtils.outJsonString(response, str);
			
		} catch (ServiceException e) {//创建失败
			e.printStackTrace();
			String s="{'code':0,'res':'"+e.getMessage()+"'}";
			MyUtils.outJsonString(response, s);
		}
	}
	

	//查看帖子详情
	public void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//获取topicid
		Integer topicid=Integer.parseInt(request.getParameter("topicid"));
		//调用service方法，查到主题，查到回复列表
		TblTopic topic=ts.findTopicById(topicid);
		List<TblReply> replyList=ts.findReplyListByTopicid(topicid);
		
		List<Integer> replyidList=null;
		if(replyList!=null){
			replyidList = replyList.stream().map(TblReply::getReplyid).collect(Collectors.toList());
		}
		

		//将数据添加到请求
		request.setAttribute("replyidList", replyidList);
		request.setAttribute("topic",topic);
		request.setAttribute("replyList",replyList);
		//转发到detail.jsp
		request.getRequestDispatcher("/detail.jsp").forward(request, response);
	}
	
	//添加回复帖子
	public void postreply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//将参数封装成TblReply对象
		TblReply reply=MyUtils.mapToJavaBean(request.getParameterMap(),TblReply.class);
		Timestamp time=new Timestamp(System.currentTimeMillis());
		reply.setPublishtime(time);
		reply.setModifytime(time);
		//reply.setContent(request.getParameter("content"));
		//System.out.println(reply.toString());
		//调用service层的添加回复方法
		try {	//添加成功	
			String flag = ts.createNewReply(reply);
			String str="";
			//发送结果到前台（要把topicid   带过去）
			str="{'code':1,'topicid':'"+reply.getTopicid()+"'";
			if(flag.equals("yes")){//有敏感词，
				str+=",'res':'系统检测到您的用词不文明，请注意您的言行举止！'}";
			}else{
				//未包含敏感词
				str+="}";
			}
			MyUtils.outJsonString(response, str.toString());
		} catch (ServiceException e) {	//添加失败
			e.printStackTrace();
			String s="{'code':0,'res':'"+e.getMessage()+"'}";
			MyUtils.outJsonString(response, s);
		}
	}
	
	//删除回复
	public void deleteReply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//获得要删除的帖子id
		Integer replyid = Integer.parseInt(request.getParameter("replyid"));
		Integer topicid = Integer.parseInt(request.getParameter("topicid"));
		//调用service层的删除方法
		ts.deleteReplyById(replyid);
		//跳转到detail.jsp界面
		//request.getRequestDispatcher("/detail.jsp").forward(request, response);
		response.sendRedirect("topic.s?op=detail&topicid="+topicid);
	}
	//ajax查询所有主题
	public void queryAllTopic(HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<TblTopic> topicList=ts.queryAllTopic();
		String json = JSON.toJSONString(topicList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().append(json);
	}
	
	//查找收藏的帖子
	public void findLike(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		TblUser user = (TblUser) session.getAttribute("user");
		
	}
	
}
