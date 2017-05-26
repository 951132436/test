<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>折叠面板</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" title="xxx系统" style="height:100px">北</div>
	<div data-options="region:'west'" title="系统菜单" style="width:150px">
		<!-- 添加折叠面板 -->
		<div class="easyui-accordion" title="系统管理" data-options="fit:true">
			<div title="xxxx">1</div>
			<div title="xxxx">2</div>
			<div title="xxxx">3</div>
		</div>
	</div>
	<div data-options="region:'center'">中</div>
<!-- 	<div data-options="region:'east'" style="width:150px">东</div> -->
	<div data-options="region:'south'" style="height:50px">南</div>
</body>

</html>