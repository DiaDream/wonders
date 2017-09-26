package com.xyb.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xyb.controller.base.BaseController;
import com.xyb.entity.User;
import com.xyb.service.UserService;
import com.xyb.utils.CookieUtil;
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
	public String addUser(HttpServletRequest request,HttpServletResponse response,User user,
			@ApiParam(value = "验证码", required = true) @RequestParam String phonecode
    	){
		resultMap.put("status", 400);
		String username =  user.getUsername();
		User dbuser = userService.getUserByUsername(username);
		if(null != dbuser){
			resultMap.put("message", "帐号|Email已经存在！");
			return JSON.toJSONString(resultMap);
		}
		HttpSession session=getSession(request);
		String code=(String)session.getAttribute("code");
		if(phonecode!=null&&phonecode.equals(code)){
			user.setId(UUID.randomUUID().toString());
			user.setUsertype("0");
			//对密码进行MD5加密
			user.setPassword(MD5Util.MD5Encode(user.getPassword(), "utf-8"));
			//执行数据插入
			int flag=userService.addUser(user);
			if(flag>0){
				//注册成功把用户数据放入session和cookie中
				CookieUtil.addCookie(response , "loginName" , username , loginMaxAge); //将姓名加入到cookie中  
	            CookieUtil.addCookie(response , "loginPwd" , user.getPassword() , loginMaxAge);   //将密码加入到cookie中  
				session.setAttribute("user", user);
	            resultMap.put("data", "success");
				resultMap.put("message", "注册成功！");
			}
			
			
		}else{
			resultMap.put("message", "验证码不正确");
			
		}
		
		return JSON.toJSONString(resultMap);
	}
	//根据id查询用户信息
	@RequestMapping(value="userinfo",method=RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "根据id得到用户信息",response=User.class,httpMethod = "GET",notes = "得到用户数据")
	@ResponseBody
	public String getUserById(
			 @ApiParam(value = "用户ID", required = true) @RequestParam String id){
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
		//删除用户
		@RequestMapping(value="delbyid",method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
		@ApiOperation(value = "根据id删除用户",httpMethod = "POST",notes = "根据id删除用户")
		@ResponseBody
		public String delUserById(
				 @ApiParam(value = "用户ID", required = true) @RequestParam String id){
			resultMap.put("status", 400);
			userService.deleteUserByPrimarykey(id);
			resultMap.put("data", "success");
			resultMap.put("message", "查询成功");
			resultMap.put("status", 200);
			return JSON.toJSONString(resultMap);
		}
		
		
		//根据id重置密码
		@RequestMapping(value="resetpasswordbyid",method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
		@ApiOperation(value = "根据id重置密码",httpMethod = "POST",notes = "根据id删除用户")
		@ResponseBody
		public String resetPasswordById(
				User user){
			resultMap.put("status", 400);
			user.setPassword(MD5Util.MD5Encode("123456", "utf-8"));
			userService.updateUserByPrimaryKeySelective(user);
			resultMap.put("data", "success");
			resultMap.put("message", "修改成功");
			resultMap.put("status", 200);
			return JSON.toJSONString(resultMap);
		}
}
