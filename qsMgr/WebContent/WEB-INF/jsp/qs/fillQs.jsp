<%@ page language="java" contentType="text/html; charset=utf-8" import="org.apache.commons.lang3.StringUtils"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>问卷IP填写</title>
<link href="<%=request.getContextPath()%>/asset/bootstrap/css/bootstrap-theme.css" rel="stylesheet"/>
<link href="<%=request.getContextPath()%>/asset/bootstrap/css/bootstrap.css" rel="stylesheet">

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/asset/easyui/css/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/asset/easyui/css/themes/icon.css">
 
<script type="text/javascript" src="<%=request.getContextPath()%>/asset/easyui/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/asset/bootstrap/js/bootstrap.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/asset/easyui/js/jquery.easyui.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/asset/diy/jquery.cookie.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/asset/diy/MeUtils.js"></script>
</head>
<body>
	<%
		String ipAddr = request.getRemoteAddr();
		if(StringUtils.isBlank(ipAddr)){
			ipAddr = "未知的IP,请与管理员联系!";
		}
	%>
	<div class="container">
		<form id="qsInfo" class="form-signin" action="addJobRecord" method="post">
			<h2 class="form-signin-heading">您问卷信息</h2>
			<table class="table">
				<tr>
					<td>你的工号:</td>
					<td>
						<input type="text" id="jobNum" name="jobNum"  data-options="required:true" class="form-control input-sm" id="jobNum" placeholder="请输入你的工号!" required/>
					</td>
				</tr>
				<tr>
					<td>您现在IP:</td>
					<td>
						<input type="text" name="ip" value="<%=ipAddr%>"  data-options="required:true" class="form-control input-sm" id="ip" placeholder="请输入ip" required/>
					</td>
				</tr>
				<tr>
					<td>问卷名:</td>
					<td>
						<select id="qsId" name="qsId"  data-options="required:true" class="form-control input-sm">
							<c:forEach var="obj" items="${qss}">
								<option value="${obj.id}" totalNum="${obj.totalNum}">${obj.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>总量:</td>
					<td><span id="totalNum"></span></td>
				</tr>
				<tr>
					<td>您已做问卷数:</td>
					<td><span id="completeNum"></span></td>
				</tr>
				<tr>
					<td>剩余:</td>
					<td><span id="lastNum"></span></td>
				</tr>
				<tr>
					<td>处理信息:</td>
					<td><span id="doResult"></span></td>
				</tr>
			</table>
			<button class="btn btn-lg btn-primary btn-block" type="submit">提交</button>
		</form>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#jobNum").val($.cookie('JOB_NUM_INFO'));
			$('#qsId').val($.cookie('is_select_qs'));
			$('#qsId').change(function(){
				setUserInfo();
			});
			setUserInfo();
		});
		$('#qsInfo').form({
		    onSubmit: function(){
		    	return $('#qsInfo').form("validate");
		    },
		    success:function(data){
		    	$.cookie('JOB_NUM_INFO',$("#jobNum").val());
		    	$.cookie('is_select_qs',$('#qsId').val());
		    	var json = $.parseJSON(data);
		    	btDoResult(json);
		    	if(json.status == 1){
		    		$("#doResult").val('处理成功!');
		    	}else{
		    		$("#doResult").val('处理失败!');
		    	}
		    }
		});
		function setUserInfo(){
			var t = $('#qsId option:selected').val();
			var jobNum =  $("#jobNum").val();
			var qsId = $('#qsId').val()
			if(String.isEmpty(jobNum) || String.isEmpty(qsId)){
				return ;
			}
			
			$("#totalNum").text(t);
			$.post('queryUserInfo',{jobNum:jobNum,qsId:qsId},function(data){
				$("#completeNum").text(data.completeNum);
				$("#allCount").text(data.allCount);
				$("#lastNum").text(t - data.allCount);
			},"json");
		}
	</script>
</body>
</html>