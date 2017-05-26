<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据表格</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<!-- 将静态HTML渲染为datagrid样式 -->
	<table class="easyui-datagrid">
		<thead>
			<tr>
				<th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'age'">年龄</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>001</td>
				<td>小明</td>
				<td>10</td>
			</tr>
			<tr>
				<td>002</td>
				<td>小hua</td>
				<td>10</td>
			</tr>
			<tr>
				<td>002</td>
				<td>小hua</td>
				<td>10</td>
			</tr>
		</tbody>
	</table>
	
	
	<hr>
	<!-- 发送请求获取json数据格式创建datagrid -->
	<table class="easyui-datagrid" data-options="url:'${pageContext.request.contextPath }/json/datagrid_data.json'">
		<thead>
			<tr>
				<th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'age'">年龄</th>
				<th data-options="field:'address'">地址</th>
			</tr>
		</thead>
	</table>
	
	<hr>
	<!--调用easyui的API创建datagrid -->
	<table id="myTable"></table>
	<script type="text/javascript">
		$(function(){
			$("#myTable").datagrid({
				//展示标题列信息
				columns:[[
				          {title:'编号',field:'id',checkbox:true},
				          {title:'姓名',field:'name'},
				          {title:'年龄',field:'age'},
				          ]]
				,
				//发送请求获取数据
				url:'${pageContext.request.contextPath }/json/datagrid_data.json',  
				//工具栏
				toolbar:[
				         {text:'增加',iconCls:'icon-save',handler:function(){
				        	 alert(111);
				         }},   //一个对象代表一个按钮
				         {text:'修改',iconCls:'icon-edit',handler:doUpdate},
				         {text:'删除',iconCls:'icon-remove'},
				         {text:'查询',iconCls:'icon-search'}
				         ]
				,
				//是否分页
				pagination:true,
				striped:true, //条纹显示
				rownumbers:true,//行号
				singleSelect:true,//单选
				pageNumber:4, //初始化页码
				pageSize:5,   //初始化记录数
				pageList:[5,10,15] //选择每页显示数据的列表
			});
		})
		
		function doUpdate(){
			alert('修改');
		}
	</script>
</body>




</html>