package com.xyb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xyb.controller.base.BaseController;
@Controller
@RequestMapping(value="/admin/*")
public class AdminController extends BaseController{
	@RequestMapping("index")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("admin/userlist");
        return view;
    }
}
