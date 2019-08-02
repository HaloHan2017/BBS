package com.yc.web.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.yc.bean.TblUser;

/**
 * 单点登录
 *
 */
@WebListener
public class SingleLoginListener implements HttpSessionAttributeListener {

	private Map map;//容器，用于判断当前登录用户是否已登录（在别处）
	
    public SingleLoginListener() {
    	map=new HashMap<>();
    }
    
    public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	/**
     * 重写添加方法，用户登录成功后，将被存到session
     */
    public void attributeAdded(HttpSessionBindingEvent event)  { 
    	//1.获取被添加属性  的  name
    	String name = event.getName();
    	//2.如果是登录  则是 name=user
    	if("user".equals(name)){
    		//获取 该name的值，即登录的对象user
    		TblUser user = (TblUser) event.getValue();
    		//3.判断map里是否有该user对象(uname为key，user对象为value)，如果有  就替换之前的user
    		if(map.get(user.getUname())!=null){
    			//获取要替换的 对象登录的session
    			HttpSession session = (HttpSession) map.get(user.getUname());
    			//从该session中移除 登录对象，并使其失效
    			session.removeAttribute(user.getUname());
    			session.invalidate();
    			
    		}
    		//4.将登录的对象的信息 已  uname-存user的session 的形式存到  map
        	map.put(user.getUname(), event.getSession());
    	}
    }

	/**
     * 用户下线调用的方法
     */
    public void attributeRemoved(HttpSessionBindingEvent event)  { 
    	String name = event.getName();
    	if("user".equals(name)){
    		TblUser user = (TblUser) event.getValue();
    		//用户下线，将被从map中移除
    		map.remove(user.getUname());
    	}
    	
    }

    public void attributeReplaced(HttpSessionBindingEvent arg0)  { 
    }
	
}
