<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>登录页面</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
    <link href="../static/css/loginstyle.css" rel='stylesheet' type='text/css' />
    <script src="../static/js/jquery.min.js"></script>
    <style type="text/css">
    .sign{
    float: left;
    font-size: 17px;
    margin-top: 16px;
    margin-left: 15px;
    cursor: pointer;
    color: #9199aa;
    }
    </style>
</head>
<body>
<!--SIGN UP-->
<h1>Sign in</h1>
<div class="login-form">
    <div class="head-info">
    	<a href="register" class="sign">Sign up</a>
        <label class="lbl-1"> </label>
        <label class="lbl-2"> </label>
        <label class="lbl-3"> </label>
    </div>
    <div class="clear"> </div>
    <div class="avtar">
        <img src="../static/images/avtar.png" />
    </div>
    <form id="loginform"  method="post">
        <input id="username" name="username" type="text" class="text"  placeholder="请输入用户名" >
        <div class="key">
            <input type="password" name="password" id="password"  placeholder="请输入密码">
        </div>
    </form>
    <div class="signin">
        <input type="button" value="Sign in"  onclick="vilidateForm()">
    </div>
</div>
<div class="copy-rights">
    <p>Copyright &copy; 2017.9.26 <a href="#" target="_blank" title="work">工作第二天</a> - 找点事情做---<a href="#" title="友情链接" target="_blank"></a></p>
    
</div>
<script>
    function vilidateForm() {
        if($("#username").val().trim().length<1){
            alert("用户名不能为空");
            return;
        }
        if($("#password").val().trim().length<1){
            alert("密码不能为空");
            return;
        }
        //序列化提交表单
        $.post("/wonders/login/logincheck",$("#loginform").serialize(),function (data) {
            if(data.data=="success"){
                window.location.href="/wonders/admin/index";
            }else{
                alert("账号或者密码错误，请重试");
                location.reload();
            }
        });
    }

</script>
</body>
</html>