<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>问卷管理</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/asset/easyui/css/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/asset/easyui/css/themes/icon.css">

<script type="text/javascript" src="<%=request.getContextPath()%>/asset/easyui/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/asset/easyui/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/asset/easyui/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/asset/easyui/js/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/asset/diy/MeUtils.js"></script>
</head>
<body>
	<div role="tabpanel">
	  <!-- Nav tabs -->
	  <ul class="nav nav-tabs" role="tablist">
	    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Home</a></li>
	    <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Profile</a></li>
	    <li role="presentation"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">Messages</a></li>
	    <li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">Settings</a></li>
	  </ul>
	
	  <!-- Tab panes -->
	  <div class="tab-content">
	    <div role="tabpanel" class="tab-pane active" id="home">...</div>
	    <div role="tabpanel" class="tab-pane" id="profile">...</div>
	    <div role="tabpanel" class="tab-pane" id="messages">...</div>
	    <div role="tabpanel" class="tab-pane" id="settings">...</div>
	  </div>
	</div>
</body>
</html>