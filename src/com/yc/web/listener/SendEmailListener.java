package com.yc.web.listener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.yc.bean.TblUser;
import com.yc.service.UserService;
import com.yc.utils.DBUtil;

/**
 * 定时任务监听器
 *
 */
@WebListener
public class SendEmailListener implements  ServletContextListener {

    public SendEmailListener() {
    }

    public void contextDestroyed(ServletContextEvent arg0)  { 
    }


    public void contextInitialized(ServletContextEvent arg0)  { 
    	// 指定的任务，从指定的延迟后，开始进行重复执行。
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		/**
		 *  定制每天的8:00:00执行，若程序已超过8点启动,当天不再执行，等到明日八点再执行
		 *  这样保证了时间一直是8点，而不会变成程序启动时间
		 */
		calendar.set(year, month, day, 8, 00, 00);
		Date defaultdate = calendar.getTime();// 今天8点（默认发送时间）
		Date sendDate = new Date();
		// 8点后开机
		if (defaultdate.before(new Date())) {
			// 将发送时间设为明天8点
			calendar.add(Calendar.DATE, 1);
			sendDate = calendar.getTime();
		}
    	
    	//每月一号  给所有用户自动解除禁言
    	Timer t=new Timer();
    	t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Calendar c=Calendar.getInstance();
				int day = c.get(Calendar.DAY_OF_MONTH);
				if (day == 1) {
					// 每天判断，若为每月1号才执行
					//测试
					//System.out.println("任务执行完毕");
					
					/*
					 * 执行sql语句，将所有用户的 违规发言次数重置为0，已发送邮件sendEamil（禁言通知）标志  --重置为0（未发送）
					 * */
					autoOpenAllUserSpeak();
					
					//给频繁违规发言的用户  发送违规提醒邮件
					sendEmailToUsersByBadWord();
					//任务执行完毕
				}
			}

			
		},sendDate, 24 * 60 * 60 * 1000);//一天进行一次判断
    }
    //每天统计  查看近期频繁发言违规词语的用户  ，给他们发送邮件提示
    private void sendEmailToUsersByBadWord() {
		
	}
    //每月一号  自动解禁所有用户
    private void autoOpenAllUserSpeak(){
    	String sql="update tbl_user set swcount=0 where swcount!=0";//将禁言用户解禁
		DBUtil.doUpdate(sql);
		
		//获得所有用户列表
		UserService us=new UserService();
		List<TblUser> list = us.queryAllUser();
		for(int i=0;i<list.size();i++){
			//如果用户的sendEamil（禁言通知）标志 不为0，则改为0（都改为未发送状态）
			if(list.get(i).getSendEmial()!=0){
				list.get(i).setSendEmial(0);
			}
		}
    }
    
}
