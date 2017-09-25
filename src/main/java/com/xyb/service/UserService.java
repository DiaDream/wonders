package com.xyb.service;
import java.util.List;

import com.xyb.entity.User;

public interface UserService {
	//根据ID查询
	User getUserInfoByPrimarykey(String id);
	
	//查询用户列表
	List<User> getUserList();
	
	//检查用户名是否存在
	User getUserByUsername(String username);
	
	//验证用户登录
	User validateUserLogin(String username,String password);
	
	//添加用户
	int addUser(User user);
	
	//根据ID删除用户
	int deleteUserByPrimarykey(String  id);
	
	//根据用户已有数据修改用户
	int updateUserByPrimaryKeySelective(User user);
	
	//修改用户所有数据
	int updateUserByPrimarykey(User user);
}
