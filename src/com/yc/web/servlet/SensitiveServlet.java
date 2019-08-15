package com.yc.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.bean.PageBean;
import com.yc.service.SensitiveService;
import com.yc.service.ServiceException;
import com.yc.utils.MyUtils;

@WebServlet("/sensitive.s")
public class SensitiveServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;
    private SensitiveService ss=new SensitiveService();
    
    //获取所有用户的违规发言次数列表
	public void queryAllSensitiveWord(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		//获取easyui的当前显示页数page  和   每页显示数量rows
		int rows = Integer.parseInt(request.getParameter("rows"));
		int page = Integer.parseInt(request.getParameter("page"));
		//获取输入查询的敏感词
		String sname = request.getParameter("sname");
		if(sname==null || "".equals(sname)){
			sname="";
		}
		
		//创建PageBean对象，将获取的页码 和 页大小
		PageBean pageBean=new PageBean<>();
		//设置每页显示数  和   当前页数
		pageBean.setRows(rows);
		pageBean.setPage(page);
		int total = ss.getTotalCount(sname);
		pageBean.setTotal(total);
		//判断是sname自动判断  全查询  还是  条件查询
		String swList=ss.query(pageBean,sname);
		
		//将数据  发送到前台
		response.setContentType("text/html;charset=UTF-8;");
		response.getWriter().append(swList);
	}
	
	//添加敏感词
	public void addSW(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String word = request.getParameter("sname");
//		PrintWriter out = MyUtils.getPrintWriter(response);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String res="";
		try {
			ss.addWord(word);
			res="{'code':'1','msg':'添加成功！'}";
//			out.append(res);
//			MyUtils.outJsonString(response, res);
		} catch (ServiceException e) {
			e.printStackTrace();
			res="{'code':'0','msg':'"+e.getMessage()+"'}";
//			MyUtils.outJsonString(response, res);
//			out.append(res);
		}
		out.println(res);
	}
	
	//删除敏感词
	public void delSW(HttpServletRequest request,HttpServletResponse response) throws IOException {
		int sid = Integer.parseInt(request.getParameter("sid"));
		String sname = request.getParameter("sname");
//		PrintWriter out = MyUtils.getPrintWriter(response);
		String res="";
		try {
			ss.delWord(sid,sname);
			res="{'code':'1','msg':'删除成功！'}";
			MyUtils.outJsonString(response, res);
//			out.append(res);
		} catch (ServiceException e) {
			e.printStackTrace();
			res="{'code':'0','msg':'"+e.getMessage()+"'}";
			MyUtils.outJsonString(response, res);
//			out.append(res);
		}
	}
}
