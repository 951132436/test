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
	<!-- 导入ztree类库 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css"
	type="text/css" />
<script
	src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"
	type="text/javascript"></script>	
<script type="text/javascript">
	$(function(){
		// 数据表格属性
		$("#grid").datagrid({
			onDblClickRow : doDblClickRow,
			toolbar : [
				{
					id : 'add',
					text : '添加角色',
					iconCls : 'icon-add',
					handler : function(){
						location.href='${pageContext.request.contextPath}/page_admin_role_add.action';
					}
				},
				{
					id : 'edit',
					text : '修改角色',
					iconCls : 'icon-edit',
					handler : doEdit
				} 
			],
			url : '${pageContext.request.contextPath}/roleAction_pageQuery.action',
			columns : [[ 
				{
					field : 'id',
					checkbox : true,
					width : 200
				},
				{
					field : 'name',
					title : '名称',
					width : 200
				}, 
				{
					field : 'description',
					title : '描述',
					width : 200
				} 
			]],
			onDblClickRow : doDblClickRow
		});
		
		//页面加载完成  窗口是关闭的
		//修改去派员信息的窗口
		/* $('#editRoleWindow').window({
	        title: '更新角色',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    }); */
		
		
		
		
	});
	//双击修改
	function doDblClickRow(rowIndex, rowData) {
		var items = $('#grid').datagrid('selectRow',rowIndex);
		//alert(rowData);
		//console.info(rowData);
		doEdit();
	}
	
	
	//获取参数跳转到修改页面
	function doEdit() {
		//获得选中的行
		var rows = $('#grid').datagrid('getSelections');
		//判断获得行数,
		if (rows.length == 0) {
			//没有选中,提示至少选中一行
			$.messager.alert('提示信息', '请选择一条记录进行修改操作!', 'warning');
		} else {
			if (rows.length > 1) {
				//选择的不是一行,提示只能选择一行
				$.messager.alert('提示信息', '只能选择一条记录进行修改操作！', 'warning');
			} else {
				//选择一行进行修改，提示客户是否确定要修改
				$.messager.confirm('提示信息', '确定修改吗？', function(r) {
					//确定删除
					if (r) {
						window.location.href="${pageContext.request.contextPath}/roleAction_toEdit.action?id="+rows[0].id;	
					}
				});
			}
		}
	}

</script>	
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<table id="grid"></table>
	</div>
	<%-- <div class="easyui-window" title="角色修改" id="editRoleWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
				<script type="text/javascript">
					/* 保存用角色 */
					$(function(){
						
						$("#edit").click(function(){
							//form 表单校验
						var r=	$("#editRoleFrom").form("validate");
							//form 表单提交
						if(r){
							//form表单的提交 ztree 的提交 参数  获取ztree选择的数据
							var ztree=$.fn.zTree.getZTreeObj("functionTree");
							var arr = ztree.getCheckedNodes(true);
							var array = new Array();
							for(var i =0; i<arr.length;i++){
								var roleId = arr[i].id;
								array.push(roleId);
								
								
							}
							var functionId = array.join(",");
							//将得到的数据放到隐藏域中提交到后台
							$("#func").val(functionId);
							//form表单的提交
							$("#editRoleFrom").submit();
						}
						})
					})
				
				</script>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editRoleFrom" action="${pageContext.request.contextPath}/roleAction_editRole.action" method="post">
					<input type="hidden" name="id"/>
					<input type="hidden" name="functionIds" id="functionIds"/>
				<table class="table-edit" width="80%" align="center"> --%>
					
					<!-- 角色的详细信息 -->
					<tr >
						<td colspan="2">名称</td>
						<td colspan="2">
							<input type="text" name="name" class="easyui-validatebox" required="true"/>
						</td>
						
					</tr>
					<tr >
						<td colspan="2">描述</td>
						<td colspan="2">
							<input type="text" name="description" class="easyui-validatebox" required="true"/>
						</td>
						
					</tr>
					<tr >
						<td colspan="2">授权</td>
						<td>
							<ul id="functionTree" class="ztree"></ul>
						</td>
						
					</tr>
					
				</table>
			</form>
		</div>
	</div>
</body>
</html>