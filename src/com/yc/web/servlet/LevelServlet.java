package com.yc.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yc.bean.TblLevel;
import com.yc.service.LevelService;
import com.yc.service.ServiceException;
import com.yc.utils.MyUtils;

@WebServlet("/level.s")
public class LevelServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	private LevelService ls=new LevelService();
	
	//查询所有等级列表
	public void queryAllLevel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//调用ls的查询方法
		List<TblLevel> levelList = ls.queryAllLevel();
		//已json的格式传输到前台页面
		String json = JSON.toJSONString(levelList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().append(json);
	}
	
	//添加等级
	public void addLevel(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, IOException {
		//获取level等级的所有字段(时间需要转换成long)
		Map<String, String[]> map = request.getParameterMap();
		TblLevel level = MyUtils.mapToJavaBean(map, TblLevel.class);
		//调用serivce层的添加等级方法
		String result = null;
		try {
			ls.addLevel(level);
			result="{'result':1,'msg':'添加成功'}";
			MyUtils.outJsonString(response, result);
		} catch (ServiceException e) {
			e.printStackTrace();
			result="{'result':0,'msg':'"+e.getMessage()+"'}";
			MyUtils.outJsonString(response, result);
		}
	}
	
	//删除
	public void deleteLevel(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//获取要删除的level id
		int lid = Integer.parseInt(request.getParameter("lid"));
		//调用serivce层的删除等级方法
		String result = null;
		try {
			ls.deleteLevel(lid);
			result="{'result':1,'msg':'删除成功'}";
			MyUtils.outJsonString(response, result);
		} catch (ServiceException e) {
			e.printStackTrace();
			result="{'result':0,'msg':'删除失败！'}";
			MyUtils.outJsonString(response, result);
		}
	}
	
	//修改方法
	public void mofifyLevel(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, IOException{
		//获取level等级的所有字段(时间需要转换成long)
		Map<String, String[]> map = request.getParameterMap();
		TblLevel level = MyUtils.mapToJavaBean(map, TblLevel.class);
		System.out.println(level);
		//调用serivce层的修改等级方法
		String result = null;
		try {
			ls.mofifyLevel(level);
			result="{'result':1,'msg':'修改成功'}";
			MyUtils.outJsonString(response, result);
		} catch (ServiceException e) {
			e.printStackTrace();
			result="{'result':0,'msg':'"+e.getMessage()+"'}";
			MyUtils.outJsonString(response, result);
		}
	}
}
