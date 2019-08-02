package com.yc.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * 基本servlet
 */

public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取op字段（要进行什么操作）
		String op=request.getParameter("op");
		//2.根据op的值调用对应的方法（反射技术）
		Class<?>[] clazz=new Class<?>[]{HttpServletRequest.class,HttpServletResponse.class};
		//3.获取op对应的方法
		try {
			Method method = this.getClass().getMethod(op, clazz);
			//通过反射调用方法
			method.invoke(this, request,response);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	//将request里的Map参数集合封装成JavaBean
	public Object parseParameterToT(HttpServletRequest req, Class class1) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		//将  req中的    Map<String,String[]>转成了   Map<String,String>
		Map<String,String[]> map=req.getParameterMap();
		Map<String,String> parameters=new HashMap<String,String>();
		for(   Map.Entry<String, String[]> entry: map.entrySet()) {
			parameters.put(entry.getKey(), entry.getValue()[0]);
		}
		//  parameters    uname     pwd
		List<Method> listmethods=findAllSetMethods(class1   );
		Object obj=class1.newInstance();   //   new Person();
		for(   Method m: listmethods) {   //  setuname    setpwd
			for(  Map.Entry<String, String> entry: parameters.entrySet()  ) {  // uname,    pwd
				String methodName=m.getName();   // setUname
				String pName="set"+entry.getKey();   // setuname
				if(   methodName.equalsIgnoreCase(pName)) {
					//TODO: 求出    m  这个方法中的参数类型, 强制类型转换    entry.getValue()的类型.
					Class parameterType=m.getParameterTypes()[0];
					String parameterTypeName=parameterType.getName();
					if(  "int".equals(parameterTypeName)  || "java.lang.Integer".equals(parameterTypeName)) {
						int v=Integer.parseInt(    entry.getValue());
						m.invoke(obj, v); 
					}else if(  "short".equals(parameterTypeName)  || "java.lang.Short".equals(parameterTypeName)) {
						short v=Short.parseShort(    entry.getValue());
						m.invoke(obj, v); 
					}else if(  "float".equals(parameterTypeName)  || "java.lang.Float".equals(parameterTypeName)) {
						float v=Float.parseFloat(    entry.getValue());
						m.invoke(obj, v); 
					}else if(  "double".equals(parameterTypeName)  || "java.lang.Double".equals(parameterTypeName)) {
						double v=Double.parseDouble(    entry.getValue());
						m.invoke(obj, v); 
					}else {
						//m就是要找的方法
						m.invoke(obj, entry.getValue());       //  person.setUname(   "zy" );
					}
				}
			}
		}
		return obj;
	}
	/**
	 * 取出所有的set方法
	 * @param clz
	 * @return
	 */
	private List<Method> findAllSetMethods(  Class clz  ){
		List<Method> list=new ArrayList<Method>();
		Method[] ms=clz.getMethods();
		for(   Method m:ms) {
			if(   m.getName().startsWith("set")) {
				list.add(  m );
			}
		}
		return list;
	}
	// 将一个对象以json字符串的格式输出客户端
		protected void outJsonString(Object obj, HttpServletResponse response) throws IOException {
			// 利用gson将JsonModel对象转为一个json字符串
			String jsonString = JSON.toJSONString(obj);
			outJsonString(jsonString, response);
		}

		protected void outJsonString(  String jsonString, HttpServletResponse response) throws IOException {
			// 利用gson将JsonModel对象转为一个json字符串
			response.setContentType("text/json;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jsonString);
			out.flush();
		}
}
