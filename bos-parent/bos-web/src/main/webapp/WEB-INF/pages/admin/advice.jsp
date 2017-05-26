<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		$("#grid").datagrid({
			border : false,
			rownumbers : true,
			striped : true,
			idField : 'id',
			toolbar : [{				
				id : 'button-delete',
				text : '删除',
				iconCls : 'icon-cancel',
				handler : doDelete
				}      
			],
			url : 'userAdviceAction_pageQuery.action',
			columns : [[				
			  {
				  checkbox : true,
				  field : 'id',
				  title : '意见编号',
				  width : 200
			  },
			  {
				  field : 'fromuser',
				  title : '用户名称',
				  width : 200
			  },  
			  {
				  field : 'adviceContent',
				  title : '详细意见',
				  width : 400
			  },  
			  {
				  field : 'userIp',
				  title : '用户IP',
				  width : 200
			  },  
			  {
				  field : 'adviceTimeString',
				  title : '提交时间',
				  width : 200
			  }
			]],
			pagination:true,
			fit:true
		});
	});
	
	function doDelete(){
		//将选中记录取派员的id获取到,返回数组  
		var rows = $("#grid").datagrid('getSelections');
		//判断是否选中记录
		if(rows.length==0){
			$.messager.alert('提示信息','请至少选择一条记录操作！','warning');
		}else{
			$.messager.confirm('提示信息','确定删除吗？',function(r){
				if(r){
					var array = new Array();
					for(var i = 0;i<rows.length;i++){
						var adviceId = rows[i].id;
						array.push(adviceId);
					}
					var ids = array.join(",");//默认使用,拼接
					window.location.href="userAdviceAction_deleteBatch.action?ids="+ids;
				}
			});
		}
	}
</script>	
</head>
<body class="easyui-layout">
<div data-options="region:'center'">
	<table id="grid"></table>
</div>
</body>
</html>