<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ztree</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
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
			<div title="面板二">
				<!-- 构造一棵树 -->
				<ul id="myTree" class="ztree"></ul>
				<script type="text/javascript">
					$(function(){
						//ztree属性设置
						var setting = {};  //全部使用默认值
						//ztree数据
						var nodes = [
						             {"id":1,"name":"节点一","children":[{"id":10,"name":"节点1_1"},{"id":11,"name":"节点1_2"}]},
						             {"id":2,"name":"节点二"},
						             {"id":3,"name":"节点三"}
						        ];
						//调用ztree的API构造树
						$.fn.zTree.init($("#myTree"), setting, nodes);
					})
				</script>
			</div>
			<div title="面板三">
				<!-- 通过简单json数据格式构建树 -->
				<ul id="simpleTree" class="ztree"></ul>
				<script type="text/javascript">
					//配置ztree属性信息
					var setting1 = {
							data: {
								simpleData: {
									enable: true,  
									idKey: "id",  //节点id
									pIdKey: "pId", //父节点通过pId指定
									rootPId: 0     //顶级节点0
								}
							}	
					};
					
					var nodes1 = [
					              	{"id":1,"name":"节点一","pId":"2"},
					              	{"id":2,"name":"节点二","pId":"3"},
					              	{"id":3,"name":"节点三","pId":"0"}
				              	];
					//调用ztree初始化的方法
					$.fn.zTree.init($("#simpleTree"), setting1, nodes1);
				</script>
			</div>
			<div title="菜单数据">
				<ul id="menu" class="ztree"></ul>
				<script type="text/javascript">
					var setting2 = {
							data: {
								simpleData: {
									enable: true,  
									idKey: "id",  //节点id
									pIdKey: "pId", //父节点通过pId指定
									rootPId: 0     //顶级节点0
								}
							},
							callback: {
								onClick: function(event, treeId, treeNode){ //节点单击事件 treeNode:代表节点数据行
									//在浏览器的控制台输出
// 									console.info(treeNode);
									//判断是否是顶级节点
									if(treeNode.page != undefined){
										var r = $("#myTabs").tabs('exists',treeNode.name);
										if(r){
											//选中
											$("#myTabs").tabs('select',treeNode.name);
										}else{
											//添加选项卡
											$("#myTabs").tabs("add",{
												title:treeNode.name,
												closable:true,
												content:'<iframe height="100%" frameborder="0" width="100%" src="${pageContext.request.contextPath}/'+treeNode.page+'"></iframe>'
											});
										}
									}
								}   
							}
					};
					//数据从json文件中获取
					//调用ztree初始化的方法
					//发送ajax请求获取json文件中数据
					//$.post() $.ajax()  $.get() $.load() $.getJson() $.getScript
					var url = "${pageContext.request.contextPath}/json/menu.json"
					$.post(url,null,function(data){
						$.fn.zTree.init($("#menu"), setting2, data);
					},"json");
				</script>
			</div>
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