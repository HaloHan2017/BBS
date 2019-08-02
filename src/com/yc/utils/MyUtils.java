package com.yc.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import javax.print.attribute.ResolutionSyntax;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;



/**
 * 工具类
 * @author Administrator
 *
 */
public class MyUtils {
	/**
	 * MD5加密
	 * @param inStr
	 * @return
	 */
	public static String parseMD5(String inStr){
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("密码加密失败");
		}
		char[] charArr = inStr.toCharArray();
		byte[] byteArr = new byte[charArr.length];
		for(int i=0;i<charArr.length;i++){
			byteArr[i] = (byte) charArr[i];
		}
		
		byte[] md5Bytes = md5.digest(byteArr);
		
		StringBuilder hexValue = new StringBuilder();
		
		for(int i=0;i<md5Bytes.length;i++){
			int val = (int)md5Bytes[i] & 0xff;
			if(val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		
		return hexValue.toString();
	}
	
	//将Map<String,String[]>转换为javabean
	public static<T> T mapToJavaBean(Map<String,String[]> mapStrArr,Class<T> t){
		//把Map<String,String[]>转成Map<String,Object>
		Set<Entry<String,String[]>> entrySet=mapStrArr.entrySet();
		Map<String,String> map=new HashMap<>();
		for (Entry<String, String[]> entry : entrySet) {
			map.put(entry.getKey(), entry.getValue()[0]);
		}
		//再使用fastjson转为javabean
		String jsonStr=JSON.toJSONString(map);
		JSONObject json=JSONObject.parseObject(jsonStr);
		return JSON.toJavaObject(json,t);	
	}
	
	/**
	 * 借助于fastJSON工具把List<Map>转List<javaBase>对象
	 * @param mList DBHelper工具从数据库查询返回的对象
	 * @param t	javaBase的类型
	 * @return
	 */
	public static<T> List<T> ListMapToJavaBean(List<Map<String, Object>> mList,Class<T> t) {
		List<T> tList = new ArrayList<T>();
		for (Map<String, Object> map : mList) {
			 //把map对象转成json字符串
			String strJson = JSON.toJSONString(map);
			//把json字符串转成JSONObject对象
			JSONObject json = JSONObject.parseObject(strJson);
			//把JSONObject对象转成javaBean并添加到集合
			tList.add(JSON.toJavaObject(json, t));
		}
		return tList;
	}
	
	/**
	 * 验证功能
	 * @param session
	 * @return
	 */
	public static BufferedImage createImageCode(HttpSession session) {
	    //创建图片缓冲对象
	    BufferedImage bi = new BufferedImage(68, 22,BufferedImage.TYPE_INT_RGB);
	    //通过图片缓冲对象获取画笔
	    Graphics g = bi.getGraphics();
	    //设置画笔颜色(默认是白色)
	    g.setColor(new Color(200,188,255));
	    //把颜色填充到画布指定位置和大小
	    g.fillRect(0,0,68,22);

	    //创建一个随机对象
	    Random r = new Random();
	    //随机产生干扰线，使图象中的认证码不易被其它程序探测到。   
	    g.setColor(Color.BLACK);   
	    for (int i = 0; i < 50; i++) {
	        int x = r.nextInt(68);   
	        int y = r.nextInt(22);   
	        int xl = r.nextInt(5);   
	        int yl = r.nextInt(5);
	        g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
	        //x,y 为起点的坐标,x + xl, y + yl为终点的坐标
	        g.drawLine(x, y, x + xl, y + yl);   
	    }   


	    //准备一个字符数组（用于随机生成的验证码字符）
	    char[] c = "1234567890".toCharArray();
	    //用于存放生成的验证码字符
	    StringBuffer s = new StringBuffer();
	    //随机生成4个字符
	    for(int i = 0,len = c.length;i < 4;i++) {
	        //获取随机下标
	        int index = r.nextInt(len);
	        //设置画笔颜色
	        g.setColor(new Color(r.nextInt(80),r.nextInt(150),r.nextInt(200)));
	        //把字符画到画布指定的位置
	        g.drawString(c[index]+"",4+i*15,18);
	        //把生成的字符存到字符串缓冲区备用
	        s.append(c[index]);
	    }
	    session.setAttribute("vfcode",s.toString());
	    return bi;
	}
	//邮箱验证码
	public static String createEmailCode(HttpSession session) {
		//创建一个随机对象
	    Random r = new Random();
		//准备一个字符数组（用于随机生成的验证码字符）
	    char[] c = "1234567890".toCharArray();
	    //用于存放生成的验证码字符
	    StringBuffer s = new StringBuffer();
	  //随机生成6个字符
	    for(int i = 0,len = c.length;i < 6;i++) {
	    	//获取随机下标
	        int index = r.nextInt(len);
	      //把生成的字符存到字符串缓冲区备用
	        s.append(c[index]);
	    }
	    //将验证码存到session中
	    session.setAttribute("emailcode",s.toString());
	    //设置120s失效
	    session.setMaxInactiveInterval(2*60);
		return s.toString();
		
	}
	
	//上传头像
	//property 要上传的属性名   uploadPath存放在服务器的地址
	public static Map<String,Object> upload(HttpServletRequest request,String property,String uploadPath,String virtualPath){
		//收集数据的容器
		Map<String,Object> map = new HashMap<String,Object>();
				
		try {
			//创建磁盘文件项工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//创建文件上传核心对象
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解析request获得文件项对象集合
			List<FileItem> parseRequest = upload.parseRequest(request);
			
			for (FileItem item : parseRequest) {
				//判断是否是普通表单项
				boolean formField = item.isFormField();
				if(formField){
					//普通表单项 获得表单的数据 封装到TblUser实体中
					String fieldName = item.getFieldName();
					String fieldValue = item.getString("UTF-8");
					
					map.put(fieldName, fieldValue);
				}else{
					//文件上传项 获得文件名称 获得文件的内容
					String fileName = item.getName();
					//文件重新命名   防止文件重名
					String newFileName ="";
					//如果用户选择的是  系统自带头像   则无需进行文件io操作
					if(fileName!=null && !"".equals(fileName)){
						//文件重新命名   防止文件重名
						newFileName = MyUtils.getNewFileName(fileName);
						InputStream in = item.getInputStream();
						OutputStream out = new FileOutputStream(uploadPath+"/WebContent/image/upload/head"+"/"+newFileName);//I:/xxx/xx/xxx/xxx.jpg
						IOUtils.copy(in, out);
						in.close();
						out.close();
						item.delete();
						
						//存的时候，
						map.put(property,newFileName);
					}
				}
			}
		} catch (FileUploadException | IOException e) {
			System.out.println("File Error!");
			e.printStackTrace();
		}
		return map;
	} 
	
	//使用uuid为文件重新命名
	public static String getNewFileName(String oldFileName) {
		StringBuffer newFileName=new StringBuffer();
		//获取文件拓展名
		String extensions = oldFileName.substring(oldFileName.lastIndexOf("."));
		//产生新文件名（uuid）
		newFileName.append(UUID.randomUUID().toString().replaceAll("-", ""));
		//加上拓展名
		newFileName.append(extensions);
		return newFileName.toString();
	}
	
	//上传头像  简易写法
	public static String uploadFile(HttpServletRequest request,String prop) throws IOException, ServletException{
		Part part=request.getPart(prop);
		String submittedFileName = part.getSubmittedFileName();
		if(submittedFileName!=null && !"".equals(submittedFileName)){
			String newFileName=UUID.randomUUID()+submittedFileName;//文件改名，防止文件重名被覆盖
			//String path="C:/bbs/uploadFile";//保存到服务器的那个位置(写在配置文件里)
			String path=request.getServletContext().getInitParameter("path");
			//保存图片
			part.write(path+"/"+newFileName);
			//保存到数据库的路径应该是虚拟路径，因为客户端无法直接访问真实路径
			return newFileName;
		}
		return null ;
	}
	
	//返回json对象
	public static void outJsonString(HttpServletResponse response,Object obj ) throws IOException{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String jsonString = JSON.toJSONString(obj);
		out.print(jsonString);
		out.flush();//表示强制将缓冲区中的数据发送出去，不必等到缓冲区满
	}
	public static void outJsonString(HttpServletResponse response,String str ) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String jsonString = JSON.toJSONString(str);
		out.print(jsonString);
		out.flush();//表示强制将缓冲区中的数据发送出去，不必等到缓冲区满
	}
	public static PrintWriter getPrintWriter(HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		return out;
		
	}
}
