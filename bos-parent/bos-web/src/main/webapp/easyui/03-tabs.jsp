<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选项卡面板</title>
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
			<div title="面板一">
<!-- 				<input id="add" type="button" value="添加"> -->
				<a id="add" class="easyui-linkbutton">添加</a>
				<script type="text/javascript">
					//页面加载完成后给按钮绑定单击事件
					$(function(){
						$("#add").click(function(){
							//判断选项卡面板是否存在
							var r = $("#myTabs").tabs('exists','系统管理');
							if(r){
								//存在、选中
								$("#myTabs").tabs('select','系统管理');
							}else{
								//添加
								//调用tabs组件API，添加选项卡面板
								$("#myTabs").tabs('add',{
									title:'系统管理',
									iconCls:'icon-save',
									closable:true,
									content:'<iframe height="100%" frameborder="0" width="100%" src="${pageContext.request.contextPath}/page_base_staff.action"></iframe>'
								});
							}
						});
					})
				</script>
			</div>
			<div title="面板二">2</div>
			<div title="面板三">3</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<!-- 选项卡面板 -->
		<div id="myTabs" class="easyui-tabs" title="系统管理" data-options="fit:true">
			<div title="取派员" data-options="closable:true,iconCls:'icon-edit'">1</div>
			<div title="特派员" data-options="closable:true">2</div>
		</div>
	</div>
<!-- 	<div data-options="region:'east'" style="width:150px">东</div> -->
	<div data-options="region:'south'" style="height:50px">南</div>
</body>

</html>