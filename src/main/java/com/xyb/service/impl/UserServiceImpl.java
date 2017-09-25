package com.xyb.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xyb.dao.UserMapper;
import com.xyb.entity.User;
import com.xyb.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Resource
	UserMapper userMapper;
	@Override
	public User getUserInfoByPrimarykey(String id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> getUserList() {
		// TODO Auto-generated method stub
		return userMapper.getUserList();
	}

	@Override
	public User validateUserLogin(String username, String password) {
		// TODO Auto-generated method stub
		return userMapper.validateUserLogin(username, password);
	}

	@Override
	public int addUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.insert(user);
	}

	@Override
	public int deleteUserByPrimarykey(String id) {
		// TODO Auto-generated method stub
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateUserByPrimaryKeySelective(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public int updateUserByPrimarykey(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKey(user);
	}

	@Override
	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return userMapper.getUserByUsername(username);
	}

}
