package com.xyb.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.xyb.controller.base.BaseController;
import com.xyb.entity.User;
import com.xyb.service.UserService;
import com.xyb.utils.MD5Util;

@Controller
@RequestMapping(value="/user/*")
@Api(value = "User-api", description = "关于用户的CRUD", position = 1)
public class UserController extends BaseController {
	@Resource
    UserService userService;
	//添加用户
	@RequestMapping(value="adduser",method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "添加用户",httpMethod = "POST",notes = "向数据库插入一条数据")
	@ResponseBody
	public String addUser(User user){
		resultMap.put("status", 400);
		String username =  user.getUsername();
		User dbuser = userService.getUserByUsername(username);
		if(null != dbuser){
			resultMap.put("message", "帐号|Email已经存在！");
			return JSON.toJSONString(resultMap);
		}
		user.setId(UUID.randomUUID().toString());
		user.setPassword(MD5Util.MD5Encode(user.getPassword(), "utf-8"));
		userService.addUser(user);
		resultMap.put("message", "注册成功！");
		resultMap.put("status", 200);
		return JSON.toJSONString(resultMap);
	}
	//根据id查询用户信息
	@RequestMapping(value="userinfo",method=RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "根据id得到用户信息",response=User.class,httpMethod = "GET",notes = "得到用户数据")
	@ResponseBody
	public String getUserById(String id){
		resultMap.put("status", 400);
		User userInfoByPrimarykey = userService.getUserInfoByPrimarykey(id);
		resultMap.put("data", userInfoByPrimarykey);
		resultMap.put("message", "查询成功");
		resultMap.put("status", 200);
		return JSON.toJSONString(resultMap);
	}
	//查询用户列表
	@RequestMapping(value="userlist",method=RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "得到用户列表",httpMethod = "GET",notes = "得到用户列表信息")
	@ResponseBody
	public String getUserList(){
		resultMap.put("status", 400);
		List<User> userList = userService.getUserList();
		resultMap.put("data", userList);
		resultMap.put("message", "查询成功");
		resultMap.put("status", 200);
		return JSON.toJSONString(resultMap);
	}
}
