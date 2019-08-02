package com.yc.web.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.bean.LikeModel;
import com.yc.bean.LikeTopic;
import com.yc.bean.TblUser;
import com.yc.dao.LikeDao;
import com.yc.service.LikeService;
import com.yc.utils.MyUtils;

/**
 * Servlet implementation class LikeServlet
 */
@WebServlet("/like.s")
public class LikeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private LikeService ls = new LikeService();
	private LikeDao ld = new LikeDao();
	
    
	public void addlikes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LikeTopic like = MyUtils.mapToJavaBean(request.getParameterMap(), LikeTopic.class);
		HttpSession session = request.getSession();
		TblUser user = (TblUser) session.getAttribute("user");
		like.setUid(user.getUid());
		ls.findid(like);
		LikeTopic count = ld.index(like);
		LikeModel lm = new LikeModel();
		lm.setCode(1);
		lm.setObj(count);
		super.outJsonString(lm, response);
	}

	//查询是否收藏
	public void alllike(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LikeTopic like = MyUtils.mapToJavaBean(request.getParameterMap(), LikeTopic.class);
		HttpSession session = request.getSession();
		TblUser user = (TblUser) session.getAttribute("user");
		if(user!=null){
			like.setUid(user.getUid());
		}	
		
		LikeTopic count = ld.index(like);
		LikeModel lm = new LikeModel();
		lm.setCode(1);
		lm.setObj(count);
		super.outJsonString(lm, response);
		
		
	}

}
