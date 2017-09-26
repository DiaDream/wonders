package com.xyb.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xyb.controller.base.BaseController;
import com.xyb.entity.User;
import com.xyb.service.UserService;
import com.xyb.utils.CookieUtil;
import com.xyb.utils.MD5Util;
import com.xyb.utils.SMSUtil;
@Controller
@RequestMapping(value="/login/*")
public class WebPageController extends BaseController{
	 
	@Resource
    UserService userService;
	
	/**
     * 登录页面
     *
     * @param
     * @return
     */

    @RequestMapping("login")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("index/login");
        return view;
    }
    
    /**
     * 注册页面
     *
     * @param
     * @return
     */

    @RequestMapping("register")
    public ModelAndView register(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("index/register");
        return view;
    }
    /**
     * 404错误
     *
     * @param request
     * @return
     */
    @RequestMapping("error")
    public ModelAndView _404(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("error/404");
        return view;
    }
    //得到手机验证码
    @RequestMapping(value="getphonecode",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCode(HttpServletRequest request,String phone){
    	resultMap.put("status", 400);
    	if(phone==null||"".equals(phone)){
			resultMap.put("data", "failed");
			resultMap.put("message", "手机号码为空");
			resultMap.put("status", 200);
			return JSON.toJSONString(resultMap);
		}
    	try {
    		//得到随机手机验证码
			String code = SMSUtil.SendSMS(phone);
			//把手机验证码放入session中
			System.out.println(code+"~~~~");
			getSession(request).setAttribute("code", code);
			resultMap.put("status", 200);
			resultMap.put("message", "发送成功");
			resultMap.put("data", "success");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return JSON.toJSONString(resultMap);
    }
    
    //查询用户是否已经存在
    @RequestMapping(value="validateusername",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String validateUsername( 
    		@ApiParam(value = "用户名也就是手机号", required = true) @RequestParam String username){
    	resultMap.put("status", 400);
    	
    	User userByUsername = userService.getUserByUsername(username);
    	if(userByUsername!=null){
    		resultMap.put("message", "帐号|手机号码 已经存在！");
			resultMap.put("data", "failed");
			return JSON.toJSONString(resultMap);
    	}
    	resultMap.put("status", 200);
    	resultMap.put("message", "可以注册");
		resultMap.put("data", "success");
    	return JSON.toJSONString(resultMap);
    }
    
    //验证用户登录
    
    //查询用户是否已经存在
    @RequestMapping(value="logincheck",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String logincheck(HttpServletRequest request,HttpServletResponse response,
    		@ApiParam(value = "用户名也就是手机号", required = true) @RequestParam String username,
    		@ApiParam(value = "用户密码", required = true) @RequestParam String password
    		){
    	resultMap.put("status", 400);
    	if(password!=null&&!"".equals(password)){
    		password=MD5Util.MD5Encode(password, "utf-8");
    	}
    	User userByUsername = userService.validateUserLogin(username, password);
    	if(userByUsername!=null){
    		resultMap.put("status", 200);
    		resultMap.put("message", "登录成功");
			resultMap.put("data", "success");
			CookieUtil.addCookie(response , "loginName" , username , loginMaxAge); //将姓名加入到cookie中  
            CookieUtil.addCookie(response , "loginPwd" , userByUsername.getPassword() , loginMaxAge);   //将密码加入到cookie中  
			getSession(request).setAttribute("user", userByUsername);
			return JSON.toJSONString(resultMap);
    	}
    	resultMap.put("message", "账号或者密码错误");
		resultMap.put("data", "falied");
    	return JSON.toJSONString(resultMap);
    }
    //用户注销
    @ApiOperation(value = "注销登录", httpMethod = "GET", notes = "注销登录的方法")
	@RequestMapping("/loginout")
	public void loginOut(HttpServletRequest request,HttpServletResponse response) throws IOException{
		 	
			CookieUtil.addCookie(response, "loginName", null, 0); // 清除Cookie  
			CookieUtil.addCookie(response, "loginPwd", null, 0); // 清除Cookie  
			request.getSession().setAttribute("user", null); 
	        String path = request.getContextPath();  
	        String basePath = request.getScheme() + "://"  
	                + request.getServerName() + ":" + request.getServerPort()  
	                + path + "/";  
			String finalPath = basePath+"login/login";  
	        response.sendRedirect(finalPath);  
	
		
	}
}
