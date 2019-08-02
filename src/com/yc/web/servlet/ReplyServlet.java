package com.yc.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.yc.bean.LikeModel;
import com.yc.bean.TblReply;
import com.yc.bean.TblUser;
import com.yc.service.ReplyService;
import com.yc.utils.DBUtil;
import com.yc.utils.MyUtils;

/**
 * 回帖的点赞
 */
@WebServlet("/reply.s")
public class ReplyServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private ReplyService rs = new ReplyService();
	
	//获取帖子点赞数
	public void glktimes(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TblReply reply = MyUtils.mapToJavaBean(request.getParameterMap(), TblReply.class);
		Long count = rs.gettimes(reply);//获取帖子的点赞数//回帖列表的点赞数
		//int countList[] = 								 
		LikeModel lm = new LikeModel();
		lm.setCode(1);
		lm.setObj(count+"");
		super.outJsonString(lm, response);
	}
	

	//帖子的点赞
	public void glk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LikeModel lm = new LikeModel();
		TblReply reply = MyUtils.mapToJavaBean(request.getParameterMap(), TblReply.class);
		HttpSession session = request.getSession();
		TblUser user = (TblUser) session.getAttribute("user");
		reply.setUid(user.getUid());
		Long count = rs.glkreply(reply);
		lm.setCode(1);
		lm.setObj(count);
		super.outJsonString(lm, response);
	}
	
	//回帖的点赞
	public void replyglk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LikeModel lm = new LikeModel();
		TblReply reply = MyUtils.mapToJavaBean(request.getParameterMap(), TblReply.class);
		HttpSession session = request.getSession();
		TblUser user = (TblUser) session.getAttribute("user");
		reply.setUid(user.getUid());
		Long count = rs.num(reply);
		lm.setCode(1);
		lm.setObj(count);
		super.outJsonString(lm, response);
	}
	
	//回帖的点赞数查询
	public void replyLike(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String replyid = request.getParameter("replyid");
		TblReply reply = MyUtils.mapToJavaBean(request.getParameterMap(), TblReply.class);
		HttpSession session = request.getSession();
		TblUser user = (TblUser) session.getAttribute("user");
		if(user!=null){
			reply.setUid(user.getUid());
		}
		Long count = rs.counts(replyid);
		LikeModel lm = new LikeModel();
		lm.setCode(1);
		lm.setObj(count);
		super.outJsonString(lm, response);
		
		
	}


}
