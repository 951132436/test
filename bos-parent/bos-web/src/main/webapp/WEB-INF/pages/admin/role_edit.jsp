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
<!-- 导入ztree类库 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css"
	type="text/css" />
<script
	src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"
	type="text/javascript"></script>	
<script type="text/javascript">
	$(function(){
		// 授权树初始化
		var setting = {
			data : {
				key : {
					title : "t"
				},
				simpleData : {   //简单json数据格式.通过pId指定数据上级节点    [{id:1,name:'rose',pId:'0'},{id:2,name:'jack',pId:'1'},]
					enable : true
				}
			},
			check : {
				enable : true,  //数据勾选效果  模拟checkbox效果
			}
		};
		
		//异步加载ztree树
		$.ajax({
			url : '${pageContext.request.contextPath}/functionAction_listajaxForZtree.action',
			type : 'POST',
			dataType : 'json',
			async:false,
			success : function(data) {
// 				var zNodes = eval("(" + data + ")");
				$.fn.zTree.init($("#functionTree"), setting, data);
			},
			error : function(msg) {
				alert('树加载异常!');
			}
		});
		
		//ztree回显
		$.ajax({
			url : '${pageContext.request.contextPath}/functionAction_findAjaxByRoleid.action?roleId=${role.id}',
			type : 'POST',
			 dataType : 'json',
			success : function(nodes) {
				console.info(nodes);
				var treeObj = $.fn.zTree.getZTreeObj("functionTree");
				//console.info(treeObj);
				for (var i = 0; i < nodes.length; i++) {
					var node = treeObj.getNodeByParam("id", nodes[i].id, null);
					if(node!=null){
						treeObj.checkNode(node, true, false);
						treeObj.expandNode(node, true, true, true);
					}
				}
			},
			
		});
	
		// 点击修改
		$('#update').click(function(){
			//表单校验
			var r = $("#roleForm").form('validate');
			if(r){
				//获取到ztree选中的数据
				var treeObj = $.fn.zTree.getZTreeObj("functionTree");  //获取ztree组件
				var nodes = treeObj.getCheckedNodes(true);  //获取选中数据
				var array = new Array();
				for(var i=0;i<nodes.length;i++){
					var fId = nodes[i].id;
					array.push(fId);
				}
				//在form中添加权限的隐藏域
				var functionIds = array.join(',');
				$("#functionIds").val(functionIds);
				$("#roleForm").submit();
			}
		});
	});
</script>	
</head>
<body class="easyui-layout">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="update" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >确认修改</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="roleForm" action="roleAction_update.action" method="post">
				<input type="hidden" name="functionIds" id="functionIds">
				<input type="hidden" name="id" id="id" value="${role.id}">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">角色信息</td>
					</tr>
					<tr>
						<td width="200">关键字</td>
						<td>
							<input type="text" name="code" value="${role.code}" class="easyui-validatebox" data-options="required:true" />						
						</td>
					</tr>
					<tr>
						<td>名称</td>
						<td><input type="text" name="name" value="${role.name}" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td>描述</td>
						<td>
							<textarea name="description" rows="4" cols="60">${role.description}</textarea>
						</td>
					</tr>
					<tr>
						<td>授权</td>
						<td>
							<ul id="functionTree" class="ztree"></ul>
						</td>
					</tr>
					</table>
			</form>
		</div>
</body>
</html>