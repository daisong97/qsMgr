<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登录</title>
<link href="<%=request.getContextPath()%>/asset/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/asset/diy/signin.css" rel="stylesheet">

<script type="text/javascript" src="<%=request.getContextPath()%>/asset/easyui/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/asset/bootstrap/js/bootstrap.js"></script>


<script type="text/javascript" src="<%=request.getContextPath()%>/asset/diy/jquery.cookie.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/asset/diy/MeUtils.js"></script>

</head>
<body>
<div class="container">
      <form id="loginForm" class="form-signin" action="doLogin" method="post">
        <h2 class="form-signin-heading">登录吧</h2>
        <label for="inputEmail" class="sr-only">用户名</label>
        <input type="text" id="inputUserName" length = 20 class="form-control" data-toggle="tooltip" title="用户名不能为空!" placeholder="用户名" required autofocus>
        <br>
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="inputPassword" length = 20 class="form-control" data-toggle="tooltip" title="密码不能为空!" placeholder="密码" required>
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
      </form>
    </div>
    <script type="text/javascript">
    	$(function () {
    	  $('[data-toggle="tooltip"]').tooltip();
    	  $("#loginForm").submit( function () {
	  	    	var userName = $("#inputUserName").val();
	  	    	var password = $("#inputPassword").val();
	  	    	if(String.isEmpty(userName)){
	  	    		$("#inputUserName").tooltip('show');
	  				return false;
	  			}else if(String.isEmpty(password)){
	  				$("#inputPassword").tooltip('show');
	  				return false;
	  			}
    	  });
    	});
    </script>
</body>
</html>