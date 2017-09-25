package com.xyb.interceptor;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.Cookie;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xyb.entity.User;
import com.xyb.service.UserService;
import com.xyb.utils.CookieUtil;

public class PromissionInterceptor implements HandlerInterceptor{
		private static Logger logger = Logger.getLogger(PromissionInterceptor.class);  
//		@Resource
//		private UserService sUserService;
		public void afterCompletion(HttpServletRequest arg0,
				HttpServletResponse arg1, Object arg2, Exception arg3)
				throws Exception {
		}

		public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
				Object arg2, ModelAndView arg3) throws Exception {
		}

		public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
				Object arg2) throws Exception {
			
			  String uri = request.getRequestURI();  
			  HttpSession session = request.getSession();
		         
		      //设置不拦截的对象  
//		        String[] noFilters = new String[] {"login","authCode","search","checkpassword","userbase","index","checklogin","register","test"};  //对登录本身的页面以及业务不拦截  
		        boolean beFilter = true;   
//		        for (String s : noFilters) {    
//		            if (uri.indexOf(s) != -1) {    
//		                beFilter = false;    
//		                break;    
//		            }    
//		        }  
			
//		        if (beFilter==true) {//除了不拦截的对象以外  
//		            String path = request.getContextPath();  
//		            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";  
//		            //不拦截对象将取得cookie中的值
//		            Cookie cokLoginName = CookieUtil.getCookieByName(request, "loginName");  
//		            Cookie cokLoginPwd = CookieUtil.getCookieByName(request, "loginPwd"); 
//		            Cookie cokusertype = CookieUtil.getCookieByName(request, "usertype"); 
//		            
//		            if (cokLoginName != null && cokLoginPwd != null && cokLoginName.getValue() != "" && cokLoginPwd.getValue() != ""&&cokusertype != null && cokusertype != null) {  
//		                String loginName = cokLoginName.getValue();  
//		                String loginPwd = cokLoginPwd.getValue();  
//		                String usertype = cokusertype.getValue();
//		        
//		                //验证是否登录 数据库查询
//		                //User user=sUserService.getByThree(loginName, loginPwd);
//		                if (user == null) {  
//		                	CookieUtil.addCookie(response, "loginName", null, 0); // 清除Cookie  
//		                	CookieUtil.addCookie(response, "loginPwd", null, 0); // 清除Cookie  
//		                	CookieUtil.addCookie(response, "usertype", null, 0); // 清除Cookie  
//		                	logger.error("用户未登录");
//		                	try {  
//		                        response.sendRedirect(basePath + "index/login");  
//		                        return false;  
//		                    } catch (IOException e) {  
//		                        e.printStackTrace();  
//		                    }  
//		                }else {  
//		                    //从session中取得用户
//		                	User utcUsers = (User)session.getAttribute("user");  
//		                    if (utcUsers==null) {//如果未登录而直接拷贝地址栏进入页面  
//		                    	session.setAttribute("user", user);  
//		                    }else {//用户登录后  
//		                        if (utcUsers.getUsername().equals(user.getUsername())) {//如果当前登录人与cookie中信息一致  
//		                            session.setAttribute("user", user);  
//		                        }else {//如果当前登录人与cookie中信息不一致  
//		                            session.setAttribute("user", utcUsers);  
//		                        }
//		                        if(user.getUsertype()!=3){
//		                        	//用户不是管理员 跳权限错误页面
//		                        	 logger.error("用户不是平台管理员");
//		                        	 response.sendRedirect(basePath + "index/error");
//		                        }
//		                    }  
//		                }  
//		            } else{  
//		                User u = (User)session.getAttribute("user");  
//		                if (u==null) {//如果未登录  
//		                    response.sendRedirect(basePath + "index/login");  
//		                     return false;  
//		                }else {//如果已经登录  
//		                    //执行下一步
//		                	 if(u.getType()!=3){
//		                	 logger.error("用户不是管理员");
//		                      response.sendRedirect(basePath + "index/error");
//		                    }
//		                }  
//		            } 
//		        }  
		        return true;  
		}
}
