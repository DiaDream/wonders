<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>注册页面</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
    <link href="../static/css/loginstyle.css" rel='stylesheet' type='text/css' />
    <script src="../static/js/jquery.min.js"></script>
    
    <style>
    #password{
    	margin-bottom: 0em;
    }
    #yzm{
    	margin-bottom: 1em;
    	margin-top: 0em;
    }
  
    .dn{
    display: none;
    }
    .sign{
    float: left;
    font-size: 17px;
    margin-top: 16px;
    margin-left: 15px;
    cursor: pointer;
    color: #9199aa;
    }
    .tips{
    margin-bottom: 2em;
    color: #ea569a;
    }
    </style>
</head>

<body>
<!--SIGN UP-->
<h1>Sign up</h1>
<div class="login-form">
    <div class="head-info">
    	<a href="login" class="sign">Sign in</a>
        <label class="lbl-1"> </label>
        <label class="lbl-2"> </label>
        <label class="lbl-3"> </label>
    </div>
    <div class="clear"> </div>
    <form id="registerform"  method="post">
        <input id="username" name="username" type="text" class="text" placeholder="请输入手机号码" >
        <div class="key">
        	<input  type="password" name="password" id="password"  placeholder="请输入密码">
            <input  type="text" name="phonecode" id="yzm"  placeholder="请输入验证码">
            <p id="tip" class="tips"></p>
        </div>
    </form>
    <div class="signin">
        <input id="getcode" type="button" value="Get PhoneCode"  onclick="getPhoneCode()">
        <input id="signup" class="dn" type="button" value="Sign up"  onclick="registeraccount()">
    </div>
</div>
<div class="copy-rights">
    <p>Copyright &copy; 2017.9.26 <a href="#" target="_blank" title="work">工作第二天</a> - 找点事情做---<a href="#" title="友情链接" target="_blank"></a></p>
    
</div>
<script>
	//当input获得焦点所有提示消失
	$(function(){
		$("#username").focus(function(){
			$("#tip").text("");
		});
		$("#username").blur(function(){
			var name=$("#username").val();
			var telzz = /^1\d{10}$/;
			if(!telzz.test(name)) {
		        	$("#tip").text("请输入手机号码")
		            return;
		     }
			 $.post("/wonders/login/validateusername",{"username":name},function (data) {
		            if(data.data=="success"){
		            	$("#tip").text("用户名可以使用");
		            }else{
		            	$("#tip").text("用户名已存在");
		                
		            }
		        });
		});
	});
	//简单验证用户名和密码
    function vilidateForm() {
        if($("#username").val().trim().length<1){
        	$("#tip").text("用户名不能为空")
            return false;
        }
        if($("#password").val().trim().length<1){
        	$("#tip").text("密码不能为空")
            return false;
        }
        $("#tip").text("");
        return true;
       
    }
	
	//发送手机验证码
	function getPhoneCode(){
		var tel = $("#username").val();
		var telzz = /^1\d{10}$/;
		if(!vilidateForm()){
			return;
		}
		if(telzz.test(tel)) {
			$.post("/wonders/login/getphonecode", {
				phone: tel
			}, function(result) {
				if(result.data=="success"){
					$("#signup").removeClass("dn");
					$("#getcode").addClass("dn");
				}else{
					$("#tip").val(result.message)
				}
			});
		} else {
			$("#tip").text("手机号码格式不正确")
			$("#username").val("");
		}
	}
	
	function registeraccount(){
        //序列化提交表单
		if(!vilidateForm()){
			return;
		}
		if($("#yzm").val().trim().length<1){
	        	$("#tip").text("验证码不能为空");
	            return;
	    }
        $.post("/wonders/user/adduser",$("#registerform").serialize(),function (data) {
    
           if(data.data=="success"){
            window.location.href="/wonders/admin/index";
           }else{
          		alert("注册失败");
           		location.reload();
           }
        });
	}
</script>
</body>
</html>