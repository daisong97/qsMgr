<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/asset/easyui/css/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/asset/easyui/css/themes/icon.css">

<script type="text/javascript" src="<%=request.getContextPath()%>/asset/easyui/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/asset/easyui/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/asset/easyui/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/asset/easyui/js/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/asset/diy/MeUtils.js"></script>

<title>工作记录管理</title>
</head>
<body>

<table id="jrTable" class="easyui-datagrid" title="统计" style="width:850px;height:350px"
            data-options="
                rownumbers: true,
                singleSelect: false,
                iconCls: 'icon-save',
                url: 'listJr.json',
                method: 'post',
                pagination:true,
            	rownumbers:true,
            	rownumbers:true,
            	toolbar:tb,onClickRow: onClickRow
            ">
        <thead>
            <tr>
            	<th data-options="field:'id',width:240,align:'center',checkbox:true"></th>
            	<th data-options="field:'jobNumber',width:240,align:'center',editor:{type:'textbox',options:{required:true}}">工号</th>
                <th data-options="field:'ip',width:150,align:'center',editor:{type:'textbox',options:{required:true}}">ip</th>
                <th data-options="field:'qsId',width:200,align:'center',formatter:function(value,row,index){return row.qsName;},editor:{type:'combobox',options:{valueField: 'id',textField: 'name',required:true,data:qsData}}">问卷</th>
				<th data-options="field:'readyNum',width:80,align:'center'">已做问卷数</th>
                <th data-options="field:'qPrice',width:80,align:'center'">单价</th>
            </tr>
        </thead>
    </table>
    <div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" id="addQsBtn" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加问卷</a>
			<a href="#" id="sumbQsBtn" class="easyui-linkbutton" iconCls="icon-add" plain="true">提交问卷</a>
			<a href="#" id="delQsBtn" class="easyui-linkbutton" iconCls="icon-save" plain="true">删除</a>
			<a href="#" id="endEditQs" class="easyui-linkbutton" plain="true">结束编辑</a>
		</div>
		<div>
			<form id="searchFrm">
			问卷名称: <input name="name" class="easyui-textbox" style="width:120px"/>
			<a href="#" id="searchQsBtn" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			<a href="#" id="resetQsBtn" class="easyui-linkbutton" iconCls="icon-cancel">清空</a>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		var qsData = ${qsData};
		+function(){
	  		$("#addQsBtn").click(function(){
	  			append();
	  		});
	  		$("#sumbQsBtn").click(function(){
	  			var data = getChanges();
	  			if(data){
	  				var formData = {};
	  				for(var t = 0 ;t < data.length;t++){
	  					var record = data[t];
	  					for(var dd in record){
	  						if(record[dd] == null){
	  							continue;
	  						}
	  						formData['list['+t+'].'+dd] = record[dd];
	  					}
	  				}
	  				$.post("saveOrUpdateJr",formData,function(data){
	  					doResult(data);
	  				},"json");
	  			}
	  		});
	  		$("#delQsBtn").click(function(){
	  			var rows = $("#jrTable").datagrid("getSelections");
	  			 $("#jrTable").datagrid("getRowIndex", $("#jrTable").datagrid("getSelections")[0])
	  			if(rows != null){
	  				var data = [];
	  				for(var i = 0;i<rows.length;i++){
	  					var index = $("#jrTable").datagrid("getRowIndex",rows[i]);
	  					 $("#jrTable").datagrid("deleteRow",index);
	  					if(rows[i].id != null){
	  						data.push(rows[i].id);
	  					}
	  				}
	  				$.post("deleteQs",{'ids':data.toString()},function(data){
	  					doResult(data);
	  				});
	  			}
	  		});
	  		$("#resetQsBtn").click(function(){
	  			$("#searchFrm").form("reset");
	  		});
	  		$("#endEditQs").click(function(){
	  			endEditing();
	  		});
	  		
	  		$("#searchQsBtn").click(function(){
	  			 var queryParams = {};
	  			 $("#searchFrm [name]").each(function(i,item){
	  			 	 var ele = $(item);
	  			 	 if(!String.isEmpty(ele.val())){
	  			 		return true;
	  			 	 }
	  			 	 queryParams[item.name] = ele.val();
	  			 });
	  			
	  			if(queryParams != null && queryParams.length > 0){
	  				queryParams  = queryParams[0];
	  			}
	  			$('#jrTable').datagrid({
					queryParams: queryParams
				});
	  		});
	  	}($)
	  	String.isEmpty = function(s){
	  		if(s != null && typeof(s) == 'string' && $.trim(s) != ''){
	  			return true;
	  		}
	  		return false;
	  	}
	</script>
	<script type="text/javascript">
	var editIndex = undefined;
	function endEditing(){
		if (editIndex == undefined){return true}
		if ($('#jrTable').datagrid('validateRow', editIndex)){
			$('#jrTable').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickRow(index){
		//if (editIndex != index){
			if (endEditing()){
				$('#jrTable').datagrid('selectRow', index)
						.datagrid('beginEdit', index);
				editIndex = index;
			} else {
				$('#jrTable').datagrid('selectRow', editIndex);
			}
		//}
	}
	function append(){
		if (endEditing()){
			$('#jrTable').datagrid('insertRow',{
				index: 0,	
				row: {}
			});
			editIndex = 0;
			$('#jrTable').datagrid('selectRow', editIndex)
					.datagrid('beginEdit', editIndex);
		}
	}
	function removeit(){
		if (editIndex == undefined){return}
		$('#jrTable').datagrid('cancelEdit', editIndex)
				.datagrid('deleteRow', editIndex);
		editIndex = undefined;
	}
	function accept(){
		if (endEditing()){
			$('#jrTable').datagrid('acceptChanges');
		}
	}
	function reject(){
		$('#jrTable').datagrid('rejectChanges');
		editIndex = undefined;
	}
	function getChanges(){
		if(endEditing()){
			var rows = $('#jrTable').datagrid('getChanges');
			if(rows.length < 1){
				$.Alert('没有修改记录!');
			}else{
				return rows;
			}
		}else{
			$.Alert('请完整填写必填字段!');
		}
		return false;
	}
	</script>
</body>
</html>