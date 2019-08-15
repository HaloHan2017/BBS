package com.yc.dao;

import java.util.List;
import java.util.Map;

import com.yc.bean.TblTopic;
import com.yc.bean.TblUser;
import com.yc.utils.DBUtil;
import com.yc.utils.Encrypt;

public class UserDao {
	//用户登录方法
	public TblUser login(TblUser u) {
		String sql="select * from tbl_user where uname=? and upass=?";
		//最终返回一个User对象，web层根据该对象是否为空，判断是否登录成功				加密后再去数据库进行比对
		return DBUtil.get(TblUser.class, sql,u.getUname(),Encrypt.md5(u.getUpass()));
	}
	//用户注册方法
	public void reg(TblUser u) {
		String sql="insert into tbl_user values(null,?,?,?,now(),?,?,?,?,?)";
		DBUtil.doUpdate(sql, u.getUname(),Encrypt.md5(u.getUpass()),u.getHead(),u.getGender(),u.getPhone(),u.getEmail(),u.getSwcount(),u.getLid());
	}
	
	public List<TblUser> queryAllUser() {
		String sql="select uid,uname,gender,head,regtime,phone,email,swcount from tbl_user";
		return DBUtil.list(TblUser.class, sql);
	}
	//修改密码
	public void modifyPwd(String email, String newpass) {
		String sql="update tbl_user set upass=? where email=?";
		DBUtil.doUpdate(sql, Encrypt.md5(newpass),email);
	}
	public List<TblUser> findUserById(Integer uid) {
		String sql="select * from tbl_user where uid=?";
		return DBUtil.list(TblUser.class, sql, uid);
	}
	//查询所有 未被禁言的用户列表
	public List<TblUser> queryUserListByUnBan() {
		String sql="SELECT uid,uname,swcount from tbl_user";
		return DBUtil.list(TblUser.class, sql);
	}
	

	/**
	 * 修改密码
	 * @param uid
	 * @param upass1
	 */
	public void alter(Integer uid, String upass1) {
		String sql = "update tbl_user set upass=? where uid=?";
		Object[] params = {upass1,uid};
		DBUtil.doUpdate(sql, params);
	}
	//根据id查用户信息
	public List<Map<String, Object>> getUserInfoById(Integer uid) {
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	(\n" +
				"		(\n" +
				"			SELECT\n" +
				"				u.uid,\n" +
				"				COUNT(t.topicid) AS topicnum\n" +
				"			FROM\n" +
				"				tbl_user u\n" +
				"			LEFT JOIN tbl_topic t ON u.uid = t.uid\n" +
				"			where u.uid=?\n" +
				"			GROUP BY\n" +
				"				u.uid\n" +
				"			ORDER BY\n" +
				"				u.uid\n" +
				"		) a\n" +
				"		INNER JOIN (\n" +
				"			SELECT\n" +
				"				u.uid,\n" +
				"				COUNT(r.replyid) AS replynum\n" +
				"			FROM\n" +
				"				tbl_user u\n" +
				"			LEFT JOIN tbl_reply r ON u.uid = r.uid\n" +
				"			GROUP BY\n" +
				"				u.uid\n" +
				"			ORDER BY\n" +
				"				u.uid\n" +
				"		) b ON a.uid = b.uid\n" +
				"	)";
		return DBUtil.list(sql, uid);
	}
	
	//禁言  直接将swcount设置 >=3
	public int banUser(int uid) {
		String sql="update tbl_user set swcount=3 where uid=?";
		return DBUtil.doUpdate(sql, uid);
	}
	//解除禁言  直接将swcount设置 <3
	public int UnBanUser(int uid) {
		String sql="update tbl_user set swcount=0 where uid=?";
		return DBUtil.doUpdate(sql, uid);
	}
	//判断是否被禁言
	public boolean getUserBanState(int uid) {
		String sql="select swcount from tbl_user where uid=?";
		int num = Integer.parseInt(DBUtil.get(sql,uid).get("swcount").toString());
		return (num>=3) ? true:false;
		// 		》3		被禁言    未被禁言
	}
	
	//查询该用户收藏的所有帖子
	public List<Map<String, Object>> getLikeTopicsByUid(Integer uid) {
		String sql="SELECT\n" +
				"	l.lid,\n" +
				"	l.uid,\n" +
				"	t.*, l.addtime\n" +
				"FROM\n" +
				"	like_topic l\n" +
				"LEFT JOIN (\n" +
				"	SELECT\n" +
				"		t.topicid,\n" +
				"		t.title,\n" +
				"		t.publishtime,\n" +
				"		t.modifytime,\n" +
				"		b.boardname\n" +
				"	FROM\n" +
				"		tbl_topic t\n" +
				"	LEFT JOIN tbl_board b ON t.boardid = b.boardid\n" +
				") t ON l.topicid = t.topicid\n" +
				"WHERE\n" +
				"	l.uid = ?";
		return DBUtil.list( sql, uid);
	}
	
	public int queryUserSWCount(int uid) {
		String sql="select swcount from tbl_user where uid=?";
		int ctn = (int) DBUtil.get(sql, uid).get("swcount");
		return ctn;
	}
	
	//用户等级更新
	public void updateUserLevel(Integer lid, Integer uid) {
		String sql="update tbl_user set lid=? where uid=?";
		DBUtil.doUpdate(sql, lid,uid);
	}

}
